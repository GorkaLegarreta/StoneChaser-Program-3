package main.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.logging.Level;

import main.Game;
import main.Handler;
import main.gfx.Animation;
import main.gfx.Assets;

public class Player extends Creature{ //no longer abstract, so we need to define the tick and render methods	
	//Animations
	
	private Animation animRight, animLeft;
	private static long lastAttack = System.currentTimeMillis();
	private String lastAnim;
	private long lastAttackTimer, attackCooldown = 500, attackTimer = attackCooldown;
	private int kickDamage = 3;
	private int lastDirection = 0;
	private boolean playerActive = true;
	
	Rectangle kickRect;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
	
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 62;
		bounds.height = 140;
	}


	public void tick() {
		/*
		
		//Animations
		animRight.tick();
		animLeft.tick();
		//movement
		getInput();
		move();
		
		*/
		
		handler.getGameCamera().centerOnEntity(this); //to center THIS player
		
		
		if(playerActive) {
			move();
			getInput();
		}
	}
	
	public void move() {
		if(!checkEntityCollisions(xMove, 0f))
			moveX();
		if(!checkEntityCollisions(0f, yMove)) //el offset indica al rectángulo de colisiones a donde nos dirigimos
			moveY();		
	}
	
	public void moveX() {
		x += xMove;	
	}
	
	public void moveY() {
		y += yMove;	
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
			kick();
		}	
	}
	
	public void kick() {
		long attackNow = System.currentTimeMillis();
		long attackEnabler = attackNow - lastAttack;
        
		if (attackEnabler > 150) {
        	lastAttack = attackNow;

			kickRect = new Rectangle();
			kickRect.width = 50;
			kickRect.height = 30;

			if(xMove<0 || lastDirection == 0 ) {
				kickRect.x = (int) (x +bounds.x - kickRect.width - handler.getGameCamera().getxOffset()); 								
				kickRect.y = (int) (y + bounds.y + bounds.height/2 - kickRect.height/2 - handler.getGameCamera().getyOffset());
				// LOGGER
				Game.LOGGER.log(Level.FINEST,"Le pega con kickRect izquierda");
			}
			if(xMove>0 || lastDirection == 1) {
				kickRect.x = (int) (x + bounds.x + bounds.width - handler.getGameCamera().getxOffset()); 						
				kickRect.y = (int) (y + bounds.y + bounds.height/2 - kickRect.height/2 - handler.getGameCamera().getyOffset());
				// LOGGER
				Game.LOGGER.log(Level.FINEST,"Le pega con kickRect derecha");
			}
			if(yMove<0 || lastDirection == 2) {
				kickRect.x = (int) (x + bounds.x + bounds.width/2 - kickRect.width/2 - handler.getGameCamera().getxOffset()); 
				kickRect.y = (int) (y + bounds.y - kickRect.height - handler.getGameCamera().getyOffset());
				// LOGGER
				Game.LOGGER.log(Level.FINEST,"Le pega con kickRect arriba");
			}
			if(yMove>0 || lastDirection == 3) {
				kickRect.x = (int) (x + bounds.x + bounds.width/2 -kickRect.width/2 - handler.getGameCamera().getxOffset());  
				kickRect.y = (int) (y + bounds.y + bounds.height - handler.getGameCamera().getyOffset());
				// LOGGER
				Game.LOGGER.log(Level.FINEST,"Le pega con kickRect abajo");
			}

			for(main.entities.Entity e : handler.getWorld().getEntityManager().getEntities()) {
	            if(e.equals(this))
	                continue; //pasa al siguiente valor del for loop ya que no nos queremos herir a nosotros
	            if(e.getCollisionBounds(0f, 0f).intersects(kickRect)) {				//mal, necesitamos saber hacia donde se dirige cada entidad y meter el offset correspondente
	                e.hurt(kickDamage);//we only hurt one entity at a time
	                return;
	            }
			}
		} else {
			
		}	
	}	

	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()), (int) (y + bounds.y - handler.getGameCamera().getyOffset()), 62, 140);
		//g.fillRect(x, y, lastDirection, kickDamage);
		
		g.drawImage(Assets.player, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), 62, 140, null);
		
		// ESTO HACE QUE SE VEA KICKRECT
		if(kickRect != null && handler.getKeyManager().space) { 
			g.setColor(Color.BLUE);
			g.fillRect((int)kickRect.x, (int) kickRect.y, kickRect.width, kickRect.height);
		}
		
//		g.drawImage(getCurrentAnimationFrame(), (int) (x), (int) (y), width, height,  null);

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
	
	public void setPlayerActive() {
		playerActive = true;
	}


	public void setPlayerInactive() {
		playerActive = false;
	}
}
