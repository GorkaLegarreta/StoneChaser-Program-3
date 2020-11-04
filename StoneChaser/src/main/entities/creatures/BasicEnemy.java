package main.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;

import main.Handler;

public class BasicEnemy extends Creature{
	
	private static int alienSpeed = 2;
	private static int alienLives = 10;
	
	public BasicEnemy(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 50;
		bounds.height = 50;
	}

	@Override
	public void tick() {
		
		enemyMovement();
		
	}
	
	
	public void enemyMovement() {
		if(x > 600) alienSpeed = - alienSpeed;
		if(x < 20) alienSpeed = Math.abs(alienSpeed);	
		
		x += alienSpeed;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)x,(int) y, 50, 50);
		
	}

	@Override
	public void die() {
		System.out.println("You win");
		
	}
	
}
