package core.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.World;

import core.game.Game;
import core.handlers.GameStateManager;
import core.hud.Hud;
import core.player.Player;
import core.savesystem.SaveConfiguration;
import core.universe.Universe;

/**
 * @author Rafael
 *
 * 
 *         Important: PlayState depends on Universe. (we cannot be playing if we
 *         don't load the maps right)
 */
public class PlayState extends GameState {

	// temp
	public static Player player;

	private Universe universe;

	// get world from universe.sector
	private World currentWorld;

	private SaveConfiguration saveConf;
	
	private Hud hud;


	/*
	 * PlayState constructor for when we load a save.
	 */
	public PlayState(GameStateManager gsm, SaveConfiguration saveConf) {
		super(gsm);
		this.universe = Game.universe;
		this.saveConf = saveConf;
		hud = new Hud(gsm);
		
		// HERE, you would set the Sector stored in saveConf.
		universe.setSector(saveConf.getSector());
		// pass this class object to universe
		// universe.setState(this);

		currentWorld = universe.getCurrentSector().getWorld();
		// get the player reference from the SaveConfiguration we are loading
		player = saveConf.getPlayer();

	}

	/*
	 * PlayState constructor for when we start a new game.
	 */
	public PlayState(GameStateManager gsm) {
		super(gsm);
		this.universe = Game.universe;
		hud = new Hud(gsm);
		// since new game, set sector to 0 because we dont have a
		// saveconfiguration to load,
		// or we could automatically make a saveConfiguration that starts at
		// sector 0 and everything.. this is probably better
		universe.setSector(0);
		currentWorld = universe.getCurrentSector().getWorld(); // set sector
									// before this
		player = new Player(currentWorld, gsm);
		
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(float dt) {
		universe.update();
		player.update();
		// System.out.println(dplayer.getPlayerBody().getPosition());
		// world step is done in Sector's update
	}

	@Override
	public void render() {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT); // already done in Game?
		// System.out.println("playstate rendering");
		universe.render();
		hud.render();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		System.out.println("disposing PLAYSTATE");
		// universe.dispose(); do not dispose of universe. because that just
		// attemps to dispose of prev sector which is disposed by sector manager
		// in universe
	}
	
	// Getters
}
