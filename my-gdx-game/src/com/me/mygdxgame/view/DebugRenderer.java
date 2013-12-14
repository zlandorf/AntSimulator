package com.me.mygdxgame.view;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.me.mygdxgame.model.Ant;
import com.me.mygdxgame.model.FoodSource;
import com.me.mygdxgame.model.World;

public class DebugRenderer {
	private ShapeRenderer shapeRenderer;
	
	private SpriteBatch antInfoTextBatch;
	private BitmapFont antInfoFont;
	
	private SpriteBatch gameInfoTextBatch;
	private BitmapFont gameInfoFont;

	private World world;
	private OrthographicCamera fboCamera;
	
	private float accumTime = 0;
	private int nbFrames = 0;
	private int currentFps = 0;
	
	private float height;
	
	public DebugRenderer(World world, OrthographicCamera fboCamera) {
		this.world = world;
		this.fboCamera = fboCamera;

		// There is sometimes a bug where the width is smaller than the height
		// the "real" height is the smallest value between the two
		height = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		shapeRenderer = new ShapeRenderer();
		antInfoTextBatch = new SpriteBatch();
		gameInfoTextBatch = new SpriteBatch();
		antInfoFont = new BitmapFont();
		
		gameInfoFont = new BitmapFont();
		gameInfoFont.setColor(Color.BLACK);
	}
	
	public void render(float delta) {
		Vector2 nestPos = world.getNest().getPosition();
		List<Ant> ants = world.getAnts();
		
		shapeRenderer.setProjectionMatrix(fboCamera.combined);
		shapeRenderer.begin(ShapeType.Box);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.box( 1, 1, 0, world.getWidth() - 1, world.getHeight() - 1, 0);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.box(nestPos.x, nestPos.y, 0, 2, 2, 0);
		for (int i = 0; i < ants.size(); i++) {
			Ant ant = ants.get(i);
			shapeRenderer.box(ant.getPosition().x - 2, ant.getPosition().y - 2, 0, 4, 4, 0);
			Vector2 goal = ant.getGoal();
			if (null != goal) {
				shapeRenderer.setColor(Color.GREEN);
				shapeRenderer.box(goal.x - 2, goal.y - 2, 0, 4, 4, 0);
				shapeRenderer.setColor(Color.RED);
			}
		}
		shapeRenderer.end();
		
		shapeRenderer.begin(ShapeType.Line);
		for (int i = 0; i < ants.size(); i++) {
			Ant ant = ants.get(i);
			Vector2 goal = ant.getGoal();
			if (null != goal) {
				shapeRenderer.setColor(0,0,1,0.5f);
				shapeRenderer.line(ant.getPosition().x, ant.getPosition().y, goal.x, goal.y);
			}
		}
		shapeRenderer.end();
		
		drawAntInformation();
	}
	
	public void renderGameInfo(float delta) {
		// draw overlay text
		int nbAnts = world.getAnts().size();
		int food   = (int)world.getNest().getFoodStock();
		int fps    = currentFps;
		
		float currentHeight = height - 10;
		gameInfoTextBatch.begin();
		gameInfoFont.draw(gameInfoTextBatch, "Nb Ants : ", 10, currentHeight);gameInfoFont.draw(gameInfoTextBatch, String.valueOf(nbAnts), 80, currentHeight);
		currentHeight -= antInfoFont.getLineHeight() + 5;
		gameInfoFont.draw(gameInfoTextBatch, "Food : ", 10, currentHeight);gameInfoFont.draw(gameInfoTextBatch, String.valueOf(food), 80, currentHeight);
		currentHeight -= antInfoFont.getLineHeight() + 5;
		gameInfoFont.draw(gameInfoTextBatch, "Fps : ", 10, currentHeight);gameInfoFont.draw(gameInfoTextBatch, String.valueOf(fps), 80, currentHeight);
		gameInfoTextBatch.end();
		
		nbFrames++;
		accumTime += delta;
		
		if (accumTime > 1.0f) {
			currentFps = (int) (nbFrames / accumTime);
			accumTime -= 1.0f;
			nbFrames = 0;
		}
	}
	
	public void drawAntInformation() {
		antInfoTextBatch.setProjectionMatrix(fboCamera.combined);
		antInfoTextBatch.begin();
		
		antInfoFont.setScale(0.7f);
		antInfoFont.setColor(Color.MAGENTA);
		
		List<Ant> ants = world.getAnts();
		for (int i = 0; i < ants.size(); i++) {
			Ant ant = ants.get(i);
			float x = ant.getPosition().x;
			float y = ant.getPosition().y + 32;
			
			String id = "ID: "+ant.getId();
			String state = "State: "+ant.getState().toString();
			String food = "Food: "+(int)ant.getFoodCarried();
			
			antInfoFont.draw(antInfoTextBatch, id, x, y);
			y -= antInfoFont.getLineHeight() + 2;
			antInfoFont.draw(antInfoTextBatch, state, x, y);
			y -= antInfoFont.getLineHeight() + 2;
			antInfoFont.draw(antInfoTextBatch, food, x, y);
		}
		
		antInfoFont.setScale(1f);
		antInfoFont.setColor(Color.ORANGE);
		
		List<FoodSource> foodSources = world.getFoodSources();
		for (int i = 0; i < foodSources.size(); i++) {
			FoodSource source = foodSources.get(i);
			float x = source.getPosition().x;
			float y = source.getPosition().y ;
			String food = "FoodLeft : "+(int)source.getFoodLeft();
			antInfoFont.draw(antInfoTextBatch, food, x, y);
		}
		
		antInfoTextBatch.end();
	}
	
}
