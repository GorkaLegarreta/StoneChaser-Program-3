package main.worlds;

import java.awt.Color;
import java.awt.Graphics;

import main.Handler;
import main.entities.EntityManager;
import main.entities.creatures.BasicEnemy;
import main.entities.creatures.Player;
import main.items.Item;

public class World {

	private Handler handler;
	private int width, height;

	
	//Entities
	private EntityManager entityManager;
	
	private Item woodItem;
	
	public World(Handler handler) {
		this.handler = handler;
		
		entityManager = new EntityManager(handler, new Player(handler, 150, 170), new BasicEnemy(handler,20,20,50,50));
		woodItem = new Item("wood", 20, 20, 1);
	}
	
	public void tick() {
		
		entityManager.tick();
	}
	
	public void render(Graphics g) {
		
		//Entities
		g.fillRect(0, 0, 700, 400);
		g.setColor(Color.BLACK);
		entityManager.render(g);
		
		//código temporal:
		woodItem.render(g);
		
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
}