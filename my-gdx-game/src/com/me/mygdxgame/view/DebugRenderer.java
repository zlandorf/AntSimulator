package com.me.mygdxgame.view;

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
	private SpriteBatch textBatch;
	
	private BitmapFont font;
	
	private World world;
	private OrthographicCamera camera;
	
	public DebugRenderer(World world, OrthographicCamera camera) {
		this.world = world;
		this.camera = camera;
		
		shapeRenderer = new ShapeRenderer();
		textBatch = new SpriteBatch();
		font = new BitmapFont();
	}
	
	public void render(float delta) {
		Vector2 nestPos = world.getNest().getPosition();
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Box);
		shapeRenderer.setColor(Color.RED);
		
		shapeRenderer.box(nestPos.x, nestPos.y, 0, 2, 2, 0);
		for (Ant ant : world.getAnts()) {
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
		for (Ant ant : world.getAnts()) {
			Vector2 goal = ant.getGoal();
			if (null != goal) {
				shapeRenderer.setColor(0,0,1,0.5f);
				shapeRenderer.line(ant.getPosition().x, ant.getPosition().y, goal.x, goal.y);
			}
		}
		shapeRenderer.end();
		
		drawText();
		
	}
	
	public void drawText() {
		textBatch.setProjectionMatrix(camera.combined);
		textBatch.begin();
		
		font.setScale(0.7f);
		font.setColor(Color.MAGENTA);
		for (Ant ant : world.getAnts()) {
			float x = ant.getPosition().x;
			float y = ant.getPosition().y + 32;
			
			String id = "ID: "+ant.getId();
			String state = "State: "+ant.getState().toString();
			String food = "Food: "+(int)ant.getFoodCarried();
			
			font.draw(textBatch, id, x, y);
			y -= font.getLineHeight() + 2;
			font.draw(textBatch, state, x, y);
			y -= font.getLineHeight() + 2;
			font.draw(textBatch, food, x, y);
		}
		
		font.setScale(1f);
		font.setColor(Color.ORANGE);
		for (FoodSource source : world.getFoodSources()) {
			float x = source.getPosition().x;
			float y = source.getPosition().y ;
			String food = "FoodLeft : "+(int)source.getFoodLeft();
			font.draw(textBatch, food, x, y);
		}
		
		textBatch.end();
	}
	
}
