package core.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import core.handlers.B2DVars;
import core.handlers.GameStateManager;
import core.handlers.MyInputProcessor;
import core.universe.TmxAssets;
import core.universe.Universe;

public class Game implements ApplicationListener {

	// public variables
	public static final String Title = "Z";
	public static final int V_WIDTH = 320; // virtual size, independent of
											// screen size
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 3; // final dimensions = 640*480

	private float accum; // keep track of how much time has gone by.

	// private variables
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudcam;

	private GameStateManager gsm;

	// Universe construction
	private TmxAssets tmxAssets; // loads tmx files
	public static Universe universe;

	@Override
	public void create() {

		// set spritebatch cam and hudcam
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudcam = new OrthographicCamera();
		hudcam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		gsm = new GameStateManager(this);
		Gdx.input.setInputProcessor(new MyInputProcessor()); // for controller
		
		// load tmx files and universe
		tmxAssets = new TmxAssets();
		universe = new Universe(gsm);
		
		// Start Game by setting the Menu state
		gsm.setState(gsm.PLAYSTATE);
		
	}
		
	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	public void update(float dt) {

	}

	// game loop method
	@Override
	public void render() {
		accum += Gdx.graphics.getDeltaTime();
		// while (accum >= STEP){
		accum = 0;
		gsm.update(B2DVars.STEP);
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
