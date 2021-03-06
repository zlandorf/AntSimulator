package fr.zlandorf.antSimulator.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import fr.zlandorf.antSimulator.constants.AntSimulatorConstants;
import fr.zlandorf.antSimulator.model.Ant;
import fr.zlandorf.antSimulator.model.FoodSource;
import fr.zlandorf.antSimulator.model.World;

public class Controller {

	private World world;
	private List<AntController> antControllers;
	private AntCommunicationBus communicationBus;
	private float time;
	
	public Controller(World world) {
		this.world = world;
		antControllers = new ArrayList<AntController>();
		communicationBus = new AntCommunicationBus();
		populateAnts();
		populateFood();
		time = 0;
	}

	public void update(float delta) {
		time += delta;
		
		if (time > AntSimulatorConstants.FOOD_SPAWN_FREQUENCY) {
			world.addFoodSource();
			time -= AntSimulatorConstants.FOOD_SPAWN_FREQUENCY;
		}
		
		// Using iterator to remove the items
		Iterator<FoodSource> foodIter = world.getFoodSources().iterator();
		while (foodIter.hasNext()) {
			FoodSource source = foodIter.next();
			if (source.getFoodLeft() <= 0.0f) {
				foodIter.remove();
			}
		}
		
		if (world.getFoodSources().isEmpty()) {
			world.addFoodSource();
		}
		
		for (int i = 0; i < antControllers.size(); i++) {
			antControllers.get(i).update(delta);
		}

		List<Ant> ants = world.getAnts();
		for (int i = 0; i < ants.size(); i++) {
			ants.get(i).update(delta);
		}
		
		while (world.getNest().getFoodStock() >= AntSimulatorConstants.ANT_FOOD_COST) {
			spawnAntFromNest();
			world.getNest().removeFoodStock(AntSimulatorConstants.ANT_FOOD_COST);
		}
	}
	
	private void populateAnts() {
		for (int i= 0; i < AntSimulatorConstants.INITIAL_NB_ANTS; i++) {
			spawnAntFromNest();
		}
	}

	private void populateFood() {
		for (int i = 0; i < AntSimulatorConstants.INITIAL_NB_FOOD_SOURCES; i++) {
			world.addFoodSource();
		}
	}
	
	public void addAnt(float x, float y) {
		addAnt(new Vector2(x, y));
	}
	
	public void addAnt(Vector2 pos) {
		addAnt(new Ant(pos));
	}
	
	private void addAnt(Ant ant) {
		world.addAnt(ant);
		antControllers.add(new AntController(world, ant, communicationBus));
	}
	
	public void spawnAntFromNest() {
		addAnt(new Vector2(world.getNest().getPosition()));
	}
}
