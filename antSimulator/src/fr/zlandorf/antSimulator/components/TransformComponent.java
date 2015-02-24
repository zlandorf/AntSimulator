package fr.zlandorf.antSimulator.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import fr.zlandorf.antSimulator.core.Component;

public class TransformComponent extends Component {
	public Vector3 position;
	public Vector2 scale;
	public Vector2 direction;
	
	public TransformComponent(Vector3 position, Vector2 scale, Vector2 direction) {
		this.position = position;
		this.scale = scale;
		this.direction = direction;
	}
}
