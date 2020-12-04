package main.entities.creatures;

import main.Handler;
import main.entities.Entity;


public abstract class Creature extends Entity{ //tipo específico de criatura

	public static final float DEFAULT_SPEED = 1.8f;
	public static final int DEFAULT_CREATURE_WIDTH = 50;
	public static final int DEFAULT_CREATURE_HEIGHT = 50;
	
	protected float speed;
	protected float xMove, yMove;
	
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height); //super super nos lleva al constructor de Entity
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
	}

	//getter & setters
	
	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	} 	
	
}
