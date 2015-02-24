package fr.zlandorf.antSimulator.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.zlandorf.antSimulator.components.TextureComponent;
import fr.zlandorf.antSimulator.components.TransformComponent;
import fr.zlandorf.antSimulator.core.Engine;
import fr.zlandorf.antSimulator.core.Entity;
import fr.zlandorf.antSimulator.core.EntitySystem;
import fr.zlandorf.antSimulator.model.World;

public class RenderingSystem extends EntitySystem {

	private Engine engine;
	private SpriteBatch batch;
	
	private World world;
	private float width;
	private float height;
	
	private OrthographicCamera camera;
	
	public RenderingSystem(World world, SpriteBatch batch) {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		// Sometimes there is a bug where w and h seem to be switched
		if (width < height) {
			float tmp = width;
			width = height;
			height = tmp;
		}
		
		this.world = world;
		camera = new OrthographicCamera(width, height);
		camera.position.x = world.getNest().getPosition().x;
		camera.position.y = world.getNest().getPosition().y;
		camera.update();
		
		this.batch = batch;
	}
	
	@Override
	public void update(float delta) {
		System.out.println("updating rendering");
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for (Entity entity : engine.getEntitiesFor(TransformComponent.class, TextureComponent.class)) {
			TextureRegion texture = entity.getComponent(TextureComponent.class).texture;
			if (texture == null) {
				continue;
			}
			TransformComponent transform = entity.getComponent(TransformComponent.class);
			
			float width = texture.getRegionWidth();
			float height = texture.getRegionHeight();
//			
//			// for centered drawing
			float x = transform.position.x - width / 2;
			float y = transform.position.y - height/ 2;
			float angle = transform.direction.angle() - 90;

			batch.draw(texture, x, y, width / 2, height / 2, width, height, 1f, 1f, angle);
		}
		batch.end();
	}

	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
	}
}
