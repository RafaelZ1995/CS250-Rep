package core.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import core.game.Game;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import core.handlers.GameStateManager;
import core.hud.Background;

//import com.neet.main.Game;
//import com.neet.managers.GameKeys;
//import com.neet.managers.GameStateManager;

public class MenuState extends GameState {

	private final int playstate = 0;
	private final int exit = 1;

	private SpriteBatch sb;

	private BitmapFont titleFont;
	private BitmapFont font;

	private final String title = "unTiled";

	private int currentItem;
	private String[] menuItems;

	private GameStateManager gsm;

	private Background bg;

	public MenuState(GameStateManager gsm) {
		super(gsm);
		this.gsm = gsm;

		init();
	}

	public void init() {

		sb = gsm.getGame().getSb();

		font = new BitmapFont();
		titleFont = new BitmapFont();

		menuItems = new String[] { "New Game", "Quit" };

		bg = new Background(gsm);
	}

	public void update(float dt) {

		handleInput();
	}

	@Override
	public void render() {
		// Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		bg.render();
		sb.setProjectionMatrix(gsm.getGame().getHudcam().combined);

		sb.begin();

		float width = 40;
		
		// draw menu
		// int[] widthArr = new int[menuItems.length];
		for (int i = 0; i < menuItems.length; i++) {
			float distance = 80;
			float x = (gsm.getGame().V_WIDTH) / 2 + 50;
			float y = 180 - distance * i;

			if (currentItem == i) {
				sb.draw(Game.res.getPickup("life"), x - 50, y, 40, 40);
			}

			if (i == 0)
				sb.draw(Game.res.getTexture("newgame"), x, y, 240, 40);

			if (i == 1)
				sb.draw(Game.res.getTexture("exit"), x, y, 150, 40);
		}
		sb.end();
	}

	public void handleInput() {

		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			if (currentItem > 0)
				currentItem--;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			if (currentItem < menuItems.length - 1)
				currentItem++;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			select();
		}
	}

	private void select() {
		// play
		if (currentItem == playstate) {
			gsm.setState(GameStateManager.PLAYSTATE);
		} else if (currentItem == exit) {
			Gdx.app.exit();
		}
	}

	public void dispose() {
		font.dispose();
		titleFont.dispose();
	}

}
