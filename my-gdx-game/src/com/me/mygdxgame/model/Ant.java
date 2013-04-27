package com.me.mygdxgame.model;

import com.badlogic.gdx.math.Vector2;

public class Ant {
	
	public enum State { SEARCHING, GOING_TO, HARVESTING };
	
	private static int CURRENT_ID = 0;
	
	private Vector2 goal = null;
	
	private float foodCarried;
	private FoodSource currentFoodSource;
	
	private Vector2 position = new Vector2();
	private Vector2 direction = new Vector2();
//	private Vector2 acceleration = new Vector2();
//	private Vector2 velocity = new Vector2();
	private float stateTime;
	private State state;
	private int id;

	public Ant(Vector2 position) {
		this.position = position;
		this.stateTime = 0.f;
		this.state = State.SEARCHING;
		id = CURRENT_ID++;
	}

	public void update(float delta) {
		stateTime += delta;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}
	
	public void setPosition(Vector2 position) {
		this.setPosition(position.x, position.y);
	}
	
	public Vector2 getDirection() {
		return direction;
	}
	
	public void setDirection(float x, float y) {
		this.direction.set(x, y);
	}
	
	public void setDirection(Vector2 direction) {
		this.setDirection(direction.x, direction.y);
	}

	public float getStateTime() {
		return stateTime;
	}
	
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public Vector2 getGoal() {
		return goal;
	}

	public void setGoal(Vector2 goal) {
		this.goal = goal;
	}

	public float getFoodCarried() {
		return foodCarried;
	}

	public void setFoodCarried(float foodCarried) {
		this.foodCarried = foodCarried;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public FoodSource getCurrentFoodSource() {
		return currentFoodSource;
	}

	public void setCurrentFoodSource(FoodSource currentFoodSource) {
		this.currentFoodSource = currentFoodSource;
	}
}

