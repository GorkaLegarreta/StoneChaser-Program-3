package main.worlds;

import java.awt.Graphics;
import main.Handler;
import main.crafting.Crafting;
import main.entities.EntityManager;
import main.entities.creatures.BasicEnemy;
import main.entities.creatures.Player;
import main.gfx.Assets;
import main.inventory.Inventory;
import main.items.ItemManager;

public class JungleWorld {

	private Handler handler;
	private int width, height;
	private Player player;
	
	//Entities
	private EntityManager entityManager;	
	
	private ItemManager im;
	
	public JungleWorld(Handler handler) {
		this.handler = handler;
		player = new Player(handler,100,100);
		entityManager = new EntityManager(handler, player, new BasicEnemy(handler,20,20,50,50));
		
		im = new ItemManager(handler);
	}
	
	/////////////////////////////////////////////////////////////////
	//					METODOS TICK & RENDER					   //
	/////////////////////////////////////////////////////////////////
	public void tick() {
		
		entityManager.tick();
		im.tick();
		
	}	
	public void render(Graphics g) {
		
		//world graphics
		g.drawImage(Assets.worldTest, (int) (0 - handler.getGameCamera().getxOffset()), (int) (0 - handler.getGameCamera().getyOffset()), 700, 400, null);
		
		//Entities
		entityManager.render(g);
		
		//itemManager
		im.render(g);		
		
	}
	
	/////////////////////////////////////////////////////////////////
	//					GETTERS & SETTERS						   //
	/////////////////////////////////////////////////////////////////
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public ItemManager getItemManager() {
		return im;
	}
	
	public Inventory getInventory() {
		return im.getInventory();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Crafting getCrafting() {
		return im.getCrafting();
	}
}