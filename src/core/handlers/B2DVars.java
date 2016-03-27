package core.handlers;

public class B2DVars {

	// box2d works better with fixed time steps
	public static final float STEP = 1 / 60f;

	// pixels per meter
	public static final float PPM = 100;

	// categorybits
	public static final short BIT_GROUND = 2;
	public static final short BIT_BOX = 4;
	public static final short BIT_BALL = 9;
}
