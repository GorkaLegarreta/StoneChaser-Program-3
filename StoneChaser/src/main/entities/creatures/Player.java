package main.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import main.Game;
import main.Handler;
import main.gfx.Animation;
import main.gfx.Assets;

public class Player extends Creature{ //no longer abstract, so we need to define the tick and render methods	
	//Animations
	
	private Animation animRight, animLeft;
	private BufferedImage player_standStill = Assets.player_StandStill;
	private static long lastAttack = System.currentTimeMillis();
	private String lastAnim;
	private long lastAttackTimer, attackCooldown = 500, attackTimer = attackCooldown;
	private int kickDamage = 3;
	private int lastDirection = 0;
	private boolean playerActive = true;
	
	//Dimensions
	private static final int width = 31, height = 70;
	
	
	Rectangle kickRect;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, width*2, height*2);
	
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = width*2;
		bounds.height = height*2;
		
		//Animations
		animRight = new Animation(100, Assets.playerRight);
		animLeft = new Animation(100, Assets.playerLeft);
	}


	public void tick() {
		
		
		//Animations
		animRight.tick();
		animLeft.tick();
		
		
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

			if(xMove<0 || lastDirection == 0) {
				kickRect.x = (int) (x + bounds.x - kickRect.width); 								
				kickRect.y = (int) (y + bounds.y + bounds.height/2 - kickRect.height/2);
				// LOGGER
				Game.LOGGER.log(Level.FINEST,"Le pega con kickRect izquierda");
			}
			if(xMove>0 || lastDirection == 1) {
				kickRect.x = (int) (x + bounds.x + bounds.width); 						
				kickRect.y = (int) (y + bounds.y + bounds.height/2 - kickRect.height/2);
				// LOGGER
				Game.LOGGER.log(Level.FINEST,"Le pega con kickRect derecha");
			}
			if(yMove<0 || lastDirection == 2) {
				kickRect.x = (int) (x + bounds.x + bounds.width/2 - kickRect.width/2); 
				kickRect.y = (int) (y + bounds.y - kickRect.height);
				// LOGGER
				Game.LOGGER.log(Level.FINEST,"Le pega con kickRect arriba");
			}
			if(yMove>0 || lastDirection == 3) {
				kickRect.x = (int) (x + bounds.x + bounds.width/2 -kickRect.width/2);  
				kickRect.y = (int) (y + bounds.y + bounds.height);
				// LOGGER
				Game.LOGGER.log(Level.FINEST,"Le pega con kickRect abajo");
			}
			
			//al crear el rectángulo, comprobamos también si éste coincide con cualquier otro de las entidades (si le ha dado una patada)
			//y si es el caso, quitamos vida a la entidad que ha sido golpeada.
			
			for(main.entities.Entity e : handler.getWorld().getEntityManager().getEntities()) {
				
				if(e.equals(this)) {
	            	continue; //pasa al siguiente valor del for loop ya que no nos queremos herir a nosotros
	            	
	            }if(e.getCollisionBounds(0f, 0f).intersects(kickRect)) {	//le pasamos un offset de 0 porque hasta el momento en cada entidad estamos aplicando el offset.
	            	
	            	e.hurt(kickDamage);										//sólo herimos a cada entidad una vez
	                return;
	            }
			}
		}
	}	

	public void render(Graphics g) {

//		g.setColor(Color.BLUE);
//		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()), (int) (y + bounds.y - handler.getGameCamera().getyOffset()), 62, 140);
	
		
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width*2, height*2, null);
//		g.drawImage(Assets.bottomInterface, handler.getHeight() - 50, 0, 870, 50, null);
		g.drawImage(Assets.bottomInterface, 0, handler.getHeight() - 50, 870, 50, null);
		
		// ESTO HACE QUE SE VEA KICKRECT
		if(kickRect != null && handler.getKeyManager().space) { 
			g.setColor(Color.BLUE);
			g.fillRect((int) (kickRect.x - handler.getGameCamera().getxOffset()), (int) (kickRect.y - handler.getGameCamera().getyOffset()), kickRect.width, kickRect.height);
		}
		
//		g.drawImage(getCurrentAnimationFrame(), (int) (x), (int) (y), width, height,  null);

	}
	
	private BufferedImage getCurrentAnimationFrame() {
			
		
		
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
			if(lastAnim == "left") return Assets.player_StandStill;
			else return Assets.player_StandStill;
		}
	}
	
	public void setPlayerActive() {
		playerActive = true;
	}


	public void setPlayerInactive() {
		playerActive = false;
	}
}
