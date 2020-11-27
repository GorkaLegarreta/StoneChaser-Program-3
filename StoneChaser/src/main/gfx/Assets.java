package main.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage player, inventory, trunk;
	
	public static void init() {
		
		SpriteSheet plyr = new SpriteSheet(ImageLoader.loadImage("/jugador.png")); 
		SpriteSheet inv = new SpriteSheet(ImageLoader.loadImage("/Inventory.png"));
		SpriteSheet trnk = new SpriteSheet(ImageLoader.loadImage("/trunk.png"));
		
		player = plyr.crop(5, 6, 31, 70);
		inventory = inv.crop(0, 0, 200, 186);
		trunk = trnk.crop(0, 0, 18, 20);
	}
	
}
