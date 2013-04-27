package com.me.mygdxgame.controller;

import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.mygdxgame.constants.AntSimulatorConstants;
import com.me.mygdxgame.model.Ant;
import com.me.mygdxgame.model.FoodSource;
import com.me.mygdxgame.model.World;
import com.me.mygdxgame.util.Util;

public class AntController implements AntListener {
	
	private Ant ant;
	private World world;
	
	private Vector2 goal = null;
	private Vector2 optimisedGoal = null;
	private Vector2 tempVector = null;
	
	private int currentTick;
	private int nextDirectionChangeTick;
	private AntCommunicationBus communicationBus;
	
	public AntController(World world, Ant ant, AntCommunicationBus communicationBus) {
		this.world = world;
		this.ant = ant;
		this.currentTick = 0;
		this.nextDirectionChangeTick = 0;
		this.tempVector = new Vector2();
		this.communicationBus = communicationBus;
		this.communicationBus.addListener(this);
	}
	
	@Override
	public void onAntMessage(Ant emitter, AntMessageType type) {
		
		if (emitter.getId() == ant.getId()) return;

		float distance = emitter.getPosition().dst(ant.getPosition());
		if (distance > AntSimulatorConstants.ANT_HEARING_DISTANCE) return;
		
		switch (type) {
		case FOUND_FOOD :
			if (ant.getState() == Ant.State.SEARCHING) {
				foundFoodSoure(emitter.getCurrentFoodSource());
			}
			break;
		default:
			break;
		}
	}
	
	public void update(float delta) {
		switch (ant.getState()) {
		case SEARCHING:
			search();
			break;
		case HARVESTING:
			harvest();
			break;
		default:
			break;
		}
		
		if (null != goal) {
			moveTowardsGoal();
		}
	}
	
	// Called when an ant has arrived at destination
	private void onArrival(Vector2 destination) {
		switch (ant.getState()) {
		case SEARCHING:
			// reset goal to force the ant to find another one later
			setNewGoal(null);
			break;
		case GOING_TO://this is the state used to carry food from the food source to the nest
			if (destination.equals(world.getNest().getPosition())) {
				onNestArrival();
			} else {
				onFoodArrival();
			}
			break;
		default:
//			setNewGoal(null);
//			ant.setState(Ant.State.SEARCHING);
			System.out.println(ant.getState());
			break;
		}
	}
	
	private void onNestArrival() {
		float foodCarried = ant.getFoodCarried();
		ant.setFoodCarried(0.0f);
		world.getNest().addFoodStock(foodCarried);
		
		FoodSource source = ant.getCurrentFoodSource();
		if (null == source) {
			setNewGoal(null);
			ant.setState(Ant.State.SEARCHING);
			ant.setCurrentFoodSource(null);
		} else {
			setNewGoal(source.getPosition());
		}
	}
	
	private void onFoodArrival() {
		FoodSource source = ant.getCurrentFoodSource();
		if (null !=  source && source.getFoodLeft() > 0.0f) {
			ant.setState(Ant.State.HARVESTING);	
		} else {
			ant.setState(Ant.State.SEARCHING);
			ant.setCurrentFoodSource(null);
		}
		
		setNewGoal(null);
	}
	
	private void foundFoodSoure(FoodSource source) {
		ant.setState(Ant.State.GOING_TO);
		ant.setCurrentFoodSource(source);
		setNewGoal(source.getPosition());
	}
	
	//------------ GOAL FINDING METHODS
	private void search() {
		
		List<FoodSource> sources = world.getFoodSources();
		int nbSources = sources.size();
		FoodSource source;
		
		//using indexation to avoid iterator instanciation
		for (int i = 0; i < nbSources; i++) {
			source = sources.get(i);
			if (ant.getPosition().dst(source.getPosition()) <= AntSimulatorConstants.ANT_FOOD_FIND_DISTANCE && source.getFoodLeft() > 0.0f) {
				foundFoodSoure(source);
				communicationBus.notifyOthers(ant, AntMessageType.FOUND_FOOD);
				return;
			}	
		}
		
		
		if (null == goal || currentTick == nextDirectionChangeTick) {
			currentTick = 0;
			setNewGoal(findRandomGoal());
			nextDirectionChangeTick = (int) (Math.random() * AntSimulatorConstants.ANT_DIR_CHANGE_FREQUENCY);
		} else {
			currentTick++;
		}
	}
	
