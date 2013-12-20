package fr.zlandorf.antSimulator.art;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AntSimulatorArt {
	public static TextureRegion[] antTextureRegions;
	public static TextureRegion[] foodSourceTexture;
	public static TextureRegion nestTexture;
	public static TextureRegion payloadTexture;
	
	public static void load() {
		loadAntTextures();
		loadNestTexture();
		loadFoodTexture();
	}
	
	private static void loadFoodTexture() {
		Texture plTex = new Texture(Gdx.files.internal("data/payload.png"));
		payloadTexture = new TextureRegion(plTex);
		
		Texture texture = new Texture(Gdx.files.internal("data/food.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		foodSourceTexture = new TextureRegion[4];
		int nbSlices = 4;
		
		int width = texture.getWidth() / nbSlices;
		int height = texture.getHeight();
		
		
		
		for (int i = 0; i < nbSlices; i++) {
			foodSourceTexture[i] = new TextureRegion(texture, i * width, 0, width, height);
		}
		
		//test merge gitgui/gitk
	}

	private static void loadNestTexture() {
		Texture texture = new Texture(Gdx.files.internal("data/nest.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		nestTexture = new TextureRegion(texture);
	}

	private static void loadAntTextures() {
		Texture antTexture = new Texture(Gdx.files.internal("data/ant.png"));
		antTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		antTextureRegions = new TextureRegion[4];
		int nbSlices = 4;
		
		int width = antTexture.getWidth() / nbSlices;
		int height = antTexture.getHeight();
		
		for (int i = 0; i < nbSlices; i++) {
			antTextureRegions[i] = new TextureRegion(antTexture, i * width, 0, width, height);
		}
	}
}
