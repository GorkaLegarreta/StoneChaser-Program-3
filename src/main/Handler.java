package main;

import main.gfx.GameCamera;
import main.input.KeyManager;
import main.input.MouseManager;
import main.input.ScreenMouseMovement;
import main.worlds.World;

public class Handler {

	private Game game;
	private World world;
		
	public Handler(Game game) {
		this.game = game;
	}
	

	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	
	public ScreenMouseMovement getMouseMovement() {
		return game.getMouseMovement();
	}
	
	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}
	
	public void spotlightEnabler() {
		game.spotlightEnabler();
	}
	
	public void spotlightDisabler() {
		game.spotlightDisabler();
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
}
