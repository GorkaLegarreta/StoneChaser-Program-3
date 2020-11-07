package main.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Item {

	protected String name;
	protected int x, y, itemQuantity;
	protected boolean active;
	
	protected Item[] items = new Item[30];
	
	protected static final int DEFAULT_ITEM_WIDTH = 20, DEFAULT_ITEM_HEIGHT = 20;
	
	protected Rectangle itemBounds;
	//protected Rectangle playerBounds = 
			
	public Item(String name, int x, int y, int itemQuantity, boolean active) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.itemQuantity = itemQuantity;
		this.active = active;
		
		itemBounds = new Rectangle(x, y, DEFAULT_ITEM_WIDTH, DEFAULT_ITEM_HEIGHT);
	}

	public void tick() {
		
		//if(handler)
		init();
	}
	
	public void init() {
		
		Item wood = new Item("wood", 20, 20, 1, true);
		
		
	}
	
	public void render(Graphics g) {
		
		
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, DEFAULT_ITEM_WIDTH, DEFAULT_ITEM_HEIGHT);
		g.drawString("" + itemQuantity, x + 10, y + 35);
	}

	public Item createItem(String name, int x, int y, int itemQuantity, boolean active) {
		Item i = new Item(name, x, y, itemQuantity, active);
		
		itemBounds.x = x;
		itemBounds.y = y;
		
		return i;
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

	public boolean isActive() {
		return active;
	}

	public void setActive() {
		active = true;
	}
	
}