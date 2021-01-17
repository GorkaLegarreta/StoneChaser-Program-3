package main.worlds;

import java.awt.Graphics;
import main.Handler;
import main.crafting.Crafting;
import main.entities.EntityManager;
import main.entities.creatures.BasicEnemy;
import main.entities.creatures.Player;
import main.entities.statics.Tree;
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
		
		//inicializamos el entitymanager
		entityManager = new EntityManager(handler, player, new BasicEnemy(handler,20,20,50,50));
		
		//entidades estaticas
		entityManager.addEntity(new Tree(handler, 65, 163));
		entityManager.addEntity(new Tree(handler, 170, 490));
		entityManager.addEntity(new Tree(handler, 260, 780));
		entityManager.addEntity(new Tree(handler, 50, 500));
		entityManager.addEntity(new Tree(handler, 400, 200));
		entityManager.addEntity(new Tree(handler, 500, 350));
		entityManager.addEntity(new Tree(handler, 600, 163));
		entityManager.addEntity(new Tree(handler, 1400, 50));
		entityManager.addEntity(new Tree(handler, 700, 600));
		entityManager.addEntity(new Tree(handler, 800, 700));
		entityManager.addEntity(new Tree(handler, 900, 400));
		entityManager.addEntity(new Tree(handler, 1000, 200));
		entityManager.addEntity(new Tree(handler, 1100, 100));
		entityManager.addEntity(new Tree(handler, 1200, 500));
		entityManager.addEntity(new Tree(handler, 1300, 720));
		
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
		g.drawImage(Assets.jungleWorld, (int) (0 - handler.getGameCamera().getxOffset()), (int) (0 - handler.getGameCamera().getyOffset()), 1400*2, 800*2, null);
		
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