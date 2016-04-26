package core.player;

import static core.handlers.B2DVars.PPM;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

import core.game.Game;
import core.handlers.B2DVars;
import core.handlers.GameStateManager;
import core.handlers.MyInput;
import core.handlers.PlayContactListener;

//	// player info
//	private boolean isDead;
//	private boolean isHit;
//
//	// player info
//	private int maxHealth;
//	private int currentHealth;
//	private int damage;


public class Player {

	private Body playerBody;
	private MyInput myinput;
	private PlayContactListener cl;
	private GameStateManager gsm;

	private String displayHealth;
	BitmapFont myBitmap;
	private final int health = 10;
	private boolean isPlayerHit;

	private long startTime;
	private long elapsedTime;
	private final double coolDownTime = 10;
	private boolean dashAbility;
	private boolean isFacingRight; // to check if player is facing left or right
	private SpriteBatch batch;

	public Player(World world, GameStateManager gsm) {

		this.cl = Game.universe.getCurrentSector().getContactListener();
		this.gsm = gsm;
		myinput = new MyInput();

		// create box player
		BodyDef bdef = new BodyDef();
		bdef.position.set(160 / PPM, 200 / PPM);
		bdef.type = BodyType.DynamicBody;

		// currently there is no world. because Sectors are only
		// initialized once, so when we dispose them, how do we get them
		// back?
		playerBody = world.createBody(bdef);
		System.out.println("here again");
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(5 / PPM, 5 / PPM);
		FixtureDef fdef = new FixtureDef();

		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_BOX;
		fdef.filter.maskBits = B2DVars.BIT_GROUND | B2DVars.BIT_BALL;

		Fixture fixture = playerBody.createFixture(fdef);
		fixture = playerBody.createFixture(fdef);
		fixture.setUserData("box");

		// another fixture for playerBody
		shape.setAsBox(2 / PPM, 2 / PPM, new Vector2(0, -7 / PPM), 0);
		fdef.filter.categoryBits = B2DVars.BIT_BOX;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		fdef.isSensor = true; // setting it to be a "ghost fixture"
		playerBody.createFixture(fdef).setUserData("foot");

		startTime = (long) (TimeUtils.millis() - (coolDownTime * 1000)); // number of seconds since between now and Jan 1, 1970
												// 2 seconds is subtracted or else there will be error when special is activated for first time
		batch = gsm.getGame().getSb();

	}

	public void create() {

		myBitmap = new BitmapFont();

	}

	public void update() {

		updateCooldowns(); // start calculating time for special ability cooldown
		updateHealth();

		if(myinput.isDown(myinput.JUMP)) {
			if(cl.isPlayerOnGround()) 
				playerBody.setLinearVelocity(0, 4);
		}
		
		if(myinput.isDown(myinput.LEFT)) {
			playerBody.setLinearVelocity(-1, 0);
			isFacingRight = false;
		}

		if(myinput.isDown(myinput.RIGHT)) {
			playerBody.setLinearVelocity(1, 0);
			isFacingRight = true;
		}

		if(myinput.isDown(myinput.UP)) {
			if(cl.isPlayerOnGround())
				playerBody.setLinearVelocity(0, 2);
		}

		if(myinput.isDown(myinput.DOWN))
			playerBody.setLinearVelocity(0, -1);

		if(myinput.isPressed(myinput.DASH)) {
			dashAbility();
		}

	}

	public void updateHealth() {
		int currentHealth = health;
		displayHealth = "health: " + currentHealth; // display player health

		// if player is attached, decrease health and update health displayed on screen
		if(isPlayerHit == true) {
			currentHealth--;
			displayHealth = "health: " + currentHealth;
		}
		if(currentHealth == 0) {
			System.out.println("GAME OVER BITCH!!!");
		}
	}

	// cool-down for special ability
	private void updateCooldowns() {

		elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

	}

	// if cool-down time is
	public void dashAbility(){

		if(elapsedTime >= coolDownTime) {
			startTime = System.currentTimeMillis();
			//System.out.println("Time elapsed in seconds = " + ((System.currentTimeMillis() - startTime) / 1000));
			dashAbility = true;


			// if character is facing right, activating special ability will move character right, else left
			if(isFacingRight == true)
				playerBody.setLinearVelocity(50, 0);
			else
				playerBody.setLinearVelocity(-50, 0);
		} else
			System.out.println((coolDownTime - elapsedTime) + " more seconds until attack recharges.");

	}

	public Body getPlayerBody() {
		return playerBody;
	}


	public void render() {

		batch.begin();
		myBitmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		myBitmap.draw(batch, displayHealth, 25, 100);
		batch.end();

	}

}

////	public void attack() { };
//
//	public void hit() {
//
//		if(isHit = true)
//			currentHealth--;
//
//		isHit = false;
//
//	}
//
//
//	public void calculateDamage() {
//		currentHealth = currentHealth - damage;
//
//		if(currentHealth <= 10) {
//			// give player a warning
//		}
//
//		if(maxHealth - currentHealth == 0) {
//			// game over
//		}
//	}
//
//	public void update(float dt) {
//
//		// check if hit
//		// check lives
//		// turning
//		// accelerating
//		// deceleration
//
//		// collision detection
//
//	}
