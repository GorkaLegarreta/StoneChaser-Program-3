package main.states;

import java.awt.Graphics;

import main.GameDBException;
import main.Handler;

public abstract class State {
	
	private static State currentState = null;

	public static void setState(State state) {
		currentState = state;
	}
	
	public static State getState() {
		return currentState;
	}
	
	//Class
	
	protected static Handler handler;
	
	@SuppressWarnings("static-access")
	public State(Handler handler) {
		this.handler = handler;
	}
	
	public abstract void tick() throws GameDBException;
	
	public abstract void render(Graphics g);
	
}