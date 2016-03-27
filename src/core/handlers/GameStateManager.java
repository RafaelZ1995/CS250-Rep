package core.handlers;

import java.util.Stack;

import core.game.Game;
import core.states.GameState;
import core.states.Play;
import core.states.PlayState;

public class GameStateManager {

	private Game game;

	private Stack<GameState> gamestates;

	public static final int PLAY = 1234;

	// actual playstate
	public static final int PLAYSTATE = 1337;

	public GameStateManager(Game game) {
		this.game = game;
		gamestates = new Stack<GameState>();
		//pushState(PLAYSTATE); // set first state when initializing
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
		if (state == PLAY) {
			return new Play(this);
		} else if (state == PLAYSTATE) {
			// temp
			// DummyPlayer dplayer = new DummyPlayer();
			// SaveConfiguration temp = new SaveConfiguration(0, 0, dplayer);

			return new PlayState(this);
		}
		return null;
	}
}
