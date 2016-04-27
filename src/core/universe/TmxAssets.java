package core.universe;

import java.util.ArrayList;
import java.util.Hashtable;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * @author Rafael
 *
 *         TmxAssets: load all the Tmx files. Universe class statically
 *         references the Tmx files for every sector.
 * 
 *         This class should be initialized in Game.create(). we only want to
 *         load every file once, so we load them when the application starts.
 */

public class TmxAssets {

	// for intuition, lets keep a different arraylist for every Sector.
	public static ArrayList<TiledMap> sector0TmxFiles = new ArrayList<TiledMap>();
	public static ArrayList<TiledMap> sector1TmxFiles = new ArrayList<TiledMap>();

	// hash table that has <id for a sector, arraylist containing all tmx files
	// needed for this sector>
	public static Hashtable<Integer, ArrayList<TiledMap>> sectorTmxFiles = new Hashtable<Integer, ArrayList<TiledMap>>();

	public TmxAssets() {

		// load every Tmx file from our assets/Maps/Sector0 folder.
		sector0TmxFiles.add(new TmxMapLoader().load("Maps/Sector0/Rooms/r0.tmx")); // id 0
		sector0TmxFiles.add(new TmxMapLoader().load("Maps/Sector0/Rooms/r1.tmx")); // id 1
		sector0TmxFiles.add(new TmxMapLoader().load("Maps/Sector0/Rooms/r2.tmx")); // id 2?
		sector0TmxFiles.add(new TmxMapLoader().load("Maps/Sector0/Rooms/r3.tmx"));
		// load every Tmx file from our assets/Maps/Sector1 folder.
		sector1TmxFiles.add(new TmxMapLoader().load("Maps/Sector1/test1.tmx"));

		// add into hashtable
		sectorTmxFiles.put(0, sector0TmxFiles);
		sectorTmxFiles.put(1, sector1TmxFiles);
	}

}
