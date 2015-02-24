package fr.zlandorf.antSimulator.systems;

import fr.zlandorf.antSimulator.components.AnimationComponent;
import fr.zlandorf.antSimulator.components.TextureComponent;
import fr.zlandorf.antSimulator.core.Engine;
import fr.zlandorf.antSimulator.core.Entity;
import fr.zlandorf.antSimulator.core.EntitySystem;

public class AnimationSystem extends EntitySystem {
	private Engine engine;
	
	@Override
	public void update(float delta) {
		System.out.println("updating animations");
		
		for (Entity entity : engine.getEntitiesFor(AnimationComponent.class, TextureComponent.class)) {
			TextureComponent texture = entity.getComponent(TextureComponent.class);
			AnimationComponent animation = entity.getComponent(AnimationComponent.class);
			animation.stateTime += delta;
			texture.texture = animation.animation.getKeyFrame(animation.stateTime, true);
		}
	}

	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
	}
}
