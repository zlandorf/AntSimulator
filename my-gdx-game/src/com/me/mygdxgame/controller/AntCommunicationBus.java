package com.me.mygdxgame.controller;

import java.util.ArrayList;
import java.util.List;

import com.me.mygdxgame.model.Ant;


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
