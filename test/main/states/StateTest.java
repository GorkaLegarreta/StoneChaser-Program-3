package main.states;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import main.Game;
import main.GameDBException;
import main.Handler;

public class StateTest {
	
	
	
	@BeforeClass
	public static void beforeClass() {
		System.out.println("Before Class");	
		Game game = new Game("StoneChaser", 700, 400);
		Handler handler = new Handler(game);
//		MenuState menuState = new MenuState(handler);
//		GameState gameState = new GameState(handler);
	}
	@Before
	public void before() {
		System.out.println("Before");
		Game game = new Game("StoneChaser", 700, 400);
		Handler handler = new Handler(game);
//		MenuState menuState = new MenuState(handler);
//		GameState gameState = new GameState(handler);
	}
	@Test
	public void gameStateTest() throws GameDBException {
		System.out.println("Test");
		Game game = new Game("StoneChaser", 700, 400);
		Handler handler = new Handler(game);
		GameState gameState = new GameState(handler);
		//assertEquals(gameState,handler.getGame().gameState);
	}

	@Test
	public void menuStateTest() {		
		System.out.println("Test");
		Game game = new Game("StoneChaser", 700, 400);
		Handler handler = new Handler(game);
		MenuState menuState = new MenuState(handler);
		//assertEquals(menuState,handler.getGame().menuState);
	}
	
	@After
	public void after() throws GameDBException {
		System.out.println("After");
		Game game = new Game("StoneChaser", 700, 400);
		Handler handler = new Handler(game);
		MenuState menuState = new MenuState(handler);
		GameState gameState = new GameState(handler);
	}
	
	@AfterClass
	public static void afterClass() throws GameDBException {
		System.out.println("AfterClass");
		Game game = new Game("StoneChaser", 700, 400);
		Handler handler = new Handler(game);
		MenuState menuState = new MenuState(handler);
		GameState gameState = new GameState(handler);
	}
}
