package main.input;

import java.awt.event.*;

public class KeyManager implements KeyListener{

	private boolean[] keys;
	public boolean up = false, down , left, right, e, space = false;
	
	
	public KeyManager() {
		keys = new boolean[256];
		
	}
	
	public void tick() {
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		right = keys[KeyEvent.VK_D];
		left = keys[KeyEvent.VK_A];
		space = keys[KeyEvent.VK_SPACE];
		e = keys[KeyEvent.VK_E];
	}
	
	public void keyPressed(KeyEvent e) {
		
		keys[e.getKeyCode()] = true;
	}

	
	public void keyReleased(KeyEvent e) {
		
		keys[e.getKeyCode()] = false;
		
	}

	public void keyTyped(KeyEvent arg0) {}

}
