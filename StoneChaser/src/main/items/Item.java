package main.items;

import java.awt.Color;
import java.awt.Graphics;

public class Item {

	protected String name;
	protected int x, y, itemQuantity;
	
	public Item(String name, int x, int y, int itemQuantity) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.itemQuantity = itemQuantity;
	}

	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, 20, 20);
	}
	
}