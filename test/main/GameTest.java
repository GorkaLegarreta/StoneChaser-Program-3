package main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	private Game game;
	
	@Before
	public void setUp() {
		game = new Game("StoneChaser", 700, 400);
	}
	
	@Test
	public void getWidthTest() {
		assertEquals(700, game.getWidth());
	}

}
