package main.input;

import java.awt.event.*;

public class KeyManager implements KeyListener{

	private boolean[] keys;
	public boolean up = false, down , left, right, e, f, r, space = false;
	private float ratio, update;
	private long now, before = System.nanoTime();
	
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
		f = keys[KeyEvent.VK_F];
		r = keys[KeyEvent.VK_R];
	}
	
	public void keyPressed(KeyEvent e) {
		
		keys[e.getKeyCode()] = true;
		
//		ratio = 1000000000/4;
//		
//		now = System.nanoTime();			
//		update += (now - before)/ratio;									
//		
//		if(update >= 1) {
//			before = System.nanoTime();
//			update--;				
//			if(keys[e.getKeyCode()] != true) keys[e.getKeyCode()] = true;
//			
//		}
	}

	
	public void keyReleased(KeyEvent e) {
		
		keys[e.getKeyCode()] = false;		
		
	}

	public void keyTyped(KeyEvent arg0) {}

}
