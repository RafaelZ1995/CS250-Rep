package core.savesystem;

import core.player.DummyPlayer;
import core.player.Player;

/**
 * @author Rafael Zuniga
 * created on 3/10/16
 * 
 * This class will have all the stats that must be saved when player saves at a save station.
 * Stats include:
 * 		player health, current abilities, current sector and room he is in, 
 * 		Bosses that have been killed? so that universe knows not to even allow the player in that room again.
 */
public class SaveConfiguration {

	// name of this configuration, either user given or we just name it by the place user saved in.
	private String name;
	
	// position
	private int sector;
	private int room;
	
	// player stats
	private int health;
	private int mp;
	private int exp;
	// and so on
	
	// or it can just store the Player class reference
	private Player player;

	// public saveConfiguration(Player player)
	public SaveConfiguration(int sector, int room){
		this.sector = sector;
		this.room = room;
	}
	
	public SaveConfiguration(int sector, int room, DummyPlayer dplayer){
		this.sector = sector;
		this.room = room;
		this.player = player;
	}
	
	// GETTERS AND SETTERS
	public Player getPlayer(){
		return player;
	}
	
	public int getSector() {
		return sector;
	}

	public int getRoom() {
		return room;
	}
}
