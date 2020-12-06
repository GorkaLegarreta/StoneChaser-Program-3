package main.gfx;

import main.Handler;
import main.entities.Entity;

public class GameCamera {
	
	private float xOffset, yOffset; //how much is added or subtracted from the original position
	private Handler handler;
	
	public GameCamera(Handler game, float xOffset, float  yOffset) {
		this.handler = game;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void centerOnEntity(Entity e) {
		xOffset = e.getX() - handler.getWidth() / 2 - e.getWidth() / 2; //camera centered on the x coordinate of screen.
		yOffset = e.getY() - handler.getHeight() / 2 - e.getHeight() / 2; //centered on the y.
		checkBlankSpace();
	}
	
	public void checkBlankSpace() {
		
	}
	
	public void move(float xAmt, float yAmt) {
		xOffset += xAmt;
		yOffset += yAmt;
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

}