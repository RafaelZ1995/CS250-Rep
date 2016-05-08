package core.enemies;

import static core.handlers.B2DVars.PPM;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import core.game.Game;
import core.handlers.B2DVars;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


public class Crawler extends Enemy{
	
	private float stateTime; // do we need this
	//private Animation walkAnimation;
	private Array<TextureRegion> frames;
	private boolean setToDestroy;
	private boolean destroyed;
	
	public Crawler(World world, float x, float y) {
		super(world, x, y);
		frames = new Array<TextureRegion>();
		setToDestroy = false;
		destroyed = false;
		//frames.add(new TextureRegion(Game.res.getTexture("crawler")));
		//frames.add(new TextureRegion(Game.res.getTexture("spikyCrawler1")));
		//frames.add(new TextureRegion(Game.res.getTexture("spikyCrawler2")));

		//walkAnimation = new Animation(0.4f, frames);
		stateTime = 0;
		
		//setBounds(getX(), getY(), 16 / PPM, 16 / PPM); // boundaries of the sprite
		
	}
	
	public void update() {
		//stateTime += dt;
		if(setToDestroy && !destroyed) {
			world.destroyBody(enemyBody);
			destroyed = true;
			//setRegion(new TextureRegion(region)); smushed goomba sprite
			//stateTime = 0;
		}
		else if(!destroyed) {
			enemyBody.setLinearVelocity(velocity);
			setPosition(enemyBody.getPosition().x - getWidth() / 2, enemyBody.getPosition().y - getHeight() / 2);
			//setRegion(walkAnimation.getKeyFrame(stateTime, true));
		}
		
	}

	@Override
	protected void defineEnemy() {
		BodyDef enemyBDef = new BodyDef();
		enemyBDef.position.set(getX(), getY());
		enemyBDef.type = BodyDef.BodyType.DynamicBody;
		enemyBody = world.createBody(enemyBDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(5 / PPM, 20 / PPM);
		
		FixtureDef enemyFDef = new FixtureDef();
		enemyFDef.filter.categoryBits = B2DVars.BIT_ENEMY;
		enemyFDef.filter.maskBits = B2DVars.BIT_GROUND | 
				B2DVars.BIT_BALL | 
				B2DVars.BIT_BOX;
		enemyFDef.shape = shape;
		enemyBody.createFixture(enemyFDef).setUserData(this);
		
		/*
		setMaxHp(5);
		setDamage(5);
		*/
		
		// Create Head of crawler
		PolygonShape head = new PolygonShape();
		Vector2[] vertices = new Vector2[4];
		vertices[0] = new Vector2(-5, 5).scl(1 / PPM);
		vertices[1] = new Vector2(5, 5).scl(1 / PPM);
		vertices[2] = new Vector2(-3, 3).scl(1 / PPM);
		vertices[3] = new Vector2(3, 3).scl(1 / PPM);
		head.set(vertices);
		
		enemyFDef.shape = head;
		// bounciness. if value = 1, 100% bounce back. 10 pixels high, bounce back 10 pixels
		enemyFDef.restitution = 0.5f;
		enemyFDef.filter.categoryBits = B2DVars.BIT_ENEMY_HEAD;
		enemyFDef.filter.maskBits = B2DVars.BIT_GROUND | 
				B2DVars.BIT_BALL | 
				B2DVars.BIT_BOX;
		enemyBody.createFixture(enemyFDef).setUserData(this);
	}
	
	@Override
	public void hitOnHead() {
		setToDestroy = true;
	}
	
	
	public void draw(SpriteBatch batch) {
		if(!destroyed || stateTime < 1) { // 1 second to deletion of smushed sprite
			super.draw(batch);
		}
	}

}
