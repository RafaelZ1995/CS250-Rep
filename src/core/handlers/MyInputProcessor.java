package core.handlers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class MyInputProcessor extends InputAdapter {
	
	private boolean left, right, up, down, jump;
	
	public boolean keyDown(int k) {
		if(k == Keys.A) {
			MyInput.setKey(MyInput.BUTTON_A, true);
			left = true;
			//System.out.println("A pressed " + left);
		}
		if(k == Keys.D) {
			MyInput.setKey(MyInput.BUTTON_D, true);
			right = true;
			//System.out.println("D pressed " + right);
		}
		
		if(k == Keys.W) {
			MyInput.setKey(MyInput.BUTTON_W, true);
			up = true;
			//System.out.println("W pressed " + up);
		}
		
		if(k == Keys.S) {
			MyInput.setKey(MyInput.BUTTON_S, true);
			down = true;
			//System.out.println("S pressed " + down);
		}
		
		if(k == Keys.SPACE) {
			MyInput.setKey(MyInput.BUTTON_SPACEBAR, true);
			jump = true;
			//System.out.println("Spacebar pressed " + jump);
		}
		
		return true;
	}
	
	public boolean keyUp(int k) {
		if(k == Keys.A) {
			MyInput.setKey(MyInput.BUTTON_A, false);
			left = false;
			//System.out.println("A released " + left);
		}
		if(k == Keys.D) {
			MyInput.setKey(MyInput.BUTTON_D, false);
			right = false;
			//System.out.println("D released " + right);
		}
		
		if(k == Keys.W) {
			MyInput.setKey(MyInput.BUTTON_W, false);
			up = false;
			//System.out.println("W released " + up);
		}
		
		if(k == Keys.S) {
			MyInput.setKey(MyInput.BUTTON_S, false);
			down = false;
			//System.out.println("S released " + down);
		}
		
		if(k == Keys.SPACE) {
			MyInput.setKey(MyInput.BUTTON_SPACEBAR, false);
			jump = false;
			//System.out.println("Spacebar released " + jump);
		}
		
		return true;
	}
	
	public boolean getLeft() {
		return left;
	}

	public boolean getRight() {
		return right;
	}

	public boolean getUp() {
		return up;
	}

	public boolean getDown() {
		return down;
	}

	public boolean getJump() {
		return jump;
	}
	
}

