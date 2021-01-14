package main.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import main.Game;
import main.GameDB;
import main.GameDBException;
import main.Handler;
import main.gui.Viewer;
import main.input.MouseManager;

public class MenuState extends State  {

	private MouseManager mouseManager;
	private Rectangle lbl1, lbl2, lbl3, lbl4;	
	private Rectangle user1, user2, user3, user4;
	private Rectangle window;
	private String string1, string2, string3, string4;	
	private String[] strings = {string1, string2, string3, string4};
	private String optionPane;
	private static boolean freeze = false; //freeze is to avoid the tick opening thousands of windows
	
	private static WorldEnum world;
	
	public static enum WorldEnum{
		MUNDO1, MUNDO2, MUNDO3, MUNDO4
	}
	
	public static WorldEnum getWorldEnum() {
		return world;
	}
	public MenuState(Handler handler) {
		super(handler);
		 
		lbl1 = new Rectangle(30,40,150,50);
		lbl2 = new Rectangle(30,120,150,50);
		lbl3 = new Rectangle(30,200,150,50);
		lbl4 = new Rectangle(30,280,150,50);
		user1 = new Rectangle(225,40,425,50);
		user2 = new Rectangle(225,120,425,50);
		user3 = new Rectangle(225,200,425,50);
		user4 = new Rectangle(225,280,425,50);
		window = new Rectangle(270,350,150,40);
		mouseManager = handler.getMouseManager();	
		
	}
	
	@Override
	public void tick() throws GameDBException {	
		
		for(int i = 1; i < 5; i++) strings[i-1] = GameDB.getGameUserName(i);
		/////////////////////////////////////////////////////////////////
		//				LOS BOTONES A LA IZQUIERDA					   //
		/////////////////////////////////////////////////////////////////
		if(panelIsClicked(lbl1)) {	
			if(GameDB.existsGamePlayer(1)) {
				GameDB.incSessionNumber(1);
				world = WorldEnum.MUNDO1;
				changeToGameState(1);												
					
				// TODO más código para que se cargue la posicion del jugador, el mundo en el que estaba, salud items...
				
				worldIsloadedLogger(1);
			} else {
				createNewUser(1);				
			}				
		}
		
		if(panelIsClicked(lbl2)) {			
			if(GameDB.existsGamePlayer(2)) {				
				GameDB.incSessionNumber(2);
				world = WorldEnum.MUNDO2;
				changeToGameState(2);
							
				// TODO más código para que se cargue la posicion del jugador, el mundo en el que estaba, salud items...
				
				worldIsloadedLogger(2);
			} else {
				createNewUser(2);
			}		
		}
		
		if(panelIsClicked(lbl3)) {			
			if(GameDB.existsGamePlayer(3)) {				
				GameDB.incSessionNumber(3);
				world = WorldEnum.MUNDO3;
				changeToGameState(3);	
					
				// TODO más código para que se cargue la posicion del jugador, el mundo en el que estaba, salud items...
				
				worldIsloadedLogger(3);
			} else {
				createNewUser(3);			
			}			
		}
		if(panelIsClicked(lbl4)) {			
			if(GameDB.existsGamePlayer(4)) {				
				GameDB.incSessionNumber(4);
				world = WorldEnum.MUNDO4;
				changeToGameState(4);
				
				// TODO más código para que se cargue la posicion del jugador, el mundo en el que estaba, salud items...
				
				worldIsloadedLogger(4);
			} else {
				createNewUser(4);				
			}			
		}
		/////////////////////////////////////////////////////////////////
		//					 BOTONES A LA DERECHA					   //
		/////////////////////////////////////////////////////////////////
		if (panelIsClicked(user1)) {
			confirmToDeleteUser(1);
		}
		if(panelIsClicked(user2)) {			
			confirmToDeleteUser(2);
		}
		if(panelIsClicked(user3)) {			
			confirmToDeleteUser(3);
		}
		if(panelIsClicked(user4)) {
			confirmToDeleteUser(4);		
		}
		/////////////////////////////////////////////////////////////////
		//				POSIBLE BOTÓN DE MOSTRAR TABLAS				   //
		/////////////////////////////////////////////////////////////////		
//		if (panelIsClicked(window)) {
//			if (!freeze) {
//				changeWindow();
//				startStop();
//			} 
//		}
		openWindow();
		solveFreezeProblem();
	}
	
	@Override
	public void render(Graphics g) {
		Color orange = new Color(255, 200, 120);
		g.setColor(orange);		
		g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
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
		
		g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 15));
		g.drawString("OPEN STATISTICS", 280, 375);
		g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
		g.drawRect(window.x, window.y, window.width, window.height);		
		g.drawString(strings[0], 380, 60);
		g.drawString(strings[1], 380, 145);
		g.drawString(strings[2], 380, 225);
		g.drawString(strings[3], 380, 305);		
	}
	
	public boolean panelIsClicked(Rectangle obj) {
		return mouseManager.isLeftPressed() && obj.contains(mouseManager.getMouseX(), mouseManager.getMouseY());
	}
	public void worldIsloadedLogger(int world) {
		Game.LOGGER.log(Level.INFO,"Se ha iniciado como Mundo"+world);	
		Game.LOGGER.log(Level.CONFIG,"GameState Cargada e inicializada en Configuracion Mundo"+world);
	}
	public void changeToGameState(int user) {
		// Player
		GameState.handler.getWorld().getPlayer().setPlayerX(user);
		GameState.handler.getWorld().getPlayer().setPlayerY(user);
	
		// State
		State.setState(handler.getGame().gameState);
	}
	public void createNewUser(int world) throws GameDBException {
		optionPane = JOptionPane.showInputDialog("Introduce el nombre para el usuario");
		if (optionPane == null) {
			optionPane = GameDB.getGameUserName(world);
		} else {
			GameDB.createGamePlayer(world, optionPane);
			strings[world-1] = optionPane;
		}
	}
	public void confirmToDeleteUser(int world) throws GameDBException {
		JOptionPane.showConfirmDialog(null, "¿Quieres eliminar al usuario?");
		if (JOptionPane.showConfirmDialog(null, "¿Quieres eliminar al usuario?")==JOptionPane.YES_OPTION) {
			GameDB.deleteUsers(world);
		} else if (JOptionPane.showConfirmDialog(null, "¿Quieres eliminar al usuario?")==JOptionPane.NO_OPTION) {
			
		}
	}
	public void changeWindow() {
		Game.getWindow().setInvisibility();
		SwingUtilities.invokeLater(new Runnable() {			
			@Override
			public void run() {
				new Viewer();				
			}
		});
	}
	public void openWindow() {
		if (panelIsClicked(window)) {
			if (!freeze) {
				changeWindow();
				startStop();
			}
		}
	}
	public static void startStop() {
		if(freeze) {
			freeze = false;
		} else {
			freeze = true;
		}
	}
	@SuppressWarnings("static-access")
	public void solveFreezeProblem() {
		if(Game.getWindow().getVisibility()) {
			freeze=false;
		}
	}
}
