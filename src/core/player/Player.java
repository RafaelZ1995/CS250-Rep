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

public class Player {

	private Body playerBody;
	private MyInput myinput;
	private PlayContactListener cl;
	private GameStateManager gsm;

	private String displayHealth;
	private BitmapFont myBitmap;
	private final int TOTAL_HEALTH = 10;
	private boolean isPlayerHit;

	private long startTime; // for dashing
	private long elapsedTime; // for dashing
	private final double coolDownTime = 2; // dash Cool down
	private boolean dashing;
	private boolean isFacingRight; // to check if player is facing left or right

	private World world;

	// Motion
	private final float ACC = (float) 0.4; // acceleration
	private final float DACC = (float) 0.2; // deacceleration
	private final float MAX_SPEED = (float) 2.3;
	private float speed;
	
	// Health
	private boolean isDead;
	private boolean isHit;
	private int maxHealth;
	private int currentHealth;
	private int damage;

	public Player(World world, GameStateManager gsm) {

		this.cl = Game.universe.getCurrentSector().getContactListener();
		this.gsm = gsm;
		this.world = world;
		myinput = new MyInput();

		// create box player
		BodyDef bdef = new BodyDef();
		bdef.position.set(160 / PPM, 200 / PPM);
		bdef.type = BodyType.DynamicBody;
		
		playerBody = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(5 / PPM, 5 / PPM);
		FixtureDef fdef = new FixtureDef();

		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_BOX;
		fdef.filter.maskBits = B2DVars.BIT_GROUND | 
				B2DVars.BIT_ENEMY |
				B2DVars.BIT_ENEMY_HEAD;

		// so that player slides down a wall velocity is towards the wall
		fdef.friction = (float) 0;

		Fixture fixture = playerBody.createFixture(fdef);
		fixture.setUserData("playerBody");

		// foot sensor fixture to detect ground.
		shape.setAsBox(2 / PPM, 2 / PPM, new Vector2(0, -7 / PPM), 0);
		fdef.isSensor = true; // setting it to be a "ghost fixture"
		fixture = playerBody.createFixture(fdef);
		fixture.setUserData("foot");

		startTime = (long) (TimeUtils.millis() - (coolDownTime * 1000));
		// 2 seconds is subtracted or else there will be error when special is
		// activated for first time
		
		// set health
		currentHealth = TOTAL_HEALTH;

	}

	public void update() {

		// calculate cooldowns for special abilities
		updateCooldowns();
		updateHealth();

		// eventually we would want a boolean to check if we are using any
		// ability at all
		if (!dashing)
			calculateNormalMovement(); // checks jump, left, right

		calculateAbilities(); // checks dash

		// finally update position here
		float linearY = playerBody.getLinearVelocity().y; 
		// get the linear Velocity y, and use it for setLinearVelocity
		playerBody.setLinearVelocity(speed, linearY);
		// that way we are not changing it to 0.

	}

	private void calculateAbilities() {
		if (myinput.isDown(myinput.DASH)) {
			dashing = true;
			dashAbility();
		} else {
			// we can wait for cd to be over to put this back to false.
			dashing = false;
		}
	}

	private void calculateNormalMovement() {
		if (myinput.isPressed(myinput.JUMP) && cl.isPlayerOnGround()) {
			playerBody.applyForceToCenter(0, 140, true);
		}

		if (myinput.isPressed(myinput.LEFT)) {
			isFacingRight = false;
			if (speed < -MAX_SPEED)
				speed = -MAX_SPEED;
			else
				speed -= ACC;
		} else if (myinput.isPressed(myinput.RIGHT)) {
			isFacingRight = true;

			if (speed > MAX_SPEED)
				speed = MAX_SPEED;
			else
				speed += ACC;
		} else { // decelerate

			if (isFacingRight) {
				speed -= DACC;
				if (speed < 0)
					speed = 0;
			} else {
				speed += DACC;
				if (speed > 0)
					speed = 0;
			}

		}

	}

	// cool-down for special ability
	private void updateCooldowns() {

		elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

	}

	public int updateHealth() {
		
		currentHealth = 2;
		// decrement health if player is attacked 
		if (isPlayerHit == true) 
			currentHealth--;
		
		 if(currentHealth == 0) {
			 
			 System.out.println("Game Over");
			 
		 }
		 
		return currentHealth;
		
	}
	
//	public boolean isPlayerHit() {
//		
//	}
	
	public int getCurrentHealth() {
		
		return currentHealth;
		
	}

	// if cool-down time is
	public void dashAbility() {

		if (elapsedTime >= coolDownTime) {
			startTime = System.currentTimeMillis();
			// System.out.println("Time elapsed in seconds = " +
			// ((System.currentTimeMillis() - startTime) / 1000));

			// if character is facing right, activating special ability will
			// move character right, else left
			if (isFacingRight == true) {
				speed = 5;
			} else {
				speed = -5;
			}
		} else
			System.out.println((coolDownTime - elapsedTime) + " more seconds until attack recharges.");

	}

	public Body getPlayerBody() {
		
		return playerBody;
		
	}

	public void render() {

	}
	
}

// public void update(float dt) {
//
// // check if hit
// // check lives
// // turning
// // accelerating
// // deceleration
//
// // collision detection
//
// }
