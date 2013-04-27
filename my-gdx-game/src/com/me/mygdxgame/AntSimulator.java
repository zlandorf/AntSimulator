package com.me.mygdxgame;

import com.badlogic.gdx.Game;
import com.me.mygdxgame.art.AntSimulatorArt;
import com.me.mygdxgame.screens.AntSimulatorScreen;

public class AntSimulator extends Game {
	
	@Override
	public void create() {
		AntSimulatorArt.load();
		setScreen(new AntSimulatorScreen());
	}
}
