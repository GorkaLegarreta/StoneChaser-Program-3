package main.states;

import static org.junit.Assert.*;

import java.awt.Graphics;
import org.junit.Before;
import org.junit.Test;
import main.Game;
import main.GameDBException;
import main.Handler;

public class StateTest {
	
	private State state = null;
	private State menu;
	private State game;
	private Game stoneChaser;
	private Handler handler;
	
	@Before
	public void prepare() {
		stoneChaser = new Game("", 100, 100);
		handler = new Handler(stoneChaser);
		menu = new State(handler) {			
			@Override
			public void tick() throws GameDBException {}			
			@Override
			public void render(Graphics g) {}
		};
		game = new State(handler) {			
			@Override
			public void tick() throws GameDBException {}			
			@Override
			public void render(Graphics g) {}
		};
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void TestSetState() {		
		state.setState(menu);
		assertEquals(stoneChaser.menuState, state);
		state.setState(game);
		assertEquals(stoneChaser.gameState, state);
	}
}
