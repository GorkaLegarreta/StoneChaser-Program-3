package main.utilities;

import main.items.Item;

public class Position {
	//clase simple para organizar posiciones
	
	private int x, y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Position setInvPosition(Item i) {
		return new Position(x + 22 - i.getWidth()/2, y + 21 - i.getHeight()/2);	
	}
	
	public Position setCraftPosition(Item i) {
		return new Position(x + 31 - i.getWidth()/2, y + 30 - i.getHeight()/2);	
	}
	
}
