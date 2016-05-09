package core.player;

import static core.handlers.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import core.game.Game;
import core.handlers.B2DVars;
import core.savesystem.SaveConfiguration;

public class DummyPlayer {

	private Body playerBody;
	
	public DummyPlayer() {
		// create box player
		BodyDef bdef = new BodyDef();
		bdef.position.set(160 / PPM, 200 / PPM);
		bdef.type = BodyType.DynamicBody;
		
		// currently there is no world. because Sectors are only
		// initialized once, so when we dispose them, how do we get them 
		// back?
		playerBody = Game.universe.getCurrentSector().getWorld().createBody(bdef);
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
	
	public void handleInput(){
		if(Gdx.input.isKeyPressed(Keys.LEFT))
			playerBody.applyForceToCenter(-7, 0, true);
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
			playerBody.applyForceToCenter(7, 0, true);
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			playerBody.applyForceToCenter(0, 20, true);
		}
	}
	
	public Body getPlayerBody(){
		return playerBody;
	}

}
