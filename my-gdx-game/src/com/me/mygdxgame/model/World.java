package com.me.mygdxgame.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.me.mygdxgame.constants.AntSimulatorConstants;
import com.me.mygdxgame.util.Util;

public class World {
	
	private int width = 1000;
	private int height = 800;

	private List<Ant> ants;
	private List<FoodSource> foodSources; 
	
	private Nest nest;
	private Rectangle worldBoundingBox;

	public World() {
		width = Gdx.graphics.getWidth() * AntSimulatorConstants.SIZE_FACTOR;
		height = Gdx.graphics.getHeight() * AntSimulatorConstants.SIZE_FACTOR;
		if (width < height) {
			int tmp = width;
			width = height;
			height = tmp;
		}
		
		worldBoundingBox = new Rectangle(0, 0, width, height);
		ants = new ArrayList<Ant>();
		foodSources = new ArrayList<FoodSource>();
		nest = new Nest(Util.getRandomWorldPosition(width, height));
		addFoodSource();
	}
	
	public void addFoodSource() {
		foodSources.add(new FoodSource(Util.getRandomWorldPosition(width, height)));
	}
	
	public void addAnt(Ant ant) {
		ants.add(ant);
	}
	

	public Rectangle getWorldBoundingBox() {
		return worldBoundingBox;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public List<Ant> getAnts() {
		return ants;
	}
	
	
	public Nest getNest() {
		return nest;
	}

	public void setNest(Nest nest) {
		this.nest = nest;
	}

	public List<FoodSource> getFoodSources() {
		return foodSources;
	}
	
	public void removeFoodSource(FoodSource source) {
		foodSources.remove(source);
	}

}
