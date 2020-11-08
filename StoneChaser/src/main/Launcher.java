package main;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Launcher {
	
	private final static Logger LOGGER = Logger.getLogger(Launcher.class.getName());
	private static FileHandler fh;
	public static void main(String args[]) {
		try {
			fh = new FileHandler("Logger.txt");
			LOGGER.addHandler(fh);
		} catch (SecurityException e) {
			LOGGER.log(Level.SEVERE, "SecurityException");
			LOGGER.info("SecurityException");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IOException");
			LOGGER.info("IOException");
		}
		
		LOGGER.log(Level.FINE, "Level.FINE:"+Level.FINE.intValue()+" Inicio"); 	//este se tiene que escribir en fichero
		LOGGER.info("Level.INFO:"+Level.INFO.intValue()+" Inicio");	//Este se enseña por consola
		
		Game game = new Game("StoneChaser", 700, 400,fh);
		game.start();
		
		
	}
}
