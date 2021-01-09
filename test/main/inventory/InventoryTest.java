package main.inventory;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.Game;
import main.Handler;
import main.gfx.Assets;
import main.items.Item;
import main.worlds.World;

public class InventoryTest {
	
	Game game;
	Handler handler;
	Inventory inv;
	Item madera;
	World world;
	
	@Before
	public void setUp() {
		game = new Game("Stonechaser", 700, 400);
		handler = new Handler(game);
		inv = new Inventory(handler);
		madera = new Item("madera", Assets.trunk, (int) (18*2), (int) (20*2), 0, handler, inv);
		world = new World(handler);
	}
	
	@Test
	public void testAddToInventory() {
		
		inv.addToInventory(madera);
		assertEquals(madera, world.getInventory().getItemArray()[0]);
		
	}

}
