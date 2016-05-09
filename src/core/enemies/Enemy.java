package core.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import core.game.Game;
import core.handlers.B2DVars;
import core.states.PlayState;

/**
 * This abstract class will be inherited by all of the other enemy classes.
 * 
 * 
 */
public abstract class Enemy extends Sprite{
	protected World world;
	//protected PlayState playState;
	public Body enemyBody;
	private boolean right;
	
	public Vector2 velocity;
	
	/**
	protected int maxHealth; // Enemy's max health
	protected int currHealth; // Current health of enemy
	protected int damage; // Damage of the enemy upon contact
	protected boolean dead; // Is the enemy dead or alive
	
	protected BodyDef enemyBDef;
	protected FixtureDef enemyFDef;

	protected Body enemyBody;
	
	**/
	
	/**
	 * 
	 * @param x, x-position of the enemy
	 * @param y, y-position of the enemy
	 */
	public Enemy(World world, float x, float y) {
		this.world = world;
		setPosition(x, y);
		defineEnemy();
		velocity = new Vector2(1, 0);
	}
	
	protected abstract void defineEnemy();
	public abstract void update();
	public abstract void hitOnHead();
	
	public void reverseVelocity(boolean x, boolean y) {
		if(x) {
			velocity.x = -velocity.x;
		}
		if(y) {
			//System.out.println("vel: " + enemyBody.getLinearVelocity().y);
			velocity.x = -velocity.x;
		}
	}
	
	public void reverse(){
		//right = !right;
		velocity.x = -velocity.x;
	}
	
	public boolean getIsRight(){
		return right;
	}
	
	/**
	 * Sets the max hp of the enemy.
	 * @param hp
	 */
	/*public void setMaxHp(int hp) {
		maxHealth = hp;
	}*/
	
	/**
	 * Gets the max health of the enemy.
	 */
	/*public int getMaxHp() {
		return maxHealth;
	}*/
	
	/**
	 * Returns the current health point of the enemy.
	 * @return currHealth
	 */
	/*public int getCurrentHp() {
		return currHealth;
	}*/
	
	/**
	 * Updates the enemy's current health of the enemy is hit
	 * @param damage
	 */
	/*public void hit(int damage) {
		if (isDead()) { // Enemy already dead
			return;
		}
		currHealth -= damage;
		if (currHealth <= 0) {
			dead = true;
			currHealth = 0;
		}
	}*/
	
	/**
	 * Sets the damage of the enemy
	 * @param damage
	 */
	/*public void setDamage(int damage) {
		this.damage = damage;
	}*/
	
	/**
	 * Returns the damage of the enemy.
	 * @return damage
	 */
	/*public int getDamage() {
		return damage;
	}*/
	
	/**
	 * Checks if the enemy is dead or alive.
	 * @return boolean dead
	 */
	/*public boolean isDead() {
		return dead;
	}*/
	

}