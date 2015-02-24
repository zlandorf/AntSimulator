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
		this.systems = new ArrayList<EntitySystem>();
	}
	
	public void addEntity(Entity entity) {
		long entityId = currentId++;
		entity.setId(entityId);
		entities.add(entity);
		entitiesById.put(entity.getId(), entity);
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public Entity getEntity(long id) {
		return entitiesById.get(id);
	}
	
	public List<Entity> getEntitiesFor(Class<? extends Component>... components) {
		System.out.println("Get entities for : "+components);
		List<Entity> ents = new ArrayList<Entity>();
		for (Entity entity : entities) {
			boolean matches = true;
			for (Class<? extends Component> component : components) {
				if (entity.getComponent(component) == null) {
					matches = false;
					break;
				}
			}
			
			if (matches) {
				ents.add(entity);
			}
		}
		return ents;
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
