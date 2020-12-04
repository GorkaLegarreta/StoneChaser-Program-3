package main.states;

import java.awt.Graphics;
import java.util.logging.Level;

import main.Game;
import main.Handler;
import main.entities.creatures.Player;
import main.worlds.World;
import main.entities.statics.*;

public class GameState extends State{
	
	
	private World world;
	
	public GameState(Handler handler) {
		super(handler);
		
		world = new World(handler);
		handler.setWorld(world);
	}
	
	public void tick() { 
		world.tick();
		
		//handler.getGameCamera().move(1, 0); //para establecer la posicion del jugador, pero tiene que ser en un init porque si no suma el valor y se va moviendo la cámara (cinemáticas?).
	}
	
	public void render(Graphics g) {
		world.render(g);	
	}
}