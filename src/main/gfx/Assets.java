package main.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage player, sword, cuerda, hierro, palo, casco, craftingTable, trunk, 
								stone, cuero, worldTest, inventarioPlegado, inventarioDesplegado, 
								itemPointers, craftingOutcome, craftButton1, craftButton2, 
								player_standStill_right, player_standStill_left;
	
	public static BufferedImage[] playerRightAnim = new BufferedImage[7], playerLeftAnim = new BufferedImage[7];
	
	public static void init() {
		
		SpriteSheet allSprites = new SpriteSheet(ImageLoader.loadImage("/textures/allSprites.png")); 
		
		//mundos y fondos
		worldTest = allSprites.crop(0, 500, 700, 400);
		
		//inventario y UI
		craftingTable = allSprites.crop(500, 200, 200, 186);
		inventarioPlegado = allSprites.crop(766, 200, 142, 61);
		inventarioDesplegado = allSprites.crop(500, 386, 384, 61);
		itemPointers = allSprites.crop(700, 200, 66, 64);
		craftButton1 = allSprites.crop(908, 200, 53, 19);
		craftButton2 = allSprites.crop(908, 219, 53, 19);
		craftingOutcome = allSprites.crop(524, 222, 53, 48);
		
		//items
		casco = allSprites.crop(612, 0, 27, 28);
		palo = allSprites.crop(554, 0, 18, 19);
		hierro = allSprites.crop(595, 0, 17, 20);
		cuero = allSprites.crop(639, 0, 25, 23);
		cuerda = allSprites.crop(572, 0, 23, 23);
		sword = allSprites.crop(535, 0, 19, 20);
		trunk = allSprites.crop(517, 0, 18, 20);
		stone = allSprites.crop(500, 0, 17, 10);
		
		//jugador y sprites de las animaciones de movimiento
		//player = allSprites.crop(0, 0, 31, 70); 
		playerRightAnim[0] = allSprites.crop(31, 0, 36, 70);
		playerRightAnim[1] = allSprites.crop(67, 0, 36, 70);
		playerRightAnim[2] = allSprites.crop(103, 0, 36, 70);
		playerRightAnim[3] = allSprites.crop(140, 0, 36, 70);
		playerRightAnim[4] = allSprites.crop(176, 0, 36, 70);
		playerRightAnim[5] = allSprites.crop(212, 0, 36, 70);
		playerRightAnim[6] = allSprites.crop(248, 0, 36, 70);
		playerLeftAnim[0] = allSprites.crop(248, 140, 36, 70);
		playerLeftAnim[1] = allSprites.crop(212, 140, 36, 70);
		playerLeftAnim[2] = allSprites.crop(176, 140, 36, 70);
		playerLeftAnim[3] = allSprites.crop(140, 140, 36, 70);
		playerLeftAnim[4] = allSprites.crop(104, 140, 36, 70);
		playerLeftAnim[5] = allSprites.crop(68, 140, 36, 70);
		playerLeftAnim[6] = allSprites.crop(32, 140, 36, 70);
		player_standStill_right = allSprites.crop(0, 70, 36, 70);
		player_standStill_left = allSprites.crop(36, 70, 36, 70);
	}
	
	public static BufferedImage getImgByName(String itemName) {
		
		if(itemName.equals("casco")) return casco;
		else if(itemName.equals("palo")) return palo;
		else if(itemName.equals("hierro")) return hierro;
		else if(itemName.equals("cuero")) return cuero;
		else if(itemName.equals("cuerda")) return cuerda;
		else if(itemName.equals("trunk")) return trunk;
		else if(itemName.equals("sword")) return sword;
		else return stone;
				
	}
	
	public static String getNameByImg(BufferedImage img) {
		
		if(img.equals(casco)) return "casco";
		else if(img.equals(palo)) return "palo";
		else if(img.equals(hierro)) return "hierro";
		else if(img.equals(cuero)) return "cuero";
		else if(img.equals(cuerda)) return "cuerda";
		else if(img.equals(trunk)) return "trunk";
		else if(img.equals(sword)) return "sword";
		else return "stone";
				
	}	
}