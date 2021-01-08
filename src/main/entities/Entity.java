package main.entities;

import java.awt.*;
import main.Handler;

public abstract class Entity {
	
	protected Handler handler;
	protected float x, y; //los cálculos pueden ser de tipo float (no podemos pintar medio pixel).
	protected int width, height;
	protected int health, totalDamage;
	protected boolean active = true;
	protected Rectangle bounds;
	
	public Entity(Handler handler, float x, float y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = Integer.parseInt(handler.getPropertiesFile().getProperty("defaultHealth"));
		totalDamage = 0;
		bounds = new Rectangle(0, 0, width, height);
	}
	
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}
	
	public float getX() {
		return x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getTotalDamage() {
		return totalDamage;
	}

	public void setTotalDamage(int totalDamage) {
		this.totalDamage = totalDamage;
	}

	public abstract void tick();

	public abstract void render(Graphics g);
	
	public abstract void die();
	
	public void hurt(int damage) {
		health -= damage;
		totalDamage += damage;
		if(health <= 0) {
			active = false;
			die();
		}	
	}
	
	public boolean checkEntityCollisions(float xOffset, float yOffset) {
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this)) //no comprobamos colisiones contra si mismo
				continue;
			if(e.getCollisionBounds(0f,  0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset) {
		return new Rectangle((int) (x + bounds.x + xOffset), 
				(int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	public void testRect() {
		System.out.println((x + bounds.x) + ", " + (y + bounds.y));
	}
	
}
