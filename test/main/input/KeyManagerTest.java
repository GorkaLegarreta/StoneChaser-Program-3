package main.input;

import static org.junit.Assert.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import org.junit.Before;
import org.junit.Test;

public class KeyManagerTest {
	
	private boolean[] keys = new boolean[256];
	public boolean up, down, left,  right, e, f, r, space = false;
	public boolean pause = false;
	private KeyEvent ke;
	
	@Before 
	public void gameIsRunning() {
		pause = keys[KeyEvent.VK_P];
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		right = keys[KeyEvent.VK_D];
		left = keys[KeyEvent.VK_A];
		space = keys[KeyEvent.VK_SPACE];
		e = keys[KeyEvent.VK_E];
		f = keys[KeyEvent.VK_F];
		r = keys[KeyEvent.VK_R];
	}
	@Test
	public void TestPauseUnpause() {
		
	}
	@Test
	public void TestStoppedWhenPaused() {
		
	}

}
