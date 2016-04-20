package core.universe;

import static core.handlers.B2DVars.PPM;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import core.handlers.B2DVars;

/**
 * @author Rafael
 *
 *
 *         Okay soo Doors...
 *
 *         First: Tmx files are the ones that have hard coded what room each
 *         door leads to. Each door also has a name. For now, they are either
 *         leftDoor (if they are on the left side of the map) or rightDoor (if
 *         they are on the right side of the map)
 * 
 *         Thus: In the Room class, we store each door in a hashtable<String
 *         (name of door), Door (actual instance of door)>. When the
 *         ContactListener recognizes a contact between the player and a door,
 *         it will save the name of the door in an instance variable called
 *         doorUsed. After Contact listener does his thing, The Sector class
 *         will now know it has to change the room. At this point, it will
 *         proceed to figure out which door to come out of in the new room. It
 *         will do so by, getting the instance of the door it should appear in
 *         front of, from the hashtable<String, Door> in the new Room. To update
 *         players position, Sector calls the Door method getPlayerSpawn which
 *         returns a Vector2 a bit infront of the Door.
 * 
 *         For example, if usedDoor = "leftDoor", then we use the
 *         hashtable<String, Door> doortable, to get doortable.get("rightDoor").
 *         Because if we go into a door in the left, we should appear in a door
 *         in the right in the new room.
 * 
 * 
 */
public class Door {

	private Body doorBody;

	// id 'to' refers to. which should be the number that refers to the tmx file
	// in TmxAssets array.
	int id;

	// room it leads to.
	private int to;

	private String name;

	// position
	float width, height;

	// location
	float x, y;
	
	// Shape
	Rectangle doorShape;

	// where player should spawn if it comes out of this door
	Vector2 playerSpawn;

	public Door(World world, RectangleMapObject rectObject) {
		this.doorShape = rectObject.getRectangle();
		this.width = doorShape.width;
		this.height = doorShape.height;
		this.x = doorShape.x;
		this.y = doorShape.y;
		this.name = rectObject.getName();

		// bdef
		BodyDef bdef = new BodyDef();
		// divide by 2, because libgdx doubles the value from tile
		bdef.position.set(x / PPM, (y + height / 2) / PPM);
		bdef.type = BodyType.StaticBody;

		// body
		doorBody = world.createBody(bdef);

		// shape
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / PPM, height / 2 / PPM);

		// fdef
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_BOX;
		fdef.isSensor = true;

		// fixture
		Fixture fixture = doorBody.createFixture(fdef);
		fixture = doorBody.createFixture(fdef);
		fixture.setUserData(this);
		// or all in one line
		// doorBody.createFixture(fdef).setUserData();

		// room it leads to
		to = Integer.parseInt((String) rectObject.getProperties().get("to"));

		// createplayer spawn
		if (name.equals("leftDoor")) {
			playerSpawn = new Vector2(doorBody.getPosition().x + 0.2f, doorBody.getPosition().y); // + 0.2f so that it doesnt
													// spawn on door
		} else if (name.equals("rightDoor")) {
			playerSpawn = new Vector2(doorBody.getPosition().x - 0.2f, doorBody.getPosition().y);
		} else {
			System.out.println("player spawn is NULL");
			playerSpawn = null;
		}
	}

	public Vector2 getPlayerSpawn() {
		return playerSpawn;
	}

	public String getDirection() {
		return name;
	}

	public int to() {
		return to;
	}
}
