package core.universe;

import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import core.handlers.B2DVars;
import core.handlers.GameStateManager;

/**
 * @author Rafael Zuniga created on 3/7/2016
 * 
 *         Sector: A collection of Room's that makes up a section of the whole
 *         universe of our game.
 * 
 *         Comments: As of now, each Sector has its own World instance
 * 
 *         When player advances from Sector A to B, we dispose of A...
 */

public class Sector {

	// ID of this sector
	private final int SECTOR_ID;

	// for now, arraylist to hold the rooms making up a sector
	private ArrayList<Room> rooms = new ArrayList<Room>();

	// World, each Sector will have its own world
	private World world;

	// tmx files for this sector only, passed on from Universe
	private ArrayList<TiledMap> tmxFiles;

	// Selecting a current room by using an int
	private int currentRoom;
	
	private GameStateManager gsm;

	// Constructor initializes all the rooms in this sector.
	public Sector(int SECTOR_ID, ArrayList<TiledMap> tmxFiles, GameStateManager gsm) {
		// temporary
		currentRoom = 0;

		this.SECTOR_ID = SECTOR_ID;
		this.tmxFiles = tmxFiles;
		this.gsm = gsm;
		world = new World(new Vector2(0, -9.81f), true);

		setRoom(currentRoom);
		
		// initialize all rooms in this sector, with respective tmx file
		
		/*
		for (int i = 0; i < tmxFiles.size(); i++) {
			rooms.add(new Room(world, tmxFiles.get(i)));
		}
		*/
		// problem: when we initialize the Room, it automatically
		// renders them to the screen.
	}

	// update current room
	public void update() {
		world.step(B2DVars.STEP, 6, 2);
		roomStack.peek().update();
		//rooms.get(currentRoom).update();
	}

	// render current room
	public void render() {
		roomStack.peek().render();
		//rooms.get(currentRoom).render();
	}

	public void dispose() {
		// previous room? or previous previous room?

		//rooms.get(currentRoom).dispose(); // dispose all rooms

		world.dispose();
	}

	// GETTERS
	public Room getCurrentRoom() {
		return rooms.get(currentRoom);
	}
	
	public int getCurrentRoomN(){
		return currentRoom;
	}

	// return ith room
	public Room getRoom(int i) {
		return rooms.get(i);
	}

	public World getWorld() {
		return world;
	}
	
	public int getNumRooms(){
		return tmxFiles.size();
	}
	

	// SETTERS
	public void setCurrentRoom(int i) {
		currentRoom = i;
	}
	
	
	// Room Manager
	private Stack<Room> roomStack = new Stack<Room>();
	
	public void setRoom(int id){
		
		if (roomStack.isEmpty()){
			Room temp = new Room(world, tmxFiles.get(id), gsm);
			roomStack.push(temp);
			currentRoom = id;
		}
		else{
			Room toDispose = roomStack.pop();
			toDispose.dispose();
			
			// set up next sector
			Room temp = new Room(world, tmxFiles.get(id), gsm);
			roomStack.push(temp);
			currentRoom = id;
		}
		
	}

}
