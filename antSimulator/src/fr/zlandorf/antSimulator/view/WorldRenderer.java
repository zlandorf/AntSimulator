package fr.zlandorf.antSimulator.view;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;

import fr.zlandorf.antSimulator.art.AntSimulatorArt;
import fr.zlandorf.antSimulator.constants.AntSimulatorConstants;
import fr.zlandorf.antSimulator.model.Ant;
import fr.zlandorf.antSimulator.model.FoodSource;
import fr.zlandorf.antSimulator.model.World;
import fr.zlandorf.antSimulator.util.Util;

public class WorldRenderer {

	private World world;
	private OrthographicCamera camera;
	private OrthographicCamera fboCamera;
	
	private SpriteBatch fboBatch;
	private SpriteBatch batch;
	
	private AntRenderer antRenderer = null;
	private DebugRenderer debugRenderer = null;
	private FrameBuffer fbo = null;
	private TextureRegion fboRegion = null;
	
	private float width;
	private float height;
	
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
		
		fboCamera = new OrthographicCamera(world.getWidth(), world.getHeight());
		fboCamera.position.x = world.getWidth() / 2;
		fboCamera.position.y = world.getHeight() / 2;
		fboCamera.update();
		
		batch = new SpriteBatch();
		fboBatch = new SpriteBatch();
		
		antRenderer = new AntRenderer(world);
		debugRenderer = new DebugRenderer(world, fboCamera);
		
		fbo = new FrameBuffer(Format.RGB565, (int)world.getWidth(), (int) world.getHeight(), false);
		fboRegion = new TextureRegion(fbo.getColorBufferTexture());
		fboRegion.flip(false,  true);
	}
	
	public void render(float delta) {
		
		fboBatch.setProjectionMatrix(fboCamera.combined);

		fbo.begin();
		Gdx.gl.glClearColor(.95f, .867f, .699f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		fboBatch.begin();
		drawNest();
		drawFood();
		drawAnts();
		fboBatch.end();
		if (debug) {
			debugRenderer.render(delta);
		}
		fbo.end();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.draw(fboRegion,0,0);
		// right part of the screen
		if (camera.position.x >= world.getWidth() / 2) {
			batch.draw(fboRegion,world.getWidth(),0);
			
			// top 
			if (camera.position.y >= world.getHeight() / 2) {
				batch.draw(fboRegion, 0, world.getHeight());
				batch.draw(fboRegion, world.getWidth(), world.getHeight());
			} else { // bottom
				batch.draw(fboRegion, 0, -world.getHeight());
				batch.draw(fboRegion, world.getWidth(), -world.getHeight());
			}
			
		} else { // left part of the screen
			batch.draw(fboRegion,-world.getWidth(),0);
			
			// top
			if (camera.position.y >= world.getHeight() / 2) {
				batch.draw(fboRegion, 0, world.getHeight());
				batch.draw(fboRegion, - world.getWidth(), world.getHeight());
			} else { // bottom
				batch.draw(fboRegion, 0, -world.getHeight());
				batch.draw(fboRegion, - world.getWidth(), -world.getHeight());
			}
		}
		
		batch.end();
		
		if (debug) {
			debugRenderer.renderGameInfo(delta);
		}
	}

	private void drawAnts() {
		List<Ant> ants = world.getAnts();
		for (int i = 0; i < ants.size(); i++) {
			antRenderer.render(fboBatch, ants.get(i));
		}
	}

	private void drawNest() {
		Vector2 nestPos = world.getNest().getPosition();
		Util.redunduncyDrawing(AntSimulatorArt.nestTexture, fboBatch, nestPos, 0, world);
	}
	
	private void drawFood() {
		List<FoodSource> foodSources = world.getFoodSources();
		for (int i = 0; i < foodSources.size(); i++) {
			FoodSource source = foodSources.get(i);
			Vector2 foodPos = source.getPosition();
			float foodLeft = source.getFoodLeft();
			float maxFood = AntSimulatorConstants.INITIAL_FOOD_LEVEL;
			
			float step = maxFood / AntSimulatorArt.foodSourceTexture.length;
			float start = 0;
			float end = step;
			int index = 0;
			
			// Find the food texture that matches the amount of food eaten
			for (int j = 0; j < AntSimulatorArt.foodSourceTexture.length; j++) {
				if (start <= foodLeft && foodLeft < end) {
					index = AntSimulatorArt.foodSourceTexture.length - 1 - j;
				}
				start = end;
				end += step;
			}
			Util.redunduncyDrawing(AntSimulatorArt.foodSourceTexture[index], fboBatch, foodPos, 0, world);
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
