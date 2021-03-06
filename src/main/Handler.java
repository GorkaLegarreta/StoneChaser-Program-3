package main;

import java.io.Serializable;
import java.util.Properties;
import main.gfx.GameCamera;
import main.input.KeyManager;
import main.input.MouseManager;
import main.input.ScreenMouseMovement;
import main.worlds.JungleWorld;

public class Handler implements Serializable{

	
	private static final long serialVersionUID = -8869259633233223110L;
	
	private Game game;
	private JungleWorld world;
	private int user;			//numero de usuario	
	
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
	
	public Properties getPropertiesFile() {
		return game.getPropertiesFile();
	}
	
	public void setUser(int user) {
		this.user = user;
	}
	
	public int getUser() {
		return user;
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public JungleWorld getWorld() {
		return world;
	}

	public void setWorld(JungleWorld world) {
		this.world = world;
	}
}
