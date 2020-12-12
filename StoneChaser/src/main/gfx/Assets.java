package main.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage player_StandStill, inventory, trunk, stone, inventorySlots, worldTest, bottomInterface;
	public static BufferedImage[] playerRight, playerLeft;
	
	public static void init() {
		
		SpriteSheet plyr = new SpriteSheet(ImageLoader.loadImage("/textures/jugador.png"));
		SpriteSheet plyr1 = new SpriteSheet(ImageLoader.loadImage("/textures/jugador1.png")); 
		SpriteSheet plyr2 = new SpriteSheet(ImageLoader.loadImage("/textures/jugador2.png")); 
		SpriteSheet plyr3 = new SpriteSheet(ImageLoader.loadImage("/textures/jugador3.png")); 
		SpriteSheet plyr4 = new SpriteSheet(ImageLoader.loadImage("/textures/jugador4.png")); 
		SpriteSheet plyr5 = new SpriteSheet(ImageLoader.loadImage("/textures/jugador5.png")); 
		SpriteSheet plyr6 = new SpriteSheet(ImageLoader.loadImage("/textures/jugador6.png")); 
		SpriteSheet inv = new SpriteSheet(ImageLoader.loadImage("/textures/Inventory.png"));
		SpriteSheet trnk = new SpriteSheet(ImageLoader.loadImage("/textures/trunk.png"));
		SpriteSheet stn = new SpriteSheet(ImageLoader.loadImage("/textures/stone.png"));
		SpriteSheet invSlots = new SpriteSheet(ImageLoader.loadImage("/textures/inventorySlots.png"));
		SpriteSheet wrldTst = new SpriteSheet(ImageLoader.loadImage("/textures/worldTest.png"));
		SpriteSheet bttmIntrfc = new SpriteSheet(ImageLoader.loadImage("/textures/bottomInterface.png"));
		
		playerRight = new BufferedImage[6];
		playerLeft = new BufferedImage[6];
		
		player_StandStill = plyr.crop(5, 6, 31, 70);
		
		playerRight[0] = plyr1.crop(5, 6, 31, 70);
		playerRight[1] = plyr2.crop(5, 6, 31, 70);
		playerRight[2] = plyr3.crop(5, 6, 31, 70);
		playerRight[3] = plyr4.crop(5, 6, 31, 70);
		playerRight[4] = plyr5.crop(5, 6, 31, 70);
		playerRight[5] = plyr6.crop(5, 6, 31, 70);
		
		playerLeft[0] = plyr1.crop(5, 6, 31, 70);
		playerLeft[1] = plyr2.crop(5, 6, 31, 70);
		playerLeft[2] = plyr3.crop(5, 6, 31, 70);
		playerLeft[3] = plyr4.crop(5, 6, 31, 70);
		playerLeft[4] = plyr5.crop(5, 6, 31, 70);
		playerLeft[5] = plyr6.crop(5, 6, 31, 70);
		
		inventory = inv.crop(0, 0, 200, 186);
		trunk = trnk.crop(0, 0, 18, 20);
		stone = stn.crop(0, 0, 17, 10);
		inventorySlots = invSlots.crop(0, 0, 219, 75);
		worldTest = wrldTst.crop(0, 0, 700, 400);
		bottomInterface = bttmIntrfc.crop(335, 20, 670, 50);
	}
	
}
