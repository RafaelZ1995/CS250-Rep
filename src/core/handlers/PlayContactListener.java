package core.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import core.enemies.Crawler;
import core.enemies.Enemy;
import core.player.Player;
import core.universe.Door;
import core.universe.Sector;

/*
 * This class currently:
 * 		Checks if the player was in contact with a door. If so, tell the current Sector that it should change room.
 * 
 */
public class PlayContactListener implements ContactListener {

	private int featuresInContact;
	private Sector sector;

	// stores the last door we went into
	private String doorUsed;

	public PlayContactListener(Sector sector) {
		this.sector = sector;
	}

	// Runs when two fixtures/objects collide
	@Override
	public void beginContact(Contact c) {
		// Contact is a class that knows two fixtures/objects collide
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		int cDef = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;

		// either fixture might be null sometimes.
		if (fa == null || fb == null)
			return;
		if (fa.getUserData() == null || fb.getUserData() == null)
			return;

		checkPlayerOnGround(fa, fb);
		checkPlayerContactWithDoor(fa, fb);
		
		switch (cDef) {
		case B2DVars.BIT_ENEMY_HEAD | B2DVars.BIT_BOX:
			if(fa.getFilterData().categoryBits == B2DVars.BIT_ENEMY_HEAD) {
				((Enemy)fa.getUserData()).hitOnHead();
			}
			else if(fb.getFilterData().categoryBits == B2DVars.BIT_ENEMY_HEAD) {
				((Enemy)fb.getUserData()).hitOnHead();
			} 
			break;
		case B2DVars.BIT_ENEMY | B2DVars.BIT_ENEMY:
			if(fa.getFilterData().categoryBits == B2DVars.BIT_ENEMY) {
				((Enemy)fa.getUserData()).reverseVelocity(true, false);
				//((Enemy)fa.getUserData()).reverse();
			}
			else {
				((Enemy)fb.getUserData()).reverseVelocity(true, false);
				//((Enemy)fb.getUserData()).reverse();
				

			}
			break;
			
			
		case B2DVars.BIT_BOX | B2DVars.BIT_ENEMY: 
			if(fa.getUserData() instanceof Player) {
				Player player = (Player)fa.getUserData();
				if (!player.isDashing())
					player.setHealth(((Crawler)fb.getUserData()).getDMG());
				else{ // if dashing
					Crawler cr = (Crawler)fb.getUserData();
					cr.enemyBody.applyLinearImpulse(0, 5, cr.getX(), cr.getY(), true);
				}
			}
			else if(fb.getUserData() instanceof Player) {
				Player player = (Player)fb.getUserData();
				if (!player.isDashing())
					player.setHealth(((Crawler)fa.getUserData()).getDMG());
				else{ // if dashing
					Crawler cr = (Crawler)fa.getUserData();
					cr.enemyBody.applyLinearImpulse(0, 5, cr.getX(), cr.getY(), true);
				}
			}
			//Gdx.app.log("USER", "DAMAGED");
			break;
			
		}

	}

	private void checkPlayerOnGround(Fixture fa, Fixture fb) {
		if (fa.getUserData().equals("foot")) {
			featuresInContact++;
		}
		if(fb.getUserData().equals("foot")) {
			featuresInContact++;
		}
		
	}
	
	private void checkPlayerLeavingGround(Fixture fa, Fixture fb) {
		if (fa.getUserData().equals("foot")) {
			featuresInContact--;
			
		}
		if(fb.getUserData().equals("foot")) {
			featuresInContact--;
		}
		
	}

	private void checkPlayerContactWithDoor(Fixture fa, Fixture fb) {
		if (fa.getUserData() instanceof Player || fb.getUserData() instanceof Player) {
			if (fa.getUserData() instanceof Door || fb.getUserData() instanceof Door) {

				if (fa.getUserData() instanceof Door) {
					Door door = (Door) fa.getUserData();
					// tell sector to change room
					sector.setRoomToChangeTo(door.to());
					// tell sector which door player should come out of
					doorUsed = door.getDirection();
				} else {
					Door door = (Door) fb.getUserData();
					sector.setRoomToChangeTo(door.to());
					doorUsed = door.getDirection();
				}
			}
		}

	}

	@Override
	public void endContact(Contact c) {
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		if (fa == null || fb == null)
			return;
		if (fa.getUserData() == null || fb.getUserData() == null)
			return;
		
		checkPlayerLeavingGround(fa, fb);
	}

	@Override
	public void postSolve(Contact c, ContactImpulse ci) {

	}

	@Override
	public void preSolve(Contact c, Manifold m) {

	}

	// GETTERS
	public boolean isPlayerOnGround() {
		return featuresInContact > 0;
	}

	public String getDoorUsed() {
		return doorUsed;
	}

}