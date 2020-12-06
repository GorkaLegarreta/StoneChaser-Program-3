package main.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.logging.Level;

import main.Game;
import main.Handler;
import main.gfx.Animation;

public class Player extends Creature{ //no longer abstract, so we need a tick and render method
	
	//Animations
	
	private Animation animRight, animLeft;
	private static long lastAttack = System.currentTimeMillis();
	private String lastAnim;
	private long lastAttackTimer, attackCooldown = 500, attackTimer = attackCooldown;
	private int kickDamage = 3;
	private int lastDirection = 0;
	
	Rectangle kickRect;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
	
		//definir bounds...
	}


	public void tick() {
		/*
		
		//Animations
		animRight.tick();
		animLeft.tick();
		//movement
		getInput();
		move();
		
		//handler.getGameCamera().centerOnEntity(this); //to center THIS player
		
		*/
		move();
		getInput();
	}
	
	public void die() {
		Game.LOGGER.log(Level.FINE,"Has sido eliminado, has perdido");
		Game.LOGGER.info("Has sido eliminado, has perdido");
	}

	private void getInput() {
		xMove = 0;
		yMove = 0;
		
		
		
		if(handler.getKeyManager().left) {
			xMove = -speed;
			lastDirection = 0;
		}		
		if(handler.getKeyManager().right) {
			xMove = speed;
			lastDirection = 1;		
		}
		if(handler.getKeyManager().up) {
			yMove = -speed;
			lastDirection = 2;
		}
		if(handler.getKeyManager().down) {
			yMove = speed;
			lastDirection = 3;
		}
		if(handler.getKeyManager().space) {
			long attackNow = System.currentTimeMillis();
			long attackEnabler = attackNow - lastAttack;
	        
			if (attackEnabler > 150) {
	        	lastAttack = attackNow;
				Rectangle collisionBounds = getCollisionBounds(0, 0); //variable del offset
				kickRect = new Rectangle();
				kickRect.width = 20;
				kickRect.height = 20;

				if(xMove<0 || lastDirection == 0 ) {
					kickRect.x = collisionBounds.x - kickRect.width; 								
					kickRect.y = collisionBounds.y + collisionBounds.height/2 - kickRect.height/2;
					// LOGGER
					Game.LOGGER.log(Level.FINEST,"Le pega con kickRect izquierda");
				}
				if(xMove>0 || lastDirection == 1) {
					kickRect.x = collisionBounds.x + collisionBounds.width; 						
					kickRect.y = collisionBounds.y + collisionBounds.height/2 - kickRect.height/2;
					// LOGGER
					Game.LOGGER.log(Level.FINEST,"Le pega con kickRect derecha");
				}
				if(yMove<0 || lastDirection == 2) {
					kickRect.x = collisionBounds.x + collisionBounds.width/2 - kickRect.width/2; 
					kickRect.y = collisionBounds.y - kickRect.height;
					// LOGGER
					Game.LOGGER.log(Level.FINEST,"Le pega con kickRect arriba");
				}
				if(yMove>0 || lastDirection == 3) {
					kickRect.x = collisionBounds.x + collisionBounds.width/2 -kickRect.width/2;  
					kickRect.y = collisionBounds.y + collisionBounds.height;
					// LOGGER
					Game.LOGGER.log(Level.FINEST,"Le pega con kickRect abajo");
				}

				for(main.entities.Entity e : handler.getWorld().getEntityManager().getEntities()) {
		            if(e.equals(this))
		                continue; //pasa al siguiente valor del for loop ya que no nos queremos herir a nosotros
		            if(e.getCollisionBounds(0,0).intersects(kickRect)) {
		                e.hurt(kickDamage);//we only hurt one entity at a time
		                return;
		            }
				}
			} else {
				
			}		
		}
			
	}
	

	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect((int)x, (int) y, 50, 50);
		// ESTO HACE QUE SE VEA KICKRECT
		if(kickRect != null && handler.getKeyManager().space) { 
			g.setColor(Color.BLUE);
			g.fillRect((int)kickRect.x, (int) kickRect.y, kickRect.width, kickRect.height);
		}
		
//		g.drawImage(getCurrentAnimationFrame(), (int) (x), (int) (y), width, height,  null);
//		g.setColor(Color.red);
//		g.fillRect((int) (x + bounds.x), (int) (y + bounds.y), bounds.width, bounds.height);
	}
	
	/*private BufferedImage getCurrentAnimationFrame() {
			
		
		
		if(xMove < 0 && yMove != 0) { //moves left and in any direction in the y axis
			lastAnim = "left";
			return animLeft.getCurrentFrame();
		}else if(xMove > 0 && yMove != 0) { //moves right and in any direction in the y axis
			lastAnim = "right";
			return animRight.getCurrentFrame();
		}else if(xMove < 0) { //only moves left
			lastAnim = "left";
			return animLeft.getCurrentFrame(); 
		}else if(xMove > 0) { //only moves right
			lastAnim = "right";
			return animRight.getCurrentFrame();
		}else if(yMove != 0) { //only moves up or down
			if(lastAnim == "left") return animLeft.getCurrentFrame();
			else return animRight.getCurrentFrame();
		}else {
			if(lastAnim == "left") return Assets.player_standStill_left;
			else return Assets.player_standStill_right;
		}
	}*/
}