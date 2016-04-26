package core.handlers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class MyInputProcessor extends InputAdapter {
	
	boolean direction;
	
	public boolean keyDown(int k) {
		if(k == Keys.A) {
			MyInput.setKey(MyInput.LEFT, true);
		}
		
		if(k == Keys.D) {
			MyInput.setKey(MyInput.RIGHT, true);
		}
		
		if(k == Keys.W) {
			MyInput.setKey(MyInput.UP, true);
		}
		
		if(k == Keys.S) {
			MyInput.setKey(MyInput.DOWN, true);
		}
		
		if(k == Keys.SPACE) {
			MyInput.setKey(MyInput.JUMP, true);
		}
		
		if(k == Keys.K) {
			MyInput.setKey(MyInput.DASH, true);
		}
		
		return true;
	}
	
	public boolean keyUp(int k) {
		if(k == Keys.A) {
			MyInput.setKey(MyInput.LEFT, false);
		}
		if(k == Keys.D) {
			MyInput.setKey(MyInput.RIGHT, false);
		}
		
		if(k == Keys.W) {
			MyInput.setKey(MyInput.UP, false);
		}
		
		if(k == Keys.S) {
			MyInput.setKey(MyInput.DOWN, false);
		}
		
		if(k == Keys.SPACE) {
			MyInput.setKey(MyInput.JUMP, false);
		}
		
		if(k == Keys.K) {
			MyInput.setKey(MyInput.DASH, false);
		}
		
		return true;
	}
	
}

