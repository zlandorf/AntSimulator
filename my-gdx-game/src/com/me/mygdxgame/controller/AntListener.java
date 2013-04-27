package com.me.mygdxgame.controller;

import com.me.mygdxgame.model.Ant;

public interface AntListener {
	public void onAntMessage(Ant emitter, AntMessageType type);
}
