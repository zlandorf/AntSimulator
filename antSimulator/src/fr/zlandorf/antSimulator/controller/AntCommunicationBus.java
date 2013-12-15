package fr.zlandorf.antSimulator.controller;

import java.util.ArrayList;
import java.util.List;

import fr.zlandorf.antSimulator.model.Ant;


public class AntCommunicationBus {

	List<AntListener> listeners;
	
	public AntCommunicationBus() {
		listeners = new ArrayList<AntListener>();
	}
	
	public void addListener(AntListener listener) {
		listeners.add(listener);
	}
	
	public void notifyOthers(Ant emitter, AntMessageType type) {
		int nbListeners = listeners.size();
		for (int i = 0; i < nbListeners; ++i) {
			listeners.get(i).onAntMessage(emitter, type);
		}
	}
}
