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
	
	//protected static final int DEFAULT_ITEM_WIDTH = 20, DEFAULT_ITEM_HEIGHT = 20;
	
	protected Rectangle itemBounds;
	
	public Item(String name, BufferedImage img, int x, int y, int width, int height, int itemQuantity, int id, boolean active, Handler handler, Inventory inv) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.itemQuantity = itemQuantity;
		this.active = active;
		this.id = id;
		this.handler = handler;
		this.inv = inv;
		this.img = img;
		
		itemBounds = new Rectangle(x, y, width, height);
	}
	
	public void tick() {
		
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(itemBounds) && active) {
			setInactive();
			inv.addToInventory(this);
			System.out.println(name + " has been picked up (hold E to open inventory, press F to drop)");			
			
		}
	}
	
	public void render(Graphics g) {
		
		
		g.drawImage(img, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		g.setColor(Color.WHITE);
		g.drawString("" + itemQuantity, (int) (x + width - handler.getGameCamera().getxOffset()), (int) (y + height + 5 - handler.getGameCamera().getyOffset()));
	
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
	
	public void setInactive() {
		active = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}