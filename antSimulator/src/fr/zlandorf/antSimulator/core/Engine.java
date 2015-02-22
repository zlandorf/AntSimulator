package fr.zlandorf.antSimulator.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Engine {
	private List<Entity> entities;
	private Map<Long, Entity> entitiesById;
	private List<EntitySystem> systems;
	
	private long currentId = 0;
	
	public Engine() {
		this.entities = new ArrayList<Entity>();
		this.entitiesById = new HashMap<Long, Entity>();
	}
	
	public void addEntity(Entity entity) {
		long entityId = currentId++;
		entity.setId(entityId);
		entities.add(entity);
		entitiesById.put(entityId, entity);
	}
	
	public void addSystem(EntitySystem system) {
		systems.add(system);
		system.addedToEngine(this);
	}
	
	public void update(float delta) {
		for (EntitySystem system : systems) {
			system.update(delta);
		}
	}
}
