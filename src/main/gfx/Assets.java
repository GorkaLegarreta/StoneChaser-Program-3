package main.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage player, inventory, trunk, stone, inventorySlots, worldTest, inventarioPlegado, inventarioDesplegado;
	
	public static void init() {
		
		SpriteSheet plyr = new SpriteSheet(ImageLoader.loadImage("/textures/jugador.png")); 
		SpriteSheet inv = new SpriteSheet(ImageLoader.loadImage("/textures/Inventory.png"));
		SpriteSheet trnk = new SpriteSheet(ImageLoader.loadImage("/textures/trunk.png"));
		SpriteSheet stn = new SpriteSheet(ImageLoader.loadImage("/textures/stone.png"));
		SpriteSheet invSlots = new SpriteSheet(ImageLoader.loadImage("/textures/inventorySlots.png"));
		SpriteSheet invplgdo = new SpriteSheet(ImageLoader.loadImage("/textures/inventarioPlegado.png"));
		SpriteSheet invdesplgdo = new SpriteSheet(ImageLoader.loadImage("/textures/inventarioDesplegado.png"));
		SpriteSheet wrldTst = new SpriteSheet(ImageLoader.loadImage("/textures/worldTest.png"));
		
		player = plyr.crop(5, 6, 31, 70);
		inventory = inv.crop(0, 0, 200, 186);
		inventarioPlegado = invplgdo.crop(0, 0, 142, 61);
		inventarioDesplegado = invdesplgdo.crop(0, 0, 384, 61);
		trunk = trnk.crop(0, 0, 18, 20);
		stone = stn.crop(0, 0, 17, 10);
		inventorySlots = invSlots.crop(0, 0, 219, 75);
		worldTest = wrldTst.crop(0, 0, 700, 400);
		
	}
	
}
