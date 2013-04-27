package com.me.mygdxgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.me.mygdxgame.art.AntSimulatorArt;
import com.me.mygdxgame.constants.AntSimulatorConstants;
import com.me.mygdxgame.model.Ant;
import com.me.mygdxgame.model.FoodSource;
import com.me.mygdxgame.model.World;

public class WorldRenderer {

	private World world;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private SpriteBatch textBatch;
	private BitmapFont font;
	
	private AntRenderer antRenderer = null;
	private DebugRenderer debugRenderer = null;
	private ShapeRenderer shapeRenderer = null;
	
	private float width;
	private float height;
	
	private float accumTime = 0;
	private int nbFrames = 0;
	private int currentFps = 0;
	
	private boolean debug;
	
	public WorldRenderer(World world) {
		this(world, AntSimulatorConstants.DEBUG_MODE);
	}
	
	public WorldRenderer(World world, boolean debug) {
		this.debug = debug;
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		// Sometimes there is a bug where w and h seem to be switched
		if (width < height) {
			float tmp = width;
			width = height;
			height = tmp;
		}
		
		this.world = world;
		camera = new OrthographicCamera(width, height);
		camera.position.x = world.getNest().getPosition().x;
		camera.position.y = world.getNest().getPosition().y;
		camera.update();
		
		batch = new SpriteBatch();
		textBatch = new SpriteBatch();
		
		antRenderer = new AntRenderer();
		debugRenderer = new DebugRenderer(world, camera);
		shapeRenderer = new ShapeRenderer();
		
		font = new BitmapFont();
		font.setColor(Color.BLACK);
	}
	
	public void render(float delta) {
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Box);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.box(0, 0, 0, world.getWidth(), world.getHeight(), 0);
		shapeRenderer.end();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		drawNest();
		drawFood();
		drawAnts();
		
		batch.end();
		
		if (debug) {
			debugRenderer.render(delta);
		}
		
		// draw overlay text
		int nbAnts = world.getAnts().size();
		int food   = (int)world.getNest().getFoodStock();
		int fps    = currentFps;
		
		float currentHeight = height - 10;
		textBatch.begin();
		font.draw(textBatch, "Nb Ants", 10, currentHeight);font.draw(textBatch, " : "+nbAnts, 60, currentHeight);
		currentHeight -= font.getLineHeight() + 5;
		font.draw(textBatch, "Food", 10, currentHeight);font.draw(textBatch, " : "+food, 60, currentHeight);
		currentHeight -= font.getLineHeight() + 5;
		font.draw(textBatch, "Fps", 10, currentHeight);font.draw(textBatch, " : "+fps, 60, currentHeight);
		textBatch.end();
		
		nbFrames++;
		accumTime += delta;
		
		if (accumTime > 1.0f) {
			currentFps = (int) (nbFrames / accumTime);
			accumTime -= 1.0f;
			nbFrames = 0;
		}
		
	}

	private void drawAnts() {
		for (Ant ant : world.getAnts()) {
			antRenderer.render(batch, ant);
		}
	}

	private void drawNest() {
		Vector2 nestPos = world.getNest().getPosition();
		batch.draw(AntSimulatorArt.nestTexture, 
				   nestPos.x - AntSimulatorArt.nestTexture.getRegionWidth() / 2,
				   nestPos.y - AntSimulatorArt.nestTexture.getRegionHeight() / 2);
	}
	
	private void drawFood() {
		for (FoodSource source : world.getFoodSources()) {
			Vector2 foodPos = source.getPosition();
			float foodLeft = source.getFoodLeft();
			float maxFood = AntSimulatorConstants.INITIAL_FOOD_LEVEL;
			
			float step = maxFood / AntSimulatorArt.foodSourceTexture.length;
			float start = 0;
			float end = step;
			int index = 0;
			
			for (int i = 0; i < AntSimulatorArt.foodSourceTexture.length; i++) {
				if (start <= foodLeft && foodLeft < end) {
					index = AntSimulatorArt.foodSourceTexture.length - 1 - i;
				}
				start = end;
				end += step;
			}
			
			TextureRegion frame = AntSimulatorArt.foodSourceTexture[index]; 
			batch.draw(frame,
					   foodPos.x - frame.getRegionWidth() / 2,
					   foodPos.y - frame.getRegionHeight() / 2);
		}
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public boolean isDebug() {
		return debug;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
