package main.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import main.Game;
import main.GameDB;
import main.Handler;
import main.entities.creatures.Player;
import main.worlds.World;
import main.entities.statics.*;
import main.gfx.Assets;
import main.input.KeyManager;
import main.input.MouseManager;
import main.states.MenuState.WorldEnum;

public class GameState extends State{	
	
	private World world;
	private Rectangle saveButton;
	private MouseManager mouseManager;
	private KeyManager keyManager;
	private int x, y, height, width;
	public static boolean currentlySaving = false;
	
	public GameState(Handler handler) {
		super(handler);
		world = new World(handler);
		handler.setWorld(world);
		
		saveButton = new Rectangle(0, 380, 80, 20);		
		mouseManager = handler.getMouseManager();
		keyManager = handler.getKeyManager();
	}
	//////////////////////////////////////////////////////////////////
	//					METODOS TICK & RENDER						//
	//////////////////////////////////////////////////////////////////
	@SuppressWarnings("static-access")
	public void tick() { 
		
		world.tick();

		if (saveButtonIsPressed()) {
			currentlySaving = true;			
			new Thread( () -> {
			try {
				Thread.currentThread().sleep(1000);	
				if (GameDB.existsUserPosition(getUser())) {
					GameDB.updatePosition();
					Thread.currentThread().sleep(1000);	
				} else {
					GameDB.insertIntoPosiciones(getUser(), getPlayerXPosition(), getPlayerYPosition());
					Thread.currentThread().sleep(1000);	
				}				
				unPause();
				currentlySaving = false;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			}).start();
			
		}
		//handler.getGameCamera().move(1, 0); //para establecer la posicion del jugador, pero tiene que ser en un init porque si no suma el valor y se va moviendo la cámara (cinemáticas?).
	}
	
	public void render(Graphics g) {
		world.render(g);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 20));
		g.drawRect(saveButton.x, saveButton.y, saveButton.width, saveButton.height);
		g.drawString("SAVE", saveButton.x+15, saveButton.y+17);
		drawString(g);
	}
	/////////////////////////////////////////////////////////////////
	//				METODOS PARA SIMPLIFICAR CODIGO				   //
	/////////////////////////////////////////////////////////////////
	
	public boolean saveButtonIsPressed() {
		if (mouseManager.isLeftPressed() && saveButton.contains(mouseManager.getMouseX(),mouseManager.getMouseY())  ) {
			pause();
			return true;
		} else {
			return false;
		}
	}	
	
	public void pause() {
		keyManager.pause = true;
	}
	
	public void unPause() {
		keyManager.pause = false;
	}
	
	public boolean gameIsPaused() {
		return keyManager.pause;
	}
	
	public static int getPlayerXPosition() {
		return handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0F).x;
	}
	
	public static int getPlayerYPosition() {
		return handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0F).y;
	}
	
	public static int getUser() {
		if (MenuState.getWorldEnum().compareTo(WorldEnum.MUNDO1)==0) {
			return 1;
		} else if(MenuState.getWorldEnum().compareTo(WorldEnum.MUNDO2)==0) {
			return 2;
		} else if(MenuState.getWorldEnum().compareTo(WorldEnum.MUNDO3)==0) {
			return 3;
		} else {
			return 4;
		}
	}
	
	public void drawString(Graphics g) {
		if (gameIsPaused() && !currentlySaving) {
			g.drawString("PAUSE", 1, 20);
		}
		if (saveButtonIsPressed() || currentlySaving) {
			g.drawString("SAVING", 1, 20);
		}
	}
}
/*
 * 
 */
//JOptionPane.showConfirmDialog(null, "¿Quieres guardar el progreso del Mundo Actual?", "Guardar", JOptionPane.YES_NO_OPTION);
//if (JOptionPane.showConfirmDialog(null, "¿Quieres guardar el progreso del Mundo Actual?", "Guardar", JOptionPane.YES_NO_OPTION)
//		== JOptionPane.YES_OPTION) {
//	
//	//TODO más código para el guardado de la posicion actual, mundo en el que esta, vida, items...
//	
//			
//} else if (JOptionPane.showConfirmDialog(null, "¿Quieres guardar el progreso del Mundo Actual?", "Guardar", JOptionPane.YES_NO_OPTION)
//		== JOptionPane.NO_OPTION){				
//	//TODO más código para que vuelva a la pantalla de juego...
//	unPause();
//}
//unPause();
/*
 * 
 */
