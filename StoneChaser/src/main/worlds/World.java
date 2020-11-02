package main.worlds;

import java.awt.Graphics;

import main.Handler;
import main.entities.EntityManager;
import main.entities.creatures.Player;
import main.gfx.Assets;

public class World {

	private Handler handler;
	private int width, height;

	
	//Entities
	private EntityManager entityManager;
	
	public World(Handler handler) {
		this.handler = handler;

	}
	
	public void tick() {
		entityManager.tick();
	}
	
	public void render(Graphics g) {
		
		//Entities
		entityManager.render(g);
		
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