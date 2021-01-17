package main.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import main.Game;
import main.Handler;
import main.input.KeyManager;

public class BasicEnemy extends Creature{
	
	private static int basicEnemySpeed;
	private int xMove;
	private KeyManager keyManager;
	
	public BasicEnemy(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);	
		
		keyManager = handler.getKeyManager();
		
		basicEnemySpeed = Integer.parseInt(handler.getPropertiesFile().getProperty("basicEnemySpeed"));
		xMove = basicEnemySpeed;
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 50;
		bounds.height = 50;
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void tick() {
		if (!keyManager.pause) {
			move();
		}
	}
	
	public void move() {
		if(!checkEntityCollisions(xMove, 0f))
			enemyMovement();		
	}
	public void enemyMovement() {
		if(x > 600) xMove = - basicEnemySpeed;
		if(x < 20) xMove = Math.abs(basicEnemySpeed);
		
		x += xMove;
		
	}

	@Override
	public void render(Graphics g) {
		
		g.setColor(Color.RED);
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset()), 50, 50);
		
	}

	@Override
	public void die() {
		Game.LOGGER.log(Level.FINE,"El jugador ha vencido al cubo rojo");
		Game.LOGGER.info("Lo has eliminado, has ganado");
	}
	
}
