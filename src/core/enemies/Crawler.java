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

public class Crawler extends Enemy {

	private float stateTime; // do we need this
	private Animation walkAnimation;
	private Array<TextureRegion> frames;
	private boolean setToDestroy;
	private boolean destroyed;
	private Game game;
	private final int DMG = 1;
	
	

	public Crawler(World world, float x, float y, Game game) {
		super(world, x, y);
		frames = new Array<TextureRegion>();
		setToDestroy = false;
		destroyed = false;
		this.game = game;
		frames.add(new TextureRegion(Game.res.getTexture("crawler")));
		frames.add(new TextureRegion(Game.res.getTexture("crawler2")));
		// frames.add(new TextureRegion(Game.res.getTexture("spikyCrawler2")));

		walkAnimation = new Animation(0.4f, frames);
		stateTime = 0;
		

		// setBounds(getX(), getY(), 16 / PPM, 16 / PPM); // boundaries of the
		// sprite

	}

	public void update() {
		 stateTime += 0.01;
		if (setToDestroy && !destroyed) {
			world.destroyBody(enemyBody);
			destroyed = true;
			// setRegion(new TextureRegion(region)); smushed goomba sprite
			 stateTime = 0;
		} else if (!destroyed) {
			enemyBody.setLinearVelocity(velocity.x, enemyBody.getLinearVelocity().y);
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
		shape.setAsBox(10 / PPM, 10 / PPM);

		FixtureDef enemyFDef = new FixtureDef();
		enemyFDef.filter.categoryBits = B2DVars.BIT_ENEMY;
		enemyFDef.filter.maskBits = B2DVars.BIT_GROUND | B2DVars.BIT_BALL | B2DVars.BIT_BOX | B2DVars.BIT_ENEMY;
		enemyFDef.shape = shape;
		enemyBody.createFixture(enemyFDef).setUserData(this);

		// Create Head of crawler
		PolygonShape head = new PolygonShape();
		head.setAsBox(7 / PPM, 4 / PPM, new Vector2(0, 13 / PPM), 0);

		enemyFDef.shape = head;
		// bounciness. if value = 1, 100% bounce back. 10 pixels high, bounce
		// back 10 pixels
		enemyFDef.restitution = 0.5f;
		enemyFDef.filter.categoryBits = B2DVars.BIT_ENEMY_HEAD;
		enemyFDef.filter.maskBits = B2DVars.BIT_GROUND | B2DVars.BIT_BALL | B2DVars.BIT_BOX;
		enemyBody.createFixture(enemyFDef).setUserData(this);
	}

	@Override
	public void hitOnHead() {
		setToDestroy = true;
	}

	public void hitByEnemy(Enemy enemy) {
		if (enemy instanceof Crawler) {
			reverseVelocity(true, false);
		}

	}

	public void render() {
		if (!destroyed) {
			SpriteBatch sb = game.getSb();
			sb.setProjectionMatrix(game.getCam().combined);
			sb.begin();
			sb.draw(walkAnimation.getKeyFrame(stateTime, true), enemyBody.getPosition().x * PPM - 16,
					enemyBody.getPosition().y * PPM - 12, 32, 32);
			sb.end();
			
		}

	}

	public void draw(SpriteBatch batch) {
		if (!destroyed || stateTime < 1) { // 1 second to deletion of smushed
											// sprite
			super.draw(batch);
		}
	}
	
	public int getDMG() {
		return DMG;
	}

}
