package core.universe;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import core.game.Game;
import core.handlers.B2DVars;
import core.handlers.GameStateManager;
import core.handlers.PlayContactListener;
import core.states.PlayState;

/**
 * @author Rafael Zuniga created on 3/7/2016
 * 
 *         Sector: A collection of Room's that makes up a section of the whole
 *         universe of our game.
 * 
 *         Each Sector has its own World instance.
 */

public class Sector {

	// ID of this sector
	private final int SECTOR_ID;

	// for now, arraylist to hold the rooms making up a sector
	private ArrayList<Room> rooms = new ArrayList<Room>();

	// tmx files for this sector only, passed on from Universe
	private ArrayList<TiledMap> tmxFiles;

	// World, each Sector will have its own world
	private World world;

	// Selecting a current room by using an int
	private int currentRoom;

	// contact listener changes this to tell us we should switch room.
	private int roomToChangeTo;

	// Game instance
	private Game game;

	// Collision
	private PlayContactListener cl;

	// flagged bodies
	private Stack<Body> bodiesToDel;

	// Room Manager
	private Stack<Room> roomStack = new Stack<Room>();

	// Constructor initializes all the rooms in this sector.
	public Sector(int SECTOR_ID, ArrayList<TiledMap> tmxFiles, Game game) {
		// temporary
		currentRoom = 0;
		roomToChangeTo = 0;
		this.SECTOR_ID = SECTOR_ID;
		this.tmxFiles = tmxFiles;
		this.game = game;

		cl = new PlayContactListener(this);
		world = new World(new Vector2(0, -9.81f), true);
		world.setContactListener(cl);

		setRoom(currentRoom);
		bodiesToDel = new Stack<Body>();

	}

	// update current room
	public void update() {
		world.step(B2DVars.STEP, 6, 2);
		checkForRoomChange();
		deleteFlaggedBodies();
		roomStack.peek().update();
	}

	private void checkForRoomChange() {
		if (roomToChangeTo != currentRoom) {

			setRoom(roomToChangeTo);

			Door out; // door u come out of
			// decide which position to put player in after changing room
			if (cl.getDoorUsed().equals("leftDoor")) {
				out = roomStack.peek().getDoortable().get("rightDoor");
			} else if (cl.getDoorUsed().equals("rightDoor")) {
				out = roomStack.peek().getDoortable().get("leftDoor");
			} else {
				out = null;
				Gdx.app.log("Error", "Out door is null");
			}

			// change position of player
			Vector2 v = out.getPlayerSpawn();
			PlayState.player.getPlayerBody().setTransform(v, 0);
		}

	}

	/*
	 * Destroys all the bodies that have been flagged (bodies in the bodiesToDel
	 * stack).
	 */
	private void deleteFlaggedBodies() {
		while (!bodiesToDel.isEmpty()) {
			world.destroyBody(bodiesToDel.pop());
		}
	}

	/*
	 * adds all the bodies (except the player body) that currently exist in this
	 * sector's World instance to the stack bodiesToDel. In the update method we
	 * delete any bodies in bodiesToDel.
	 */
	private void flagBodiesToDispose() {
		Array<Body> toDispose = new Array<Body>();
		world.getBodies(toDispose);
		for (Body body : toDispose) {
			if (body.equals(PlayState.player.getPlayerBody())) { // skip player
																	// body
				continue;
			}
			bodiesToDel.push(body); // flag object to be deleted later
		}

	}

	public void setRoom(int id) {
		if (roomStack.isEmpty()) {
			Room temp = new Room(world, tmxFiles.get(id), game, id, this);
			roomStack.push(temp);
			currentRoom = id;
			roomToChangeTo = id;
		} else {
			Room toDispose = roomStack.pop();
			toDispose.dispose();
			flagBodiesToDispose();
			// set up next Room
			Room temp = new Room(world, tmxFiles.get(id), game, id, this);
			roomStack.push(temp);
			currentRoom = id;
			roomToChangeTo = id;
		}
	}

	// render current room
	public void render() {
		roomStack.peek().render();
	}

	public void dispose() {
		world.dispose();
	}

	// GETTERS
	public Room getCurrentRoom() {
		return rooms.get(currentRoom);
	}

	public int getCurrentRoomN() {
		return currentRoom;
	}

	// return ith room
	public Room getRoom(int i) {
		return rooms.get(i);
	}

	public World getWorld() {
		return world;
	}

	public int getNumRooms() {
		return tmxFiles.size();
	}

	// called from PlayState because Player class needs it
	public PlayContactListener getContactListener() {
		return cl;
	}

	// SETTERS
	public void setCurrentRoom(int i) {
		currentRoom = i;
	}

	public void setRoomToChangeTo(int i) {
		roomToChangeTo = i;
	}

	public void flagObject(Body b) {
		bodiesToDel.add(b);
	}
}
