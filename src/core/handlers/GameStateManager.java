package core.handlers;

import java.util.Stack;

import core.game.Game;
import core.states.GameState;
import core.states.MenuState;
import core.states.PlayState;

/*
 * Constructor Does NOT set the first state, that is left for the game class to do in create()
 */
public class GameStateManager {

	private Game game;

	private Stack<GameState> gamestates;

	// IDs of states
	public static final int MENUSTATE = 1234;
	public static final int PLAYSTATE = 1337;

	public GameStateManager(Game game) {
		this.game = game;
		gamestates = new Stack<GameState>();
	}

	public void update(float dt) {
		gamestates.peek().update(dt);
	}

	public void render() {
		gamestates.peek().render();
	}

	public Game getGame() {
		return game;
	}

	/*
	 * pops the current GameState in the stack, creates a new instance of the
	 * given state, and pushes this into the stack.
	 */
	public void setState(int state) {
		if (!gamestates.isEmpty())
			popState();
		pushState(state);
	}

	// popState()
	private void popState() {
		GameState g = gamestates.pop();
		System.out.println("disposing from gsm");
		g.dispose();
	}

	// pushState
	private void pushState(int state) {
		gamestates.push(getState(state));
	}

	// getState
	private GameState getState(int state) {
		if (state == MENUSTATE) {
			return new MenuState(this);
		} else if (state == PLAYSTATE) {
			return new PlayState(this);
		}
		return null;
	}
}
