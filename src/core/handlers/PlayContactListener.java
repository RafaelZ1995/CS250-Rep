package core.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;


public class PlayContactListener implements ContactListener {

	private boolean playerOnGround;
	
	// Runs when two fixtures/objects collide
	@Override
	public void beginContact(Contact c) {
		// Contact is a class that knows two fixtures/objects collide
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		//System.out.println(fa.getUserData() + ", " + fb.getUserData());
		if (fa.getUserData().equals("foot") || fb.getUserData().equals("foot")) {
			playerOnGround = true;
		}
		
		/*
		if (fa.getUserData().equals("ball") || fb.getUserData().equals("ball")) {
			playerOnGround = true;
		}
		*/
	}

	@Override
	public void endContact(Contact c) {
		// TODO Auto-generated method stub
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		if (fa.getUserData().equals("foot") || fb.getUserData().equals("foot")) {
			playerOnGround = false;
		}
		
		/*
		if (fa.getUserData().equals("ball") || fb.getUserData().equals("ball")) {
			playerOnGround = false;
		}
		*/
	}

	public boolean isPlayerOnGround(){
		return playerOnGround;
	}
	
	@Override
	public void postSolve(Contact c, ContactImpulse ci) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact c, Manifold m) {
		// TODO Auto-generated method stub

	}

}