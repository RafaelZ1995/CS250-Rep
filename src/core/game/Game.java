package core.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import core.handlers.GameStateManager;
import core.handlers.MusicHandler;
import core.handlers.MyInputProcessor;
import core.handlers.Resources;
import core.savesystem.SaveConfigurationHandler;
import core.universe.TmxAssets;
import core.universe.Universe;

/**
 * To implement a type of back ground effect, just make a cam with use a smaller SCALE to render it. 
 *
 */
public class Game implements ApplicationListener {

	// public variables
	public static final String Title = "unTiled";
	public static final int V_WIDTH = 320; // virtual size, independent of
											// screen size
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2; // final dimensions = 640*480

	// Saves
	SaveConfigurationHandler saveConfHandler = new SaveConfigurationHandler();

	// box2d works better with fixed time steps
	public static final float STEP = 1 / 60f;
	private float accum; // keep track of how much time has gone by.

	// private variables
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudcam;

	// get the GameStateManager singleton object statically
	private GameStateManager gsm;;

	// Universe construction
	private TmxAssets tmxAssets; // loads tmx files
	public static Universe universe;

	private int currentRoom = 0; // Nof
	
	// textures, sprites, ...
	public static Resources res;
	
	private MusicHandler musicHandler;
	

	@Override
	public void create() {

		
		// set spritebatch cam and hudcam
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH * SCALE, V_HEIGHT * SCALE);
		// cam.setToOrtho(false, V_WIDTH / B2DVars.PPM, V_HEIGHT / B2DVars.PPM);
		hudcam = new OrthographicCamera();
		hudcam.setToOrtho(false, V_WIDTH * SCALE, V_HEIGHT * SCALE);

		// load tmx files and universe
		tmxAssets = new TmxAssets();
		universe = new Universe(this); // constructor does not set sector

		// Input for player.. this could possibly be in the player class, or playstate
		Gdx.input.setInputProcessor(new MyInputProcessor());
		
		// Game state manager
		gsm = new GameStateManager(this); // constructor does not set state
		gsm.setState(GameStateManager.MENUSTATE); // PlayState requires Universe // Start Game by setting the Menu state
		
		res = new Resources(); // load textures, sprites
	}

	/*
	 * most of the disposing is done by the GSM->GameState->dispose()
	 */
	@Override
	public void dispose() {
		

	}

	@Override
	public void pause() {
	}

	public void update(float dt) {

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)){
			gsm.setState(gsm.MENUSTATE);
		}
	
	}

	// game loop method
	@Override
	public void render() {
		update(0);
		accum += Gdx.graphics.getDeltaTime();
		// if (accum >= STEP){
		accum = 0;
		gsm.update(STEP);
		gsm.render();
		// }
		
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	}

	// Getters
	public SpriteBatch getSb() {
		return sb;
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public OrthographicCamera getHudcam() {
		return hudcam;
	}

}
