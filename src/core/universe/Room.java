package core.universe;

import static core.handlers.B2DVars.PPM;

import java.util.ArrayList;
import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import core.game.Game;
import core.handlers.B2DVars;
import core.handlers.GameStateManager;
import core.states.PlayState;

/**
 * @author Rafael Zuniga created on : 3/7/2016
 * 
 *         Room: a single room that contains its own enemies, tiled map, doors
 *         leading to other rooms, ect. This class will parse the Tmx Files and
 *         all its layers; ie, create chain shapes where ever the tmx file
 *         specifies for collision. So basically, double for loop to check all
 *         the cells in the tmx file.
 * 
 *         comments: I was thinking each Room could have its own libgdx World
 *         instance, but i think it will be better for a Sector to have a single
 *         World instance. The World contains references to all the bodies
 *         created, this should allow us to still have access to bodies even if
 *         we go from a Room to another, atleast within Sectors.
 */

public class Room {

	// id
	int id;

	// temp
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera b2dcam;

	// Tiled map
	private TiledMap tiledMap;

	private OrthogonalTiledMapRenderer tmr;

	// World is passed from Sector class
	private World world;

	// game
	private Game game;

	private Sector sector; // why is this here? and as param

	// key: name of door, value: actual door
	private Hashtable<String, Door> doortable;

	public Hashtable<String, Door> getDoortable() {
		return doortable;
	}

	public Room(World world, TiledMap tiledMap, Game game, int id, Sector sector) {
		// temp
		b2dr = new Box2DDebugRenderer();
		b2dcam = new OrthographicCamera();
		b2dcam.setToOrtho(false, Game.V_WIDTH * Game.SCALE / PPM, Game.V_HEIGHT * Game.SCALE / PPM);
		// b2dcam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
		// Back ground implementation?

		this.world = world;
		this.tiledMap = tiledMap;
		this.game = game;
		this.id = id;
		this.sector = sector;
		tmr = new OrthogonalTiledMapRenderer(tiledMap);
		doortable = new Hashtable<String, Door>();

		// load tmx file
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

		// get collision layer and then get objects in this layer
		MapObjects collisionObjects = tiledMap.getLayers().get("collision").getObjects();

		// go through all objects and build libgdx2d poly lines where any are
		// found
		parsePolyLines(collisionObjects);
		System.out.println("room created");

	}

	/*
	 * this method is going through all objects in the objects inside the
	 * collision layer of the tmx file
	 * 
	 * probably will rename this to ParseAllObjects ... in the layer given
	 */
	private void parsePolyLines(MapObjects objects) {

		for (MapObject object : objects) {
			Shape shape;

			// parse poly lines
			if (object instanceof PolylineMapObject)
				shape = createPolyline((PolylineMapObject) object);
			// parse doors
			else if (object instanceof RectangleMapObject) {
				RectangleMapObject doorRect = (RectangleMapObject) object;
				Door door = new Door(world, doorRect);
				System.out.println("doorRectName: " + doorRect.getName());
				doortable.put(doorRect.getName(), door);
				continue;
			} else
				continue;

			// polylines
			Body body;
			BodyDef bdef = new BodyDef();
			bdef.type = BodyDef.BodyType.StaticBody;
			// System.out.println("parsing pt1 world: " + world);
			body = world.createBody(bdef);
			// System.out.println("parsing pt2");
			body.createFixture(shape, 1.0f).setUserData("ground");
			shape.dispose();
		}
	}

	private Shape createPolyline(PolylineMapObject pline) {
		float[] vertices = pline.getPolyline().getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length / 2];

		for (int i = 0; i < worldVertices.length; i++)
			worldVertices[i] = new Vector2(vertices[i * 2] / B2DVars.PPM, vertices[i * 2 + 1] / B2DVars.PPM);

		ChainShape cs = new ChainShape();
		cs.createChain(worldVertices);
		return cs;
	}

	private void createSquare(int row, int col) {
		BodyDef bdef = new BodyDef();
		bdef.position.set((col + 0.5f) * 32 / PPM, (row + 0.5f) * 32 / PPM);
		bdef.type = BodyType.StaticBody;
		Body body = world.createBody(bdef);

		// fixtures, actually make the body
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(16 / PPM, 16 / PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_BOX;
		Fixture fixture = body.createFixture(fdef);
		fixture.setUserData("ground");
	}

	public void render() {

		// render whole tmx file
		Body tempPlayer = PlayState.player.getPlayerBody();

		// set camera to follow player
		game.getCam().position.set(tempPlayer.getPosition().x * 100, tempPlayer.getPosition().y * 100, 0);

		game.getCam().update();

		// set b2dcam/b2d objects to render according to player
		b2dcam.position.set(PlayState.player.getPlayerBody().getPosition().x, (float) tempPlayer.getPosition().y, 0);
		b2dcam.update();

		tmr.setView(game.getCam());

		tmr.render();
		b2dr.render(world, b2dcam.combined);

	}

	public void update() {
		// enemies.update
		// moving platforms...
	}

	/*
	 * Dispose
	 */
	public void dispose() {
		// tiledMap.dispose(); do not dispose the tiledMap, this is the original
		// one.
		System.out.println("Room dipose()");
		tmr.dispose();
		b2dr.dispose();
	}

	/*
	 * Getters and Setters
	 */
	public OrthogonalTiledMapRenderer getTmr() {
		return tmr;
	}

	public void setTmr(OrthogonalTiledMapRenderer tmr) {
		this.tmr = tmr;
	}
}
