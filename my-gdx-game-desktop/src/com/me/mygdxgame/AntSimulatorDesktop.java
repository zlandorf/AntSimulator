package com.me.mygdxgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class AntSimulatorDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Ant Simulator";
		cfg.useGL20 = true;
		cfg.width = 1000;
		cfg.height = 800;
		
		new LwjglApplication(new AntSimulator(), cfg);
	}
}