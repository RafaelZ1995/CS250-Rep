package core.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import core.handlers.GameStateManager;
import core.player.Player;
import core.states.PlayState;

public class HealthBar {

	private String myHealth;
	private BitmapFont myBitmap;
	private SpriteBatch batch;
	private GameStateManager gsm;
	private long startTime;
	private long elapsedTime;
	private double LENGTH = 0.25;

	public HealthBar(GameStateManager gsm) {

		this.gsm = gsm;
		startTime = System.currentTimeMillis();
		create();

	}

	public void create() {

		batch = gsm.getGame().getSb();
		myBitmap = new BitmapFont();
		myHealth = "Health: " + PlayState.player.getCurrentHealth();
		// startTime = System.currentTimeMillis();

	}

	public void update() {

		// update health by calling updateHealth() from Player class
		myHealth = "Health: " + PlayState.player.getCurrentHealth();

	}

	// display health on the screen
	public void render() {

		myBitmap.setColor(Color.CLEAR);

		elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

		// give player a warning if their health falls to 30% or below
		// by flashing the healthbar
		if (PlayState.player.getCurrentHealth() <= 3) {
			if (elapsedTime <= LENGTH)
				myBitmap.setColor(Color.RED);
			else if (elapsedTime > LENGTH && elapsedTime <= 1)
				myBitmap.setColor(Color.CLEAR);
			else {
				startTime = System.currentTimeMillis();
			}
		}else{
			myBitmap.setColor(Color.BLUE);
		}
		
		myBitmap.draw(batch, myHealth, 25, 100);
	}

}
