package main.worlds;

import java.awt.Color;
import java.awt.Graphics;

import main.Handler;
import main.crafting.Crafting;
import main.entities.EntityManager;
import main.entities.creatures.BasicEnemy;
import main.entities.creatures.Player;
import main.gfx.Assets;
import main.inventory.Inventory;
import main.items.Item;
import main.items.ItemManager;

public class World {

	private Handler handler;
	private int width, height;

	
	//Entities
	private EntityManager entityManager;
	
	private Item i;
	private ItemManager im;
	
	public World(Handler handler) {
		this.handler = handler;
		
		entityManager = new EntityManager(handler, new Player(handler, 150, 170), new BasicEnemy(handler,20,20,50,50));
		
		im = new ItemManager(handler);
		
		
		
	}
	
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
	
	public Crafting getCrafting() {
		return im.getCrafting();
	}
}