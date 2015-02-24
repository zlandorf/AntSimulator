package fr.zlandorf.antSimulator.components;

import com.badlogic.gdx.graphics.g2d.Animation;

import fr.zlandorf.antSimulator.core.Component;

public class AnimationComponent extends Component {
	public Animation animation;
	public float stateTime;
	
	public AnimationComponent(Animation animation, float stateTime) {
		this.animation = animation;
		this.stateTime = stateTime;
	}
}
