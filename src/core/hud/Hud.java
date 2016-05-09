package core.hud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import core.game.Game;
import core.handlers.GameStateManager;

/**
 * Hud will know:
 * 		HealthBar class
 * 		MiniMap class
 * 		..
 * 
 */
public class Hud {
	
	private OrthographicCamera hudCam;
	private SpriteBatch sb;
	private HealthBar hp;
	
	public Hud(GameStateManager gsm){
		
		this.hudCam = gsm.getGame().getHudcam();
		this.sb = gsm.getGame().getSb();
		
		hp = new HealthBar(gsm);
		
	}

	public void update() { 
		
		hp.update();
		
	}
	
	public void render() {
		
		sb.setProjectionMatrix(hudCam.combined);
		sb.begin();
		sb.draw(Game.res.getTexture("frame"), 0, 0);
		hp.render();
		sb.end();
		
	}
}
