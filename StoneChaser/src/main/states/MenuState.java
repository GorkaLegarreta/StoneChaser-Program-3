package main.states;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.Handler;
import main.input.MouseManager;

public class MenuState extends State {
	
	private Rectangle rect;
	private MouseManager mouseManager;
	
	public MenuState(Handler handler) {
		super(handler);
		rect = new Rectangle(0,0,10,10);
		
		mouseManager = new MouseManager();
		
	}
	/*
	 * tick se comprueba todo el rato
	 */
	@Override
	public void tick() {	
		if(handler.getMouseManager().isLeftPressed() && rect.contains(mouseManager.getMouseX(), mouseManager.getMouseY()) ) {
			State.setState(handler.getGame().gameState);
			System.out.println("Estoy pulsando aqui");
		}
	}

	@Override
	public void render(Graphics g) {		
		
	}
	
}