	private Vector2 findRandomGoal() {
		Rectangle worldBoundingBox = world.getWorldBoundingBox();
		return Util.getRandomWorldPosition(worldBoundingBox.width, worldBoundingBox.height);
	}
	
	
	private void harvest() {
		FoodSource source = ant.getCurrentFoodSource();
		if (null == source) {
			//no more food left on that source
			ant.setState(Ant.State.SEARCHING);
			ant.setCurrentFoodSource(null);
			setNewGoal(null);
		} else {
			float foodCarried = ant.getFoodCarried();
			float foodLeftInSource = source.getFoodLeft();
			
			if (foodLeftInSource <= 0.0f) {
				//no more food left on that source
				ant.setState(Ant.State.SEARCHING);
				ant.setCurrentFoodSource(null);
				setNewGoal(null);
			} else {

				float foodHarvested = Math.min(AntSimulatorConstants.ANT_FOOD_HARVESTED_PER_TICK, foodLeftInSource);
				
				if (foodCarried >= AntSimulatorConstants.ANT_MAX_FOOD_CARRIED) {
					setNewGoal(world.getNest().getPosition());
					ant.setState(Ant.State.GOING_TO);
					return;
				}
				
				ant.setFoodCarried(foodCarried + foodHarvested);
				source.setFoodLeft(foodLeftInSource - foodHarvested);
			}
		}
	}
	
	//--------------- COMMON METHODS
	
	private void findOptimisedGoal() {
		optimisedGoal = new Vector2(goal);
		float antX = ant.getPosition().x;
		float antY = ant.getPosition().y;
		
		// Maximum distance is world / 2, because the ants can wrap around the world
		if (Math.abs(antX - goal.x) > world.getWidth() / 2) {
			if (antX > goal.x) {
				optimisedGoal.x += world.getWidth();
			} else {
				optimisedGoal.x -= world.getWidth();
			}
		}
		
		// Maximum distance is world / 2, because the ants can wrap around the world
		if (Math.abs(antY - goal.y) > world.getHeight() / 2) {
			if (antY > goal.y) {
				optimisedGoal.y += world.getHeight();
			} else {
				optimisedGoal.y -= world.getHeight();
			}
		}
	}
	
	private void moveTowardsGoal() {
		Vector2 antPos = ant.getPosition();
		float distToGoal = antPos.dst(goal);
		float distToOptimisedGoal = antPos.dst(optimisedGoal);

		Vector2 closest;
		if (distToGoal <= distToOptimisedGoal) {
			closest = goal;
		} else {
			closest = optimisedGoal;
		}
		float distanceToGoal = antPos.dst(closest); 
		tempVector.set(closest.x, closest.y);
		
		//set ant direction
		Vector2 dir = tempVector.sub(antPos);
		dir.nor();
		ant.setDirection(dir);
		
		if (distanceToGoal <= AntSimulatorConstants.ANT_SPEED) {
			ant.setPosition(closest.x, closest.y);
			onArrival(closest);
		} else {
			float newX = antPos.x + ant.getDirection().x * AntSimulatorConstants.ANT_SPEED; 
			float newY = antPos.y + ant.getDirection().y * AntSimulatorConstants.ANT_SPEED;
			ant.setPosition(newX, newY);
			wrapPositionAroundWorld();
		}
	}

	private void wrapPositionAroundWorld() {
		if (ant.getPosition().x > world.getWidth()) {
			ant.getPosition().x -= world.getWidth();	
		} else if (ant.getPosition().x < 0) {
			ant.getPosition().x += world.getWidth();
		}
		
		if (ant.getPosition().y > world.getHeight()) {
			ant.getPosition().y -= world.getHeight();	
		} else if (ant.getPosition().y < 0) {
			ant.getPosition().y += world.getHeight();
		}

	}
	
	private void setNewGoal(Vector2 newGoal) {
		if (null == newGoal) {
			this.goal = this.optimisedGoal = null;
			return;
		}
		
		this.goal = new Vector2(newGoal);
		ant.setGoal(goal);
		findOptimisedGoal();
	}

}
