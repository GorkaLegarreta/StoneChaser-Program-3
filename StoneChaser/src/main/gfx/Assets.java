package main.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage player;
	
	public static void init() {
		
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/jugador.png")); 

		player = sheet.crop(5, 6, 31, 70);
	}
	
}
