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
		g.drawString("" + itemQuantity, x + 10, y + 35);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
}