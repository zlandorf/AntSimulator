package com.me.mygdxgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.me.mygdxgame.controller.AndroidGestureListener;
import com.me.mygdxgame.controller.Controller;
import com.me.mygdxgame.model.World;
import com.me.mygdxgame.view.WorldRenderer;

public class AntSimulatorScreen implements Screen {

	private World world;
	private WorldRenderer renderer;
	private Controller controller;
	private AndroidGestureListener gestureListener;
	
	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world);
		controller = new Controller(world);
		gestureListener = new AndroidGestureListener(controller, world, renderer);
		Gdx.input.setInputProcessor(new GestureDetector(gestureListener));
	}


	
	@Override
	public void render(float delta) {
		controller.update(delta);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render(delta);
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
