package core.universe;

import java.util.ArrayList;
import java.util.Stack;

import core.game.Game;
import core.handlers.GameStateManager;
import core.states.GameState;
import core.states.PlayState;

/**
 * @author Rafael created on 3/7/2016
 * 
 *         Universe class created in PlayState. We do not want to have the same
 *         universe if player exits current universe and loads another universe
 *         from another save state.
 * 
 *         Universe class creates Sectors, Sectors create rooms.
 * 
 *         For performance, I think its good if we dispose() sector A, if player
 *         goes from Sector A to Sector B. Thus, every time player enters a
 *         sector, it must be loaded at that time. Any better ideas?
 *         
 *         Important:
 *         Constructor Does NOT set the first sector, that is left for the PlayState class to do.
 *         Universe no longer depends on gsm
 *
 */
public class Universe {

	public static int currentSector;
	
	private ArrayList<Sector> sectors = new ArrayList<Sector>();
	
	private Game game;

	/*
	 * will probably take in a saveConfiguration object eventually. So that it knows
	 * what the current sector and current room should be.
	 */
	public Universe(Game game) {
		currentSector = -1; // PlayState sets the sector
		this.game = game;
	}

	public void update() {
		sectorStack.peek().update();
		//sectors.get(currentSector).update();
	}

	public void render() {
		sectorStack.peek().render();
		//sectors.get(currentSector).render();
	}

	public void dispose() {	
		sectorStack.peek().dispose();
		// dispose previous sector?
		//sectors.get(currentSector).dispose();
	}

	public Sector getSector(int i) {
		return sectors.get(i);
	}
	
	public Sector getCurrentSector() {
		return sectorStack.peek();
		//return sectors.get(currentSector);
	}
	
	
	// Sector and World manager
	private Stack<Sector> sectorStack = new Stack<Sector>();
	
	public void setSector(int id){
		if (sectorStack.isEmpty()){
			Sector temp = new Sector(id, TmxAssets.sectorTmxFiles.get(id), game);
			sectorStack.push(temp);
			currentSector = id;
		}
		else{
			Sector toDispose = sectorStack.pop();
			toDispose.dispose();
			
			// set up next sector
			Sector temp = new Sector(id, TmxAssets.sectorTmxFiles.get(id), game);
			sectorStack.push(temp);
			currentSector = id;
		}
	}
}
