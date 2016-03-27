package core.player;

import static core.handlers.B2DVars.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import core.handlers.B2DVars;
import core.handlers.MyInput;
import core.handlers.MyInputProcessor;

//	// DASHING ABILITY 
//	
//	// player info
//	private boolean isDead;
//	private boolean isHit;
//	
//	// to control movement 
//	private float maxSpeed;
//	private float acceleration;
//	private float deceleration;
//	private float acceleratingTimer;
//	
//	// player info
//	private int maxHealth;
//	private int currentHealth;
//	private int damage;


public class Player {
		
	private Body playerBody;
	private MyInput myinput;
	
	public Player(World world) {
		
		//input = new MyInputProcessor();
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
	
	}
	
	public void update() {
		
		if(myinput.isDown(myinput.JUMP)) 
			playerBody.applyForceToCenter(0, 40, true);
			
		if(myinput.isDown(myinput.LEFT)) 
			playerBody.applyForceToCenter(-7, 0, true);
		
		if(myinput.isDown(myinput.RIGHT))
			playerBody.applyForceToCenter(7, 0, true);
		
		if(myinput.isDown(myinput.UP))
			playerBody.applyForceToCenter(0, 7, true);
		
		if(myinput.isDown(myinput.DOWN))
			playerBody.applyForceToCenter(0, -7, true);
	}
	
	public Body getPlayerBody(){
		return playerBody;
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
	
