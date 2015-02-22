package fr.zlandorf.antSimulator.core;

public abstract class EntitySystem {
	public abstract void update(float delta);
	public abstract void addedToEngine(Engine engine);
}
