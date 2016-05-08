package core.handlers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Resources {
private HashMap<String, Texture> textures;
private HashMap<String, Texture> pickups;
	
	public Resources() {
		textures = new HashMap<String, Texture>();
		pickups = new HashMap<String, Texture>();
		// load all textures
		loadTexture("res/frame.png", "frame");
		loadTexture("res/darkBlueCrystals.png", "blueCrystals");
		loadTexture("res/blueLightSpace.jpg", "space");
		//loadTexture("Enemies/spikyCrawler1.png", "spikyCrawler1");
		//loadTexture("Enemies/spikyCrawler2.png", "spikyCrawler2");
		//loadTexture("Enemies/crawler.png", "crawler");
		
		// menu screen
		loadTexture("menu/Exit.png", "exit");
		loadTexture("menu/New Game.png", "newgame");
		//loadTexture("menu/select.png", "select");
		//loadTexture("menu/mainscreen bg.png", "mainscreen bg");
		//loadTexture("menu/bg0.png", "bg0");
		loadTexture("menu/bg1.png", "bg1");
		
		loadPickup("pickups/life.png", "life");
	}
	
	public void loadPickup(String path, String key) {
		Texture tex = new Texture(Gdx.files.internal(path));
		pickups.put(key, tex);
	}
	
	public Texture getPickup(String key){
		return pickups.get(key);
	}
	
	public void loadTexture(String path, String key) {
		Texture tex = new Texture(Gdx.files.internal(path));
		textures.put(key, tex);
	}
	
	public Texture getTexture(String key) {
		return textures.get(key);
	}
	
	public void disposeTexture(String key) {
		Texture tex = textures.get(key);
		if(tex != null) tex.dispose();
	}
}
