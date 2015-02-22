package fr.zlandorf.antSimulator.core;

import java.util.HashMap;
import java.util.Map;


public class Entity {
	long id;
	private Map<Class<? extends Component>, Component> components;
	
	public Entity() {
		this.components = new HashMap<Class<? extends Component>, Component>();
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void addComponent(Component component) {
		this.components.put(component.getClass(), component);
	}
	
	public Component getComponent(Class<? extends Component> component) {
		if (components.containsKey(component)) {
			return components.get(component);
		}
		return null;
	}
}
