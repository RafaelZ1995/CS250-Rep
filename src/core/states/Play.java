package core.states;

import static core.handlers.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import core.game.Game;
import core.handlers.B2DVars;
import core.handlers.GameStateManager;
import core.handlers.PlayContactListener;

public class Play extends GameState {

	private BitmapFont font = new BitmapFont();

	private World world;
	private Box2DDebugRenderer b2dr; // renders all the bodies

	// contact listener
	private PlayContactListener cl;
		
	// player body
	private Body playerBody;

	// new cam
	private OrthographicCamera b2dcam;
	

	// Tiledmap
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmr;

	public Play(GameStateManager gsm) {
		super(gsm);
		
	
		

		cl = new PlayContactListener();

		// Vector2(side gravity, normal gravity)
		// true, something so that gravity doesnt apply to objects
		// not in screen.. (tut part 2 min 4:00)
		world = new World(new Vector2(0, -9.81f), true);
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();

		
		
		// create platform
		BodyDef bdef = new BodyDef();
		bdef.position.set(160 / PPM, 120 / PPM);
		bdef.type = BodyType.StaticBody;
		Body body = world.createBody(bdef);

		// fixtures, actually make the body
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(100 / PPM, 5 / PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
		Fixture fixture = body.createFixture(fdef);
		fixture.setUserData("ground");

		// static body - don't move, unaffected by forces (ground)
		// kinematic body - not affected by forces, but can change velocity
		// (example: moving platform, not affected by gravity)
		// dynamic body - always get affected by forces (player)

		
		// create box player
		// first create a bodyDef object with the properties you want it to have
		bdef.position.set(160 / PPM, 200 / PPM);
		bdef.type = BodyType.DynamicBody;
		// then use the bodyDef object to create a Body object using the current
		// world
		playerBody = world.createBody(bdef);

		// then u define the fixtures we want the body to have
		shape.setAsBox(5 / PPM, 5 / PPM);
		// so create a fixtureDef... define the properties of it
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_BOX;
		fdef.filter.maskBits = B2DVars.BIT_GROUND | B2DVars.BIT_BALL;
		// then use that fixtureDef to make the actual fixture and add it to the
		// body you are dealing with
		fixture = playerBody.createFixture(fdef);
		fixture.setUserData("box");
		
		// another fixture for playerBody
		shape.setAsBox(2 / PPM, 2 / PPM, new Vector2(0, -7 / PPM), 0);
		fdef.filter.categoryBits = B2DVars.BIT_BOX;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		fdef.isSensor = true; // setting it to be a "ghost fixture"
		playerBody.createFixture(fdef).setUserData("foot");

		for (int i = 0; i < 10; i++) {
			// create falling circle
			bdef.position.set(((110 + i) / PPM), (220 + i) / PPM);
			body = world.createBody(bdef);

			CircleShape cshape = new CircleShape();
			cshape.setRadius(5 / PPM);
			fdef.shape = cshape;
			System.out.println(fdef.density);
			fdef.density = 100;
			fdef.friction = 5;
			fdef.filter.categoryBits = B2DVars.BIT_BALL;
			fdef.filter.maskBits = B2DVars.BIT_GROUND | B2DVars.BIT_BOX | B2DVars.BIT_BALL;
			fdef.restitution = (float) 0.7;
			fdef.isSensor = false;
			fixture = body.createFixture(fdef);
			fixture.setUserData("ball");
		}
		/*
		 * To create a new object: Use Bodydef bdef = new BodyDef(); and set:
		 * bdef.position.set(x,y) bdef.type = static/kinematic/dynamic Then Body
		 * body = world.createBody(bdef)
		 * 
		 * Then PolygonShape shape = new PolygonShape(); shape.setas...
		 * 
		 * Then FixtureDef fdef = new FixtureDef(); fdef.shape = shape // shape
		 * u just made
		 * 
		 * finally body.createFixture(fdef);
		 * 
		 */

		/*
		 * fdef.filter.categoryBits = ... // assigns the type of that body
		 * fdef.filter.maskBits = ... // assignts the types it can colide with
		 * // relationship of collision must be vice versa then u create the
		 * body using body.createFixture if you dont assign maskBits its value
		 * is -1 and thus collides with everything
		 */

		// set up box2dcam
		b2dcam = new OrthographicCamera();
		b2dcam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
	
		// load tiled map
		// file must be in the assets folder
		tileMap = new TmxMapLoader().load("Maps/test.tmx");
		tmr = new OrthogonalTiledMapRenderer(tileMap);
		
		TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get(0);
		
		for (int row = 0; row < layer.getHeight(); row++)
			for (int col = 0; col < layer.getWidth(); col++){
				
				// cell/tile
				Cell cell = layer.getCell(col, row);
				if (cell == null || cell.getTile() == null)
					continue;
				
				// make body
				//createSquare(row, col);
			}
	}
	
	private void createSquare(int row, int col){
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
		fdef.filter.maskBits = B2DVars.BIT_BOX | B2DVars.BIT_BALL;
		Fixture fixture = body.createFixture(fdef);
		fixture.setUserData("ground");
	}

	@Override
	public void handleInput() {
		System.out.println(cl.isPlayerOnGround());
		if(Gdx.input.isKeyPressed(Keys.SPACE) && cl.isPlayerOnGround())
			playerBody.applyForceToCenter(0, 40, true);
		if(Gdx.input.isKeyPressed(Keys.LEFT))
			playerBody.applyForceToCenter(-7, 0, true);
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
			playerBody.applyForceToCenter(7, 0, true);
	}

	@Override
	public void update(float dt) {
		handleInput();
		world.step(dt, 6, 2);
	}

	@Override
	public void render() {

		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draw tile map
		tmr.setView(cam);
		tmr.render();
		
		b2dr.render(world, b2dcam.combined);

	}

	@Override
	public void dispose() {
		world.dispose();
		tmr.dispose();
	}

}
