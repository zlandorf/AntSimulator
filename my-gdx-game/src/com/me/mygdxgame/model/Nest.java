package com.me.mygdxgame.model;

import com.badlogic.gdx.math.Vector2;

public class Nest {

	private Vector2 position;
	private float foodStock;
	
	public Nest(Vector2 position) {
		this.position = position;
		foodStock = 0;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void addFoodStock(float stock) {
		foodStock += stock;
	}
	
	public float getFoodStock() {
		return foodStock;
	}

	public void removeFoodStock(float stock) {
		foodStock -= stock;
	}
}
