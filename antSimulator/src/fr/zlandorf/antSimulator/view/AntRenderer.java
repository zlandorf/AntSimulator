package fr.zlandorf.antSimulator.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.zlandorf.antSimulator.art.AntSimulatorArt;
import fr.zlandorf.antSimulator.constants.AntSimulatorConstants;
import fr.zlandorf.antSimulator.model.Ant;
import fr.zlandorf.antSimulator.model.World;
import fr.zlandorf.antSimulator.util.Util;

public class AntRenderer {
	private static final float FRAME_DURATION = 0.1f;
    private Animation animation;
    private TextureRegion currentFrame;
    private World world;
    
	public AntRenderer(World world) {
		this.world = world;
		this.animation = new Animation(FRAME_DURATION, AntSimulatorArt.antTextureRegions);
	}
	
	public void render(SpriteBatch batch, Ant ant) {
		float antStateTime = ant.getStateTime();
		currentFrame = animation.getKeyFrame(antStateTime, true);
		Vector2 antPos = ant.getPosition();
		float antAngle = ant.getDirection().angle() - 90;
		
		Util.redunduncyDrawing(currentFrame, batch, antPos, antAngle, world);
		
		if (ant.getFoodCarried() >= AntSimulatorConstants.ANT_MAX_FOOD_CARRIED) {
			TextureRegion payloadTex = AntSimulatorArt.payloadTexture;
			Util.redunduncyDrawing(payloadTex, batch, antPos, 0, world);
		}
	}
}
