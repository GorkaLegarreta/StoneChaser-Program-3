package main.inventory;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import main.Handler;
import main.items.Item;

public class Inventory {

	private long dropNow, enableDrop, lastDrop, noDropNow, noDropBefore, dropUnableMsg;
	private boolean renderEnabler = false;
	
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
			
			if(!inventory.isEmpty()) lastGathered = inventory.getLast();	//ultimo elemento de la lista (en un linkedlist con getlast conseguimos el ultimo elemento introducido)
			else lastGathered = null;			
			
			if(!(lastGathered == null)) {
				
				dropNow = System.currentTimeMillis();
				enableDrop = dropNow - lastDrop;
				
				if(enableDrop > 500) {
					lastDrop = dropNow;
					
					inventory.remove(lastGathered);
					lastGathered.unFixItemPosition();
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
		
		if(inventory.size() >= 1) inventory.get(0).render(g);
		if(inventory.size() >= 2) inventory.get(1).render(g);
		
		if(renderEnabler) {
			for (int i = 2; i < inventory.size(); i++) {
				inventory.get(i).render(g);
			}
		}
		
	}
	
	public void addToInventory(Item item) {
		if(inventory.size() <= 6) {
			itemAdder(item);
						
			//TODO otro setposition en el que se le pueda pasar una posicion directamente, y tengamos posiciones ya guardadas de
			//distintos slots del inventario, entonces cuando se vaya a guardar que se compruebe la posicion en la que se deba guardar.
			
		}else { System.out.println("The inventory is full");}
	}
	
	public void itemAdder(Item i) {
		//siempre va a funcionar con inventory, por eso le pasamos sólo el item como parámetro.
		
		Item alreadyInInv = null;
		int index = -1;
		
		for (Iterator iterator = inventory.iterator(); iterator.hasNext();) {
			Item item = (Item) iterator.next();
			
			if(item.getId() == i.getId()) {
				
				alreadyInInv = item;
				index = inventory.indexOf(item);
				break;
			}
			
		}
		
		if(alreadyInInv != null) {
			inventory.get(index).increaseItemQuantity(i.getItemQuantity());
			i.setInactive();
			lastGathered = inventory.get(index);
		}
		else if(inventory.size() <= 1) {
			inventory.add(i);		//al entrar en el if el tamaño del inventario será como mínimo 1 y como max 2 gracias al add(i);
			
			i.setInactive();
			
			if(inventory.size() == 1) i.setPosition(handler.getWidth()/2 - i.getWidth() - 8, handler.getHeight() - i.getHeight() - 10);
			else i.setPosition(handler.getWidth()/2 + i.getWidth() - 20, handler.getHeight() - i.getHeight() - 10);
			
			lastGathered = i;
			i.fixItemPosition();
			
		}else if(inventory.size() >= 2 && inventory.size() <= 5) {
			inventory.add(i);
			
			i.setInactive();			
			
			System.out.println(inventory.size());
			
			if(inventory.size() <= 3) i.setPosition(handler.getWidth()/2 - i.getWidth() - 70, handler.getHeight() - i.getHeight() - 10);
			else if(inventory.size() <= 4) i.setPosition(handler.getWidth()/2 - i.getWidth() - 130, handler.getHeight() - i.getHeight() - 10);
			else if(inventory.size() <= 5) i.setPosition(handler.getWidth()/2 - i.getWidth() + 112, handler.getHeight() - i.getHeight() - 10);
			else i.setPosition(handler.getWidth()/2 - i.getWidth() + 172, handler.getHeight() - i.getHeight() - 10);
			
			
			lastGathered = i;
			i.fixItemPosition();
			
		}else { System.out.println("The inventory is full"); }
		
	}

	public Item getLastGathered() {
		return lastGathered;
		
	}

	public void setLastGathered(Item lastGathered) {
		this.lastGathered = lastGathered;
	}
	
	public void displayFullInv() {
		this.renderEnabler = true;
	}
	
	public void displayShortInv() {
		this.renderEnabler = false;
	}
	
}