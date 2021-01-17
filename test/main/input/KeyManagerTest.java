package main.input;

import static org.junit.Assert.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class KeyManagerTest implements KeyListener {
	
	private KeyManager manager;
	private KeyEvent pauseEvent;
	private boolean pause;
	private boolean[] keys;
	
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	
	
	
	
	@BeforeClass
	public void setUp() {
		manager = new KeyManager();	
	}
	@Test
	public void testPauseUnpause() {}
	
	
	
	
	
//	@Before 
//	public void gameIsRunning() {
//		pause = keys[KeyEvent.VK_P];
//		up = keys[KeyEvent.VK_W];
//		down = keys[KeyEvent.VK_S];
//		right = keys[KeyEvent.VK_D];
//		left = keys[KeyEvent.VK_A];
//		space = keys[KeyEvent.VK_SPACE];
//		e = keys[KeyEvent.VK_E];
//		f = keys[KeyEvent.VK_F];
//		r = keys[KeyEvent.VK_R];
//	}
//	@Test
//	public void TestPauseUnpause() {
//		
//	}
//	@Test
//	public void TestStoppedWhenPaused() {
//		
//	}
	

}
