package core.handlers;

import java.util.Stack;

import core.game.Game;
import core.states.GameState;
import core.states.Play;


public class GameStateManager {

	private Game game;
	
	private Stack<GameState> gamestates;
	
	public static final int PLAY = 1234;
	
	public GameStateManager(Game game){
		this.game = game;
		gamestates = new Stack<GameState>();
		pushState(PLAY); // set first state when initializing
	}
	
	public void update(float dt){
		gamestates.peek().update(dt);
	}
	
	public void render(){
		gamestates.peek().render();
	}
	
	public Game getGame(){
		return game;
	}
	
	
	// pushing and popping
	private GameState getState(int state){
		if (state == PLAY) return new Play(this);
		return null;
	}
	
	public void setState(int state){
		popState();
		pushState(state);
	}
	
	public void pushState(int state){
		gamestates.push(getState(state));
	}
	
	public void popState(){
		GameState g = gamestates.pop();
		g.dispose();
	}
}
