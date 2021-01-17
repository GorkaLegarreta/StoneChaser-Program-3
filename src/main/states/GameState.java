package main.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

import main.Game;
import main.GameDB;
import main.GameDBException;
import main.Handler;
import main.gfx.Assets;
import main.input.KeyManager;
import main.input.MouseManager;
import main.inventory.Inventory;
import main.items.Item;
import main.states.MenuState.WorldEnum;
import main.worlds.World;

public class GameState extends State{	
	
	private World world;
	private Rectangle saveButton;
	private MouseManager mouseManager;
	private KeyManager keyManager;
	public static boolean currentlySaving = false;
	private Item[] inv;
	public int id,x,y,quantity,index;
	public String name;
	
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
					GameDB.updatePosition();
					GameDB.deleteInventory(getUser());			
					unPause();
					currentlySaving = false;
					
					
				} catch (InterruptedException | GameDBException e) {
					Thread.currentThread().interrupt();
				}
			}).start();
			Game.LOGGER.log(Level.FINEST,"Se han guardado los datos de con éxito en la BD" );
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
		if (mouseManager.isLeftPressed() && saveButton.contains(mouseManager.getMouseX(), mouseManager.getMouseY()) ) {
			pause();
			
			if(handler.getUser() == 1) saveItems(1);
			if(handler.getUser() == 2) saveItems(2); 
			if(handler.getUser() == 3) saveItems(3);	
			if(handler.getUser() == 4) saveItems(4);
			
			return true;
		} else {
			return false;
		}
	}
	
	public void saveItems(int user) {
		
		File f;
		int c = 0;
		inv = handler.getWorld().getInventory().getItemArray();
		
		if(user == 1) f = new File("user1Inv.txt");
		else if(user == 2) f = new File("user2Inv.txt");
		else if(user == 3) f = new File("user3Inv.txt");
		else f = new File("user4Inv.txt");
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(f))){
			while(c < 6) {
				if(inv[c] != null) {
					bw.write(inv[c].getName() + "," + Assets.getNameByImg(inv[c].getImage()) + "," + inv[c].getWidth() + "," + inv[c].getHeight() + "," + inv[c].getId() + "," + inv[c].getItemQuantity() + ",");
					bw.newLine();
					bw.flush();
				}
				c++;
			}
			
		} catch (IOException e) {
			Game.LOGGER.log(Level.WARNING, "No se ha podido guardar el inventario del usuario. Error: " + Game.getStackTrace(e));
		}
			
		
		
//		for (int i=0; i<inv.length; i++) {
//			
//			if (inv[i] != null) {
//				
//				id = inv[i].getId();
//				name = inv[i].getName();
//				x = inv[i].itemX();
//				y = inv[i].itemY();
//				quantity = inv[i].getItemQuantity();
//				index = i;
//				GameDB.insertIntoInventory(id,name,x,y,index,quantity);
//			}
//			
//		}		
	}
	
	
	@SuppressWarnings("static-access")
	public void pause() {
		keyManager.pause = true;		
	}	
	@SuppressWarnings("static-access")
	public void unPause() {
		keyManager.pause = false;
	}	
	@SuppressWarnings("static-access")
	public boolean gameIsPaused() {
		return keyManager.pause;
	}
	
	public static int getPlayerXPosition() {
		return handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0F).x;
	}
	public static int getPlayerYPosition() {
		return handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0F).y;
	}
	public static int getItemXPosition() {
		return 0;
	}
	public static int getItemYPosition() {
		return 0;
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