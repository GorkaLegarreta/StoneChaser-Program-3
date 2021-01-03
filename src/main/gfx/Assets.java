package main.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage player, sword, cuerda, hierro, palo, casco, craftingTable, trunk, 
								stone, cuero, worldTest, inventarioPlegado, inventarioDesplegado, 
								itemPointers, craftingOutcome, craftButton1, craftButton2;
	
	public static void init() {
		
		SpriteSheet plyr = new SpriteSheet(ImageLoader.loadImage("/textures/jugador.png")); 
		SpriteSheet inv = new SpriteSheet(ImageLoader.loadImage("/textures/Inventory.png"));
		SpriteSheet trnk = new SpriteSheet(ImageLoader.loadImage("/textures/trunk.png"));
		SpriteSheet stn = new SpriteSheet(ImageLoader.loadImage("/textures/stone.png"));
		SpriteSheet invplgdo = new SpriteSheet(ImageLoader.loadImage("/textures/inventarioPlegado.png"));
		SpriteSheet invdesplgdo = new SpriteSheet(ImageLoader.loadImage("/textures/inventarioDesplegado.png"));
		SpriteSheet wrldTst = new SpriteSheet(ImageLoader.loadImage("/textures/worldTest.png"));
		SpriteSheet swrd = new SpriteSheet(ImageLoader.loadImage("/textures/sword.png"));
		SpriteSheet cro = new SpriteSheet(ImageLoader.loadImage("/textures/cuero.png"));
		SpriteSheet rope = new SpriteSheet(ImageLoader.loadImage("/textures/rope.png"));
		SpriteSheet hrro = new SpriteSheet(ImageLoader.loadImage("/textures/hierro.png"));
		SpriteSheet plo = new SpriteSheet(ImageLoader.loadImage("/textures/palo.png"));
		SpriteSheet csco = new SpriteSheet(ImageLoader.loadImage("/textures/casco.png"));
		SpriteSheet pntrs = new SpriteSheet(ImageLoader.loadImage("/textures/itemPointers.png"));
		SpriteSheet crftbtn = new SpriteSheet(ImageLoader.loadImage("/textures/craftButton.png"));
		
		craftButton1 = crftbtn.crop(0, 0, 53, 19);
		craftButton2 = crftbtn.crop(0, 19, 53, 19);
		itemPointers = pntrs.crop(0, 0, 66, 64);
		casco = csco.crop(0, 0, 27, 28);
		palo = plo.crop(0, 0, 18, 19);
		hierro = hrro.crop(0, 0, 17, 20);
		cuero = cro.crop(0, 0, 25, 23);
		cuerda = rope.crop(0, 0, 23, 23);
		player = plyr.crop(5, 6, 31, 70);
		sword = swrd.crop(0, 0, 20, 20);
		craftingTable = inv.crop(0, 0, 200, 186);
		inventarioPlegado = invplgdo.crop(0, 0, 142, 61);
		inventarioDesplegado = invdesplgdo.crop(0, 0, 384, 61);
		trunk = trnk.crop(0, 0, 18, 20);
		stone = stn.crop(0, 0, 17, 10);
		worldTest = wrldTst.crop(0, 0, 700, 400);
		craftingOutcome = inv.crop(24, 22, 53, 48);
		
	}
	
}
