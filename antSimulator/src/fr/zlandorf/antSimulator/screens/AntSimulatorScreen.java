package fr.zlandorf.antSimulator.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;

import fr.zlandorf.antSimulator.controller.AndroidGestureListener;
import fr.zlandorf.antSimulator.controller.Controller;
import fr.zlandorf.antSimulator.core.Engine;
import fr.zlandorf.antSimulator.model.World;
import fr.zlandorf.antSimulator.systems.AnimationSystem;
import fr.zlandorf.antSimulator.systems.RenderingSystem;
import fr.zlandorf.antSimulator.view.WorldRenderer;

public class AntSimulatorScreen implements Screen {

	
	private World world;
	private WorldRenderer renderer;
	private Controller controller;
	private AndroidGestureListener gestureListener;
	
	private Engine engine;
	
	@Override
	public void show() {
		System.out.println("loading engine");
		engine = new Engine();
		
		System.out.println("loading world");
		world = new World(engine);
		System.out.println("loading world renderer");
		renderer = new WorldRenderer(world);
		System.out.println("loading controller");
		controller = new Controller(world);
		gestureListener = new AndroidGestureListener(controller, world, renderer);
		Gdx.input.setInputProcessor(new GestureDetector(gestureListener));
		
		System.out.println("Adding animation system");
		engine.addSystem(new AnimationSystem());
		System.out.println("Adding rendering system");
		engine.addSystem(new RenderingSystem(world, renderer.batch));
		
	}


	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// TODO : how to make sure the RenderingSystem is called last ? (lookup on priority)
		controller.update(delta);
		engine.update(delta);
//		renderer.render(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}
}
