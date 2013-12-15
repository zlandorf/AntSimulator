package fr.zlandorf.antSimulator.model;

import com.badlogic.gdx.math.Vector2;

import fr.zlandorf.antSimulator.constants.AntSimulatorConstants;

public class FoodSource {
	private Vector2 position;
	private float foodLeft;
	
	public FoodSource(Vector2 position) {
		this.position = position;
		this.foodLeft = AntSimulatorConstants.INITIAL_FOOD_LEVEL;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public float getFoodLeft() {
		return foodLeft;
	}

	public void setFoodLeft(float foodLeft) {
		this.foodLeft = foodLeft;
	}
}
