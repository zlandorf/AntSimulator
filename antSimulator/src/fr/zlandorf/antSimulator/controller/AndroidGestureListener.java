package fr.zlandorf.antSimulator.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import fr.zlandorf.antSimulator.model.World;
import fr.zlandorf.antSimulator.view.WorldRenderer;

public class AndroidGestureListener implements GestureListener {
	private static final float ZOOM_MAX = 2f;
	private static final float ZOOM_MIN = 0.2f;
	
	private World world;
	private WorldRenderer renderer;
	private OrthographicCamera camera;
	
	private Controller controller;
	
	private float initialZoomScale;
	
	public AndroidGestureListener(Controller controller, World world, WorldRenderer renderer) {
		this.world = world;
		this.controller = controller;
		this.renderer = renderer;
		this.camera = this.renderer.getCamera();
		initialZoomScale = camera.zoom;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		initialZoomScale = camera.zoom;
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Vector3 worldCoordinates = new Vector3(x, y, 0);
		camera.unproject(worldCoordinates);
		controller.addAnt(worldCoordinates.x, worldCoordinates.y);
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		renderer.setDebug(!renderer.isDebug());
		return true;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		camera.translate(initialZoomScale * -deltaX, initialZoomScale * deltaY);
		if (camera.position.x < 0) {
			camera.position.x += world.getWidth();
		} else if (camera.position.x > world.getWidth()) {
			camera.position.x -= world.getWidth();
		}
		
		if (camera.position.y < 0) {
			camera.position.y += world.getHeight();
		} else if (camera.position.y > world.getHeight()) {
			camera.position.y -= world.getHeight();
		}
		
		camera.update();
		return true;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		if (distance != 0) {
			float ratio = initialDistance / distance;
			float zoomScale = initialZoomScale * ratio;
			camera.zoom = Math.min(Math.max(ZOOM_MIN, zoomScale), ZOOM_MAX);
			camera.update();
			return true;	
		}
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {

//		float initialDistance = initialPointer1.dst(initialPointer2);
//		float distance = pointer1.dst(pointer2);
		return false;
	}
}
