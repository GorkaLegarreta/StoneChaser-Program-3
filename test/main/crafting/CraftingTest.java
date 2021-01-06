package main.crafting;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.Game;
import main.Handler;
import main.gfx.Assets;
import main.inventory.Inventory;
import main.items.Item;

public class CraftingTest {

	private Game game;
	private Handler handler;
	private Crafting crafting;
	private Inventory inv;
	private Item palo, hierro;
	private Item[] crafteo;
	
	@Before
	public void setUp() {
		game = new Game("StoneChaserTest", 700, 400);
		handler = new Handler(game);
		crafting = new Crafting(handler);
		inv = new Inventory(handler);
		
		palo = new Item("palo", Assets.palo, (int) (18*2), (int) (19*2), 5, handler, inv);
		hierro = new Item("hierro", Assets.hierro, (int) (17*2), (int) (20*2), 4, handler, inv);
			
		crafteo = crafting.getCrafteo();
		
	}
	
//	@Test
//	public void testCraft() {
//			
//		
//	}
//	
//	@Test
//	public void testCheckItemQuantities() {
//			
//		
//	}
	
	@Test
	public void testToBeDecreased() {
		
		crafteo[4] = palo.createItem(340, 220, 20);
		crafteo[7] = hierro.createItem(320, 170, 40);
		
		crafting.toBeDecreased(1);
		
		assertEquals(19, crafteo[4].getItemQuantity());
		assertEquals(18, crafteo[7].getItemQuantity());
		
	}

}
