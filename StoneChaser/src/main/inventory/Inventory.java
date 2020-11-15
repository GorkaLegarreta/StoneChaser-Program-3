package main.inventory;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.Handler;
import main.items.Item;

public class Inventory {

	private long dropNow, enableDrop, lastDrop, noDropNow, noDropBefore, dropUnableMsg;
	
	protected BufferedImage inventoryImg;
	
	public Handler handler;
	
	protected Item lastGathered;
	
	private LinkedList<Item> inventory = new LinkedList<Item>();
	
	public Inventory(Handler handler) {
		
		this.handler = handler;
	}

	public void tick() {
		
		if(handler.getKeyManager().e) {
			
			System.out.println("INVENTARIO");
			for(Item i : inventory) {
				System.out.println(i.getItemQuantity() + " " + i.getName());
			}	
		}
		
		if(handler.getKeyManager().f) {
			
			if(!inventory.isEmpty())
				lastGathered = inventory.getLast();
			else
				lastGathered = null;			
			
			if(!(lastGathered == null)) {
				
				dropNow = System.currentTimeMillis();
				enableDrop = dropNow - lastDrop;
				
				if(enableDrop > 500) {
					lastDrop = dropNow;
					
					inventory.remove(lastGathered);
					
					lastGathered.setActive();
					lastGathered.setPosition((handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).x + 70), (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).y));
				}
				
			}else {
				
				noDropNow = System.currentTimeMillis();
				dropUnableMsg = noDropNow - noDropBefore;
				
				if(dropUnableMsg > 2000) {
					System.out.println("First pick an item to be able to drop it");
					noDropBefore = noDropNow;
				}	
			}
		}
	}
	
	public void render(Graphics g) {
		
	}
	
	public void addToInventory(Item item) {
		inventory.add(item);
		lastGathered = item;
		
	}

	public Item getLastGathered() {
		return lastGathered;
		
	}

	public void setLastGathered(Item lastGathered) {
		this.lastGathered = lastGathered;
	}
	
}
