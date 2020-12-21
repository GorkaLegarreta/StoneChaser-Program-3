package main.input;

import java.awt.event.*;

import main.states.GameState;

public class KeyManager implements KeyListener{

	private boolean[] keys;
	public boolean up, down, left,  right, e, f, r, space = false;
	public boolean pause = false;
	private float ratio, update;
	private long now, before = System.nanoTime();
	
	public KeyManager() {
		keys = new boolean[256];		
	}
	
	public void tick() {
		// SE COMPRUEBA SI SE ESTAN PULSANDO LAS TECLAS
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
		/*
		 * While game is unPaused()
		 */
		
		if(!pause) { 
			keys[e.getKeyCode()] = true;
		} else {
			keys[e.getKeyCode()] = false;
		}
		/*
		 * buttonP to pause() or unPause() game
		 */
		if (e.getKeyCode() == KeyEvent.VK_P && !GameState.currentlySaving) {
			if (pause) {
				pause = false;
			} else {
				pause = true;
			}
		}
		
	}	
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;				
	}
	public void keyTyped(KeyEvent arg0) {
		
	}
	

	
//	ratio = 1000000000/4;
//	
//	now = System.nanoTime();			
//	update += (now - before)/ratio;									
//	
//	if(update >= 1) {
//		before = System.nanoTime();
//		update--;				
//		if(keys[e.getKeyCode()] != true) keys[e.getKeyCode()] = true;
//		
//	}
//}
}
