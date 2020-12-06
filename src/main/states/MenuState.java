package main.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import main.Game;
import main.GameDB;
import main.Handler;
import main.input.MouseManager;

public class MenuState extends State  {
	
	private MouseManager mouseManager;
	private Rectangle lbl1, lbl2, lbl3, lbl4;	
	private Rectangle user1, user2, user3, user4;
	private String string1 ="User1-Gorka";
	private String string2 ="User2-Inigo";
	private String string3 ="User3-Pepe";
	private String string4 ="User4-World4";

	private static WorldEnum world;
		
	public enum WorldEnum{
		MUNDO1, MUNDO2, MUNDO3, MUNDO4;			
	}

	Connection con = null;
	Statement stmt = null;
	
	
	public MenuState(Handler handler) {
		super(handler);
		// play = new Rectangle(237,162,225,75); // Boton en el centro de la pantalla  
		lbl1 = new Rectangle(30,40,150,50);
		lbl2 = new Rectangle(30,120,150,50);
		lbl3 = new Rectangle(30,200,150,50);
		lbl4 = new Rectangle(30,280,150,50);
		user1 = new Rectangle(225,40,425,50);
		user2 = new Rectangle(225,120,425,50);
		user3 = new Rectangle(225,200,425,50);
		user4 = new Rectangle(225,280,425,50);
		mouseManager = handler.getMouseManager();		
	}
	
	@Override
	public void tick() {	
		if(handler.getMouseManager().isLeftPressed() && lbl1.contains(mouseManager.getMouseX(), mouseManager.getMouseY()) ) {
			//LOGGER
			Game.LOGGER.log(Level.INFO,"Se ha iniciado como Mundo1");	
			Game.LOGGER.log(Level.CONFIG,"GameState Cargada e inicializada en Configuracion Mundo1");
			// SE CARGA GAME STATE PARA PASAR AL JUEGO 
			State.setState(handler.getGame().gameState);
			///////////////////////////////////////////////
			world = WorldEnum.MUNDO1;
			if(GameDB.checkGamePlayer(1)) {
				GameDB.createGamePlayer(1, string1);
			}
		}else if(handler.getMouseManager().isLeftPressed() && lbl2.contains(mouseManager.getMouseX(), mouseManager.getMouseY()) ) {
			//LOGGER
			Game.LOGGER.log(Level.INFO,"Se ha iniciado como Mundo2");	
			Game.LOGGER.log(Level.CONFIG,"GameState Cargada e inicializada en Configuracion Mundo2");
			// SE CARGA GAME STATE PARA PASAR AL JUEGO 
			State.setState(handler.getGame().gameState);
			///////////////////////////////////////////////
			world = WorldEnum.MUNDO2;
			if(GameDB.checkGamePlayer(2)) {
				GameDB.createGamePlayer(2, string2);
			}
		}else if(handler.getMouseManager().isLeftPressed() && lbl3.contains(mouseManager.getMouseX(), mouseManager.getMouseY()) ) {
			//LOGGER
			Game.LOGGER.log(Level.INFO,"Se ha iniciado como Mundo3");	
			Game.LOGGER.log(Level.CONFIG,"GameState Cargada e inicializada en Configuracion Mundo3");
			// SE CARGA GAME STATE PARA PASAR AL JUEGO 
			State.setState(handler.getGame().gameState);
			///////////////////////////////////////////////
			world = WorldEnum.MUNDO3;
			if(GameDB.checkGamePlayer(3)) {
				GameDB.createGamePlayer(3, string3);
			}
		}else if(handler.getMouseManager().isLeftPressed() && lbl4.contains(mouseManager.getMouseX(), mouseManager.getMouseY()) ) {
			//LOGGER
			Game.LOGGER.log(Level.INFO,"Se ha iniciado como Mundo4");	
			Game.LOGGER.log(Level.CONFIG,"GameState Cargada e inicializada en Configuracion Mundo4");
			// SE CARGA GAME STATE PARA PASAR AL JUEGO 
			State.setState(handler.getGame().gameState);
			//////////////////////////////////////////////
			world = WorldEnum.MUNDO4;
			if(GameDB.checkGamePlayer(4)) {
				GameDB.createGamePlayer(4, string4);
			}
		}
		/////////////////////////////////////////////////////////////////////////////////////////
		/// BOTONES A LA DERECHA SIN IMPLEMENTAR
		/////////////////////////////////////////////////////////////////////////////////////////
		if(handler.getMouseManager().isLeftPressed() && user1.contains(mouseManager.getMouseX(), mouseManager.getMouseY()) ) {
			
		}else if(handler.getMouseManager().isLeftPressed() && user2.contains(mouseManager.getMouseX(), mouseManager.getMouseY()) ) {			
						
		}else if(handler.getMouseManager().isLeftPressed() && user3.contains(mouseManager.getMouseX(), mouseManager.getMouseY()) ) {			
			
		}else if(handler.getMouseManager().isLeftPressed() && user4.contains(mouseManager.getMouseX(), mouseManager.getMouseY()) ) {
					
		}
		
	}
	
	@Override
	public void render(Graphics g) {
		Color c = new Color(255, 200, 120);
		g.setColor(c);		
//		g.fillRoundRect(play.x,play.y,play.width,play.height, 35, 35);			
//		g.drawRoundRect(play.x,play.y,play.width,play.height, 35, 35);	
		g.fillRoundRect(lbl1.x,lbl1.y,lbl1.width,lbl1.height, 35, 35);
		g.fillRoundRect(lbl2.x,lbl2.y,lbl2.width,lbl2.height, 35, 35);
		g.fillRoundRect(lbl3.x,lbl3.y,lbl3.width,lbl3.height, 35, 35);
		g.fillRoundRect(lbl4.x,lbl4.y,lbl4.width,lbl4.height, 35, 35);
		g.fillRoundRect(user1.x,user1.y,user1.width,user1.height, 35, 35);
		g.fillRoundRect(user2.x,user2.y,user2.width,user2.height, 35, 35);
		g.fillRoundRect(user3.x,user3.y,user3.width,user3.height, 35, 35);
		g.fillRoundRect(user4.x,user4.y,user4.width,user4.height, 35, 35);
		
		g.setColor(Color.BLACK);		
		g.drawString("MUNDO 1", 60, 65);
		g.drawString("MUNDO 2", 60, 145);
		g.drawString("MUNDO 3", 60, 225);
		g.drawString("MUNDO 4", 60, 305);
		g.drawString(String.format("Mouse moved at %d,%d", mouseManager.getMouseX(), mouseManager.getMouseY()), 250, 50);
		g.drawString(String.format("Mouse clicked at %d,%d", mouseManager.getClickX(), mouseManager.getClickY()), 250, 135);
		//AQUI SI UTILIZAMOS EL getGamePlayer
		g.drawString(string1, 380, 60);
		g.drawString(string2, 380, 145);
		g.drawString(string3, 380, 225);
		g.drawString(string4,380,305);
		//g.drawString(GameDB.getGamePlayer(4), 380, 305);
		
	}
	
}
