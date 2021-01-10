package main.utilities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.Game;
import main.Handler;
import main.gfx.Assets;
import main.inventory.Inventory;
import main.items.Item;

public class PositionTest {

	Game game;
	Handler handler;
	Inventory inv;
	Position pos;
	Item madera;
	
	@Before
	public void setUp() {
		
		game = new Game("StoneChaser", 700, 400);
		handler = new Handler(game);
		inv = new Inventory(handler);
		madera = new Item("madera", Assets.trunk, (int) (18*2), (int) (20*2), 0, handler, inv);
		
		pos = new Position(1, 2);
	}
	
	@Test
	public void testGetX() {
		assertEquals(1, pos.getX());
	}
	
	@Test
	public void testGetY() {
		assertEquals(2, pos.getY());
	}
	
	@Test
	public void testSetInvPosition() {
		int returnedPos = pos.setInvPosition(madera).getX() + pos.setInvPosition(madera).getY();	//(x + 22 - item.getWidth/2) + (x + 21 - item.getHeight/2)
		assertEquals(8, returnedPos);																//(1 + 22 - 18) + (2 + 21 - 20)
	}
	
	@Test
	public void testSetCraftPosition() {
		int returnedPos = pos.setCraftPosition(madera).getX() + pos.setCraftPosition(madera).getY();	//(x + 31 - item.getWidth/2) + (x + 30 - item.getHeight/2)
		assertEquals(26, returnedPos);																//(1 + 31 - 18) + (2 + 30 - 20)
		
	}
	
	@Test
	public void testSetOutcomePosition() {
		int returnedPos = pos.setOutcomePosition(madera).getX() + pos.setOutcomePosition(madera).getY();	//(x + 30 - item.getWidth/2) + (x + 30 - item.getHeight/2)
		assertEquals(25, returnedPos);																	//(1 + 30 - 18) + (2 + 30 - 20)
	}
	
}
