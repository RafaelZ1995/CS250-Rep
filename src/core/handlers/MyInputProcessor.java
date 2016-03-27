package core.handlers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class MyInputProcessor extends InputAdapter {
	
	public boolean keyDown(int k) {
		if(k == Keys.A) {
			MyInput.setKey(MyInput.LEFT, true);
			left = true;
			System.out.println("A pressed " + left);
		}
		
		if(k == Keys.D) {
			MyInput.setKey(MyInput.RIGHT, true);
			right = true;
			System.out.println("D pressed " + right);
		}
		
		if(k == Keys.W) {
			MyInput.setKey(MyInput.UP, true);
			up = true;
			System.out.println("W pressed " + up);
		}
		
		if(k == Keys.S) {
			MyInput.setKey(MyInput.DOWN, true);
			down = true;
			System.out.println("S pressed " + down);
		}
		
		if(k == Keys.SPACE) {
			MyInput.setKey(MyInput.JUMP, true);
			jump = true;
			System.out.println("Spacebar pressed " + jump);
		}
		
		return true;
	}
	
	public boolean keyUp(int k) {
		if(k == Keys.A) {
			MyInput.setKey(MyInput.LEFT, false);
			left = false;
			System.out.println("A released " + left);
		}
		if(k == Keys.D) {
			MyInput.setKey(MyInput.RIGHT, false);
			right = false;
			System.out.println("D released " + right);
		}
		
		if(k == Keys.W) {
			MyInput.setKey(MyInput.UP, false);
			up = false;
			System.out.println("W released " + up);
		}
		
		if(k == Keys.S) {
			MyInput.setKey(MyInput.DOWN, false);
			down = false;
			System.out.println("S released " + down);
		}
		
		if(k == Keys.SPACE) {
			MyInput.setKey(MyInput.JUMP, false);
			jump = false;
			System.out.println("Spacebar released " + jump);
		}
		
		return true;
	}
	
}

