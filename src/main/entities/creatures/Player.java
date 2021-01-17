package main.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import main.Game;
import main.GameDB;
import main.Handler;
import main.gfx.Animation;
import main.gfx.Assets;

public class Player extends Creature{ //no longer abstract, so we need to define the tick and render methods	
	//Animations
	
	private Animation animRight, animLeft;
	private static long lastAttack = System.currentTimeMillis();
	
	private long attackCooldown = 150;
	private int punchDamage, lastDirection = 0;
	
	private boolean playerActive = true;
	Rectangle punchRect;
	
	//player dimensions
	private static int PLAYER_WIDTH, PLAYER_HEIGHT;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Integer.parseInt(handler.getPropertiesFile().getProperty("playerWidth"))*2, 
				Integer.parseInt(handler.getPropertiesFile().getProperty("playerHeight"))*2);				//no ponemos PLAYER_WIDTH & HEIGHT directamente en super porque tienen que inicializarse
																											//en el constructor, ya que necesitamos handler para acceder al fichero de propiedades
		punchDamage = Integer.parseInt(handler.getPropertiesFile().getProperty("playerPunchDmg"));
		
		PLAYER_WIDTH = Integer.parseInt(handler.getPropertiesFile().getProperty("playerWidth"));
		PLAYER_HEIGHT = Integer.parseInt(handler.getPropertiesFile().getProperty("playerHeight"));
		
		//animations
		animRight = new Animation(80, Assets.playerRightAnim);
		animLeft = new Animation(80, Assets.playerLeftAnim);
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = PLAYER_WIDTH*2;
		bounds.height = PLAYER_HEIGHT*2;
	}


	public void tick() {
		
		handler.getGameCamera().centerOnEntity(this); //to center THIS player
		
		if(playerActive) {
		
			//Animations
			animRight.tick();
			animLeft.tick();
			
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
			punch();
		}	
	}
	
	public void punch() {
		long attackNow = System.currentTimeMillis();
		long attackEnabler = attackNow - lastAttack;
        
		if (attackEnabler > attackCooldown) {
        	lastAttack = attackNow;

			punchRect = new Rectangle();
			punchRect.width = 50;
			punchRect.height = 30;

			if(xMove<0 || lastDirection == 0) {
				punchRect.x = (int) (x + bounds.x - punchRect.width); 								
				punchRect.y = (int) (y + bounds.y + bounds.height/2 - punchRect.height/2);
				// LOGGER
				Game.LOGGER.log(Level.FINEST,"Le pega con kickRect izquierda");
			}
			if(xMove>0 || lastDirection == 1) {
				punchRect.x = (int) (x + bounds.x + bounds.width); 						
				punchRect.y = (int) (y + bounds.y + bounds.height/2 - punchRect.height/2);
				// LOGGER
				Game.LOGGER.log(Level.FINEST,"Le pega con kickRect derecha");
			}
			if(yMove<0 || lastDirection == 2) {
				punchRect.x = (int) (x + bounds.x + bounds.width/2 - punchRect.width/2); 
				punchRect.y = (int) (y + bounds.y - punchRect.height);
				// LOGGER
				Game.LOGGER.log(Level.FINEST,"Le pega con kickRect arriba");
			}
			if(yMove>0 || lastDirection == 3) {
				punchRect.x = (int) (x + bounds.x + bounds.width/2 -punchRect.width/2);  
				punchRect.y = (int) (y + bounds.y + bounds.height);
				// LOGGER
				Game.LOGGER.log(Level.FINEST,"Le pega con kickRect abajo");
			}
			
			//al crear el rectángulo, comprobamos también si éste coincide con cualquier otro de las entidades (si le ha dado una patada)
			//y si es el caso, quitamos vida a la entidad que ha sido golpeada.
			
			for(main.entities.Entity e : handler.getWorld().getEntityManager().getEntities()) {
				
				if(e.equals(this)) {
	            	continue; //pasa al siguiente valor del for loop ya que no nos queremos herir a nosotros
	            	
	            }if(e.getCollisionBounds(0f, 0f).intersects(punchRect)) {	//le pasamos un offset de 0 porque hasta el momento en cada entidad estamos aplicando el offset.
	            	
	            	e.hurt(punchDamage);										//sólo herimos a cada entidad una vez
	                return;
	            }
			}
		}
	}	

	public void render(Graphics g) {
		
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), PLAYER_WIDTH*2, PLAYER_HEIGHT*2, null);
		// ESTO HACE QUE SE VEA KICKRECT
		if(punchRect != null && handler.getKeyManager().space) { 
			g.setColor(Color.BLUE);
			g.fillRect((int) (punchRect.x - handler.getGameCamera().getxOffset()), (int) (punchRect.y - handler.getGameCamera().getyOffset()), punchRect.width, punchRect.height);
		}
		
	}
	
	private BufferedImage getCurrentAnimationFrame() {		
		
		if(xMove < 0 && yMove != 0) { //moves left and in any direction in the y axis
			lastDirection = 0;
			return animLeft.getCurrentFrame();
		}else if(xMove > 0 && yMove != 0) { //moves right and in any direction in the y axis
			lastDirection = 1;
			return animRight.getCurrentFrame();
		}else if(xMove < 0) { //only moves left
			lastDirection = 0;
			return animLeft.getCurrentFrame(); 
		}else if(xMove > 0) { //only moves right
			lastDirection = 1;
			return animRight.getCurrentFrame();
		}else if(yMove != 0) { //only moves up or down
			if(lastDirection == 0) return animLeft.getCurrentFrame();
			else return animRight.getCurrentFrame();
		}else {
			if(lastDirection == 1) return Assets.player_standStill_right;
			else return Assets.player_standStill_left;
		}
	}
	
	public void setPlayerActive() {
		playerActive = true;
	}

	public void setPlayerInactive() {
		playerActive = false;
	}
	
	public float getPlayerX() {
		return this.x;
	}
	public float getPlayerY() {
		return this.y;
	}
	
	public void setPlayerX(int user) {
		this.x = GameDB.getGamePlayerXPosition(user);
	}
	public void setPlayerY(int user) {
		this.y = GameDB.getGamePlayerYPosition(user);
	}
}
