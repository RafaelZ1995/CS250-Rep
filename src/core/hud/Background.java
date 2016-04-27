package core.hud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

import core.game.Game;
import core.handlers.GameStateManager;
import core.states.PlayState;

/*
 * aded hud, bg, Resources
 */
public class Background {
	
	private OrthographicCamera bgCam;
	private SpriteBatch sb;
	private GameStateManager gsm;
	
	public Background(GameStateManager gsm){
		//this.hudCam = gsm.getGame().getHudcam();
		this.sb = gsm.getGame().getSb();
		this.gsm = gsm;
		bgCam = new OrthographicCamera();
		bgCam.setToOrtho(false, Game.V_WIDTH*2, Game.V_HEIGHT*2);
	}
	
	public void render(){
		Body player = PlayState.player.getPlayerBody();
		bgCam.position.set(player.getPosition().x + Game.V_WIDTH, player.getPosition().y + Game.V_HEIGHT, 0);
		bgCam.update();
		sb.setProjectionMatrix(bgCam.combined);
		sb.begin();
		sb.draw(Game.res.getTexture("space"), 0, 0);
		sb.end();
	}

}
