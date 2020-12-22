package main;

import java.util.logging.Level;

public class GameDBException extends Exception{
	
	public GameDBException(String message, Exception e) {
		super(message + e);
		Game.LOGGER.log(Level.SEVERE, message + " " + Game.getStackTrace(e));
	}
	
	@Override
	public void printStackTrace() {
		System.out.println("La inicialización de la base de datos ha fallado");
		Game.LOGGER.log(Level.SEVERE,"La inicialización de la base de datos ha fallado");
	}
	
}
