package main.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage player, inventory, trunk, stone, inventorySlots, worldTest;
	
	public static void init() {
		
		SpriteSheet plyr = new SpriteSheet(ImageLoader.loadImage("/jugador.png")); 
		SpriteSheet inv = new SpriteSheet(ImageLoader.loadImage("/Inventory.png"));
		SpriteSheet trnk = new SpriteSheet(ImageLoader.loadImage("/trunk.png"));
		SpriteSheet stn = new SpriteSheet(ImageLoader.loadImage("/stone.png"));
		SpriteSheet invSlots = new SpriteSheet(ImageLoader.loadImage("/inventorySlots.png"));
		SpriteSheet wrldTst = new SpriteSheet(ImageLoader.loadImage("/worldTest.png"));
		
		player = plyr.crop(5, 6, 31, 70);
		inventory = inv.crop(0, 0, 200, 186);
		trunk = trnk.crop(0, 0, 18, 20);
		stone = stn.crop(0, 0, 17, 10);
		inventorySlots = invSlots.crop(0, 0, 219, 75);
		worldTest = wrldTst.crop(0, 0, 700, 400);
		
	}
	
}
