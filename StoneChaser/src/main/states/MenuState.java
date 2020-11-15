package main.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.logging.Level;

import main.Game;
import main.Handler;
import main.input.MouseManager;

public class MenuState extends State  {
	
	private Rectangle play;
	private MouseManager mouseManager;
	
	public MenuState(Handler handler) {
		super(handler);
		play = new Rectangle(237,162,225,75); // Boton en el centro de la pantalla (de momento se puede quedar)
		mouseManager = handler.getMouseManager();
		
	}
	/*
	 * tick se comprueba todo el rato
	 */
	@Override
	public void tick() {	
		if(handler.getMouseManager().isLeftPressed() && play.contains(mouseManager.getMouseX(), mouseManager.getMouseY()) ) {
			State.setState(handler.getGame().gameState);
			Game.LOGGER.log(Level.FINE,"Se ha pulsado Start y GameState cargada e iniciada");
			Game.LOGGER.info("Se ha pulsado Start y GameState cargada e iniciada");	

			
		}
	}

	@Override
	public void render(Graphics g) {	
		/*
		 * Boton Start
		 */
		Color c = new Color(255, 200, 120);
		g.setColor(c);		
		g.fillRoundRect(play.x,play.y,play.width,play.height, 35, 35);
		c = c.darker();
		g.setColor(c);		
		g.drawRoundRect(play.x,play.y,play.width,play.height, 35, 35);
		c = c.darker();
		g.setColor(c);
		g.drawString("START", 330, 205);
	}
	
}
