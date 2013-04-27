package com.me.mygdxgame.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.mygdxgame.art.AntSimulatorArt;
import com.me.mygdxgame.constants.AntSimulatorConstants;
import com.me.mygdxgame.model.Ant;

public class AntRenderer {
	private static final float FRAME_DURATION = 0.1f;
    private Animation animation;
    private TextureRegion currentFrame;
    
	public AntRenderer() {
		this.animation = new Animation(FRAME_DURATION, AntSimulatorArt.antTextureRegions);
	}
	
	public void render(SpriteBatch batch, Ant ant) {
		float antStateTime = ant.getStateTime();
		currentFrame = animation.getKeyFrame(antStateTime, true);
		Vector2 antPos = ant.getPosition();
		float antAngle = ant.getDirection().angle() - 90;
		float antWidth = currentFrame.getRegionWidth();
		float antHeight = currentFrame.getRegionHeight();

		batch.draw(currentFrame, 
				   antPos.x - antWidth / 2, 
				   antPos.y - antHeight / 2, 
				   antWidth / 2, 
				   antHeight / 2, 
				   antWidth, 
				   antHeight, 
				   1f, 
				   1f, 
				   antAngle);
		
		if (ant.getFoodCarried() >= AntSimulatorConstants.ANT_MAX_FOOD_CARRIED) {
			batch.draw(AntSimulatorArt.payloadTexture, 
					antPos.x - AntSimulatorArt.payloadTexture.getRegionWidth() / 2,
					antPos.y - AntSimulatorArt.payloadTexture.getRegionHeight() / 2);
		}
	}
}
