package main;

import main.gfx.GameCamera;
import main.input.KeyManager;
import main.input.MouseManager;
import main.worlds.World;

public class Handler {

	private Game game;
	private World world;
	private Menu menu;
	
		
	public Handler(Game game) {
		this.game = game;
	}
	public Handler(Menu menu) {
		this.menu = menu;
	}

	public int getWidth() {
		return this.getWidth();
	}
	
	public int getHeight() {
		return this.getHeight();
	}
	
	public KeyManager getKeyManager() {
		return this.getKeyManager();
	}
	
	public MouseManager getMouseManager() {
		return this.getMouseManager();
	}
	
	public GameCamera getGameCamera() {
		return this.getGameCamera();
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
		
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
}
