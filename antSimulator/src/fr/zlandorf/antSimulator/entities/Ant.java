package fr.zlandorf.antSimulator.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import fr.zlandorf.antSimulator.art.AntSimulatorArt;
import fr.zlandorf.antSimulator.components.AnimationComponent;
import fr.zlandorf.antSimulator.components.TextureComponent;
import fr.zlandorf.antSimulator.components.TransformComponent;
import fr.zlandorf.antSimulator.core.Entity;

public class Ant extends Entity {
	private static final float FRAME_DURATION = 0.1f;
	
	public Ant(float x, float y) {
		addComponent(new TransformComponent(new Vector3(x, y, 0), new Vector2(), new Vector2()));
		addComponent(new AnimationComponent(new Animation(FRAME_DURATION, AntSimulatorArt.antTextureRegions), 0f));
		addComponent(new TextureComponent());
	}
}
