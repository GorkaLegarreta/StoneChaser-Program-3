package main.inventory;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.Test;

import main.Game;
import main.Handler;
import main.items.Item;
import main.items.ItemManager;

public class InventoryTest {
	
	@Test
	public void testAddToInventory() {
		
		boolean result;
		
		Game game = new Game("Stonechaser", 700, 400);
		Handler handler = new Handler(game);
		Inventory inv = new Inventory(handler);
		
		inv.addToInventory(ItemManager.madera);
		
		if(inv.lastGathered.getName().equals("madera"))result = true;
		else result = false;
		
		assertEquals(result, true);
		
	}

	
}
