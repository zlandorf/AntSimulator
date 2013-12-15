package fr.zlandorf.antSimulator;

import com.badlogic.gdx.Game;

import fr.zlandorf.antSimulator.art.AntSimulatorArt;
import fr.zlandorf.antSimulator.screens.AntSimulatorScreen;

public class AntSimulator extends Game {
	
	@Override
	public void create() {
		AntSimulatorArt.load();
		setScreen(new AntSimulatorScreen());
	}
}
