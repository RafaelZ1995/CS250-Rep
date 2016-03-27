package core.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.World;

import core.game.Game;
import core.handlers.GameStateManager;
import core.player.DummyPlayer;
import core.player.Player;
import core.savesystem.SaveConfiguration;
import core.universe.Universe;

/**
 * @author Rafael
 *
 *         next: implement update/render methods in universe, Sectors, Rooms
 */
public class PlayState extends GameState {

	// temp
	public static Player player;

	// probably going to need a class savestate that has a universe, player
	// data...
	private Universe universe;
	
	// get world from universe.sector
	private World currentWorld;

	private SaveConfiguration saveConf;

	// PlayState constructor for when we load a save.
	public PlayState(GameStateManager gsm, SaveConfiguration saveConf) {
		super(gsm);
		this.universe = Game.universe;
		this.saveConf = saveConf;
		

		// HERE, you would set the Sector stored in saveConf.
		universe.setSector(saveConf.getSector());
		// pass this class object to universe
		universe.setState(this);

		currentWorld = universe.getCurrentSector().getWorld();
		// get the player reference from the SaveConfiguration we are loading
		player = saveConf.getPlayer();

	}

	// PlayState constructor for when we start a new game.s
	public PlayState(GameStateManager gsm) {
		super(gsm);
		this.universe = Game.universe;
		universe.setSector(0);
		currentWorld = universe.getCurrentSector().getWorld(); // set sector before this
		player = new Player(currentWorld);
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		handleInput();
		universe.update();
		player.update();
		//System.out.println(dplayer.getPlayerBody().getPosition());
		// world step is done in Sector's update
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// System.out.println("playstate rendering");
		universe.render();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		System.out.println("disposing PLAYSTATE");
		// universe.dispose(); do not dispose of universe. because that just
		// attemps to dispose of prev sector which is disposed by sector manager
		// in universe
	}
}
