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
	private Player player;
	private long startTime;
	private long elapsedTime;
	
	public HealthBar(GameStateManager gsm) {

		this.gsm = gsm;
		startTime = System.currentTimeMillis();
		create();

	}

	public void create() {

		batch = gsm.getGame().getSb();
		myBitmap = new BitmapFont();
//		startTime = System.currentTimeMillis();

	}

	public void update() {
		
		// update health by calling updaeteHealth() from Player class
		myHealth = "Health: " + PlayState.player.updateHealth();

	}

	// display health on the screen
	public void render() {

		myBitmap.setColor(Color.CLEAR);

		elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
		System.out.println(elapsedTime);
		
		// give player a warning if their health falls to 30% or below
		// by flashing the healthbar 
		if (PlayState.player.getCurrentHealth() <= 3) {
			if(elapsedTime <= 0.5)
				myBitmap.setColor(Color.RED);
			else if(elapsedTime > 0.5 && elapsedTime <= 1)
				myBitmap.setColor(Color.CLEAR);
			else {
				startTime = System.currentTimeMillis();
			}
		}

		myBitmap.draw(batch, myHealth, 25, 100);
	}
	
}
