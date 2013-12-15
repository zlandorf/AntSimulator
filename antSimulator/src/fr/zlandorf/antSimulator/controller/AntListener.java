package fr.zlandorf.antSimulator.controller;

import fr.zlandorf.antSimulator.model.Ant;

public interface AntListener {
	public void onAntMessage(Ant emitter, AntMessageType type);
}
