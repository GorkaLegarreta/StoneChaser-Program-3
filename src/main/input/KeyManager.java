package main.input;

import java.awt.event.*;

import main.states.GameState;

public class KeyManager implements KeyListener{

	private static boolean[] keys;
	public boolean up, down, left,  right, e, f, r, space = false;
	public static boolean pause = false;
	
	public KeyManager() {
		keys = new boolean[256];		
	}
	
	public void tick() {
		// SE COMPRUEBA SI SE ESTAN PULSANDO LAS TECLAS SOLO SI NO ESTA EN PAUSE()
		if(!pause) {
			up = keys[KeyEvent.VK_W];
			down = keys[KeyEvent.VK_S];
			right = keys[KeyEvent.VK_D];
			left = keys[KeyEvent.VK_A];
			space = keys[KeyEvent.VK_SPACE];
			e = keys[KeyEvent.VK_E];
			f = keys[KeyEvent.VK_F];
			r = keys[KeyEvent.VK_R];
		} else {
			up = false; down = false; left = false;  right = false; e = false; f = false; r = false; space = false;
		}
	}
	/////////////////////////////////////////////////////////////////
	//				METODOS DE KEY LISTENER						   //
	/////////////////////////////////////////////////////////////////
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;				
	}
	public void keyTyped(KeyEvent e) {
		
	}
	public void keyPressed(KeyEvent e) {
		pauseUnPause(e);		
		stoppedWhenPaused(e);	
	}
	/////////////////////////////////////////////////////////////////
	//				METODOS PARA SIMPLIFICAR CODIGO				   //
	/////////////////////////////////////////////////////////////////
	public static void pauseUnPause(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_P) {
			if (pause) {
				pause = false;
			} else {
				pause = true;
			}
		}
	}	
	private static void stoppedWhenPaused(KeyEvent ke) {
		if(pause) { 
			keys[ke.getKeyCode()] = false;			
		} else {
			keys[ke.getKeyCode()] = true;			
		}
	}
}
