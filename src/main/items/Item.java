package main.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import main.Game;
import main.Handler;
import main.gfx.Assets;
import main.inventory.Inventory;

public class Item {

	protected String name;
	protected int x, y, itemQuantity, id;
	protected int width, height;
	protected boolean active;
	
	protected Handler handler;
	protected Inventory inv;
	protected Color c;
	protected BufferedImage img;
	
	//
	public int xOffset, yOffset, isFixed = 1;
	
	//protected static final int DEFAULT_ITEM_WIDTH = 20, DEFAULT_ITEM_HEIGHT = 20;
	
	protected Rectangle itemBounds;
	
	public Item(String name, BufferedImage img, int width, int height, int id, Handler handler, Inventory inv) {
		this.name = name;
		this.img = img;
		this.width = width;
		this.height = height;
		this.id = id;
		this.handler = handler;
		this.inv = inv;
		
		itemBounds = new Rectangle(x, y, width, height);
	}
	
	public Item createItem(int x, int y, int itemQuantity) {
		
		Item i = new Item(name, img, width, height, id, handler, inv);
		
		i.setPosition((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
		i.setItemQuantity(itemQuantity);
		i.setActive();
		return i;
	}
	
	public void tick() {
		
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(itemBounds) && active) {
			inv.addToInventory(this);
			System.out.println(name + " has been picked up (hold E to open inventory, press F to drop)");			
			
		}
	}
	
	public void render(Graphics g) {
		
		
		g.drawImage(img, itemX(), itemY(), width, height, null);
		g.setColor(Color.WHITE);
		g.drawString("" + itemQuantity, itemX(), itemY());
	}
	
	public int itemX() {
		xOffset = (int) handler.getGameCamera().getxOffset() * isFixed;
		return x - xOffset;
	}
	
	public int itemY() {
		yOffset = (int) handler.getGameCamera().getyOffset() * isFixed;
		return y - yOffset;
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
	
	public void fixItemPosition(){
		isFixed = 0;
	}
	
	public void unFixItemPosition() {
		isFixed = 1;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		itemBounds.x = x;
		itemBounds.y = y;
	}

	public int getY() {
		return y;
	}

	public Rectangle getItemBounds() {
		return itemBounds;
	}

	public void setItemBounds(Rectangle itemBounds) {
		this.itemBounds = itemBounds;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
	public int getItemQuantity() {
		return itemQuantity;
	}

	public void increaseItemQuantity(int increaseQuantity) {
		this.itemQuantity += increaseQuantity;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive() {
		active = true;
	}
	
	public void setInactive() {
		active = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}