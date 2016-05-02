package core.handlers;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import core.game.Game;

public class MusicHandler implements ApplicationListener{

	private Music music;
	private com.badlogic.gdx.audio.Sound sound;
	private PlayContactListener cl;
	private GameStateManager gsm;
	private MyInput myInput;
	
	public MusicHandler(GameStateManager gsm) {
		
		this.cl = Game.universe.getCurrentSector().getContactListener();
		this.gsm = gsm;
		create();
	}
	
	@Override
	public void create() {
		
		music = Gdx.audio.newMusic(Gdx.files.internal("Sound/Dreams of Above - Maze Master.mp3"));
		sound = Gdx.audio.newSound(Gdx.files.internal("Sound/Jumping.mp3"));
		
		music.setLooping(true); // music will loop
		music.setVolume(0.5f);
		music.play();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {
		
		// play sound is W or SPACEBAR is pressed 
		if(myInput.isPressed(4) == true && cl.isPlayerOnGround()) {
			sound.play(0.25f);
		}
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
		music.dispose();
		sound.dispose();
		
	}

}
