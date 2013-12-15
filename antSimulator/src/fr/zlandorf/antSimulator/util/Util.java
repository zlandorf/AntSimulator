package fr.zlandorf.antSimulator.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import fr.zlandorf.antSimulator.model.World;

/**
 * @author paul
 *
 */
public class Util {

	public static Vector2 getRandomWorldPosition(float maxX, float maxY) {
		Vector2 randomGoal = new Vector2();
		// very ugly fix for the nest or food sources to spawn on 0,0, which causes a bug
		randomGoal.x = 0.1f + (float)(Math.random() * maxX);
		randomGoal.y = 0.1f + (float)(Math.random() * maxY);
		return randomGoal;
	}
	
	
	/**
	 * This method is used to duplicate drawings on the border
	 * for the wrapping transitions around the world to be without seams
	 * @param region
	 * @param batch
	 * @param position
	 * @param world
	 */
	public static void redunduncyDrawing(TextureRegion region, SpriteBatch batch, Vector2 position, float angle, World world) {
		redunduncyDrawing(region, batch, position.x, position.y, angle, world);
	}
	
	public static void redunduncyDrawing(TextureRegion region, SpriteBatch batch, float posX, float posY, float angle, World world) {
		float width = region.getRegionWidth();
		float height = region.getRegionHeight();
		
		// for centered drawing
		float x = posX - width / 2;
		float y = posY - height/ 2;

		batch.draw(region, x, y, width / 2, height / 2, width, height, 1f, 1f, angle);
		
		if (x <= 0) {
			batch.draw(region, x + world.getWidth(), y, width / 2, height / 2, width, height, 1f, 1f, angle);
		} else if (world.getWidth() - x <= width) {
			batch.draw(region, x - world.getWidth(), y, width / 2, height / 2, width, height, 1f, 1f, angle);
		}
		
		if (y <= 0) {
			batch.draw(region, x, y + world.getHeight(), width / 2, height / 2, width, height, 1f, 1f, angle);
		} else if (world.getHeight() - y <= height) {
			batch.draw(region, x, y - world.getHeight(), width / 2, height / 2, width, height, 1f, 1f, angle);
		}
		
		// for the other corner
		if (x <= 0 && y <= 0) {
			batch.draw(region, x + world.getWidth(), y + world.getHeight(), width / 2, height / 2, width, height, 1f, 1f, angle);
		} else if (world.getWidth() - x <= width && world.getHeight() - y <= height) {
			batch.draw(region, x - world.getWidth(), y - world.getHeight(), width / 2, height / 2, width, height, 1f, 1f, angle);
		}
		
	}
}
