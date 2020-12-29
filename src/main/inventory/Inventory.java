package main.inventory;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import main.Handler;
import main.gfx.Assets;
import main.items.Item;

public class Inventory {

	private long noDropNow, noDropBefore, enableDropMsg;
	private boolean renderEnabler = false, selectItem1 = false, selectItem2 = false, 
			paintPointerAt1 = false, paintPointerAt2 = false;
	private int length;
	
	protected BufferedImage inventoryImg;
	
	public Handler handler;
	
	protected Item selectedItem;
	
	protected int selectedSlot = -1;
	
	//private LinkedList<Item> inventory = new LinkedList<Item>();
	private Item[] inventory = new Item[6];
	
	public Inventory(Handler handler) {
		
		this.handler = handler;
	}
	
	public void tick() {		
		
		if(handler.getKeyManager().f) {
			
			if(selectedSlot != -1) {
				if(inventory[selectedSlot] != null) {	
							
					inventory[selectedSlot].unFixItemPosition();
					inventory[selectedSlot].setActive();
					inventory[selectedSlot].setPosition((handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).x + ThreadLocalRandom.current().nextInt(85, 120 + 1)), (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).y + ThreadLocalRandom.current().nextInt(0, 120 + 1)));
					
					inventory[selectedSlot] = null;
						
				}else {
						
					noDropNow = System.currentTimeMillis();
					enableDropMsg = noDropNow - noDropBefore;
						
					if(enableDropMsg > 800) {
						System.out.println("This item slot is empty");
						noDropBefore = noDropNow;
					}
						
				}
			}else {System.out.println("First select an item with '1' or '2' keys");}
		}
		
		if(handler.getKeyManager().one) {
			paintPointerAt2 = false; paintPointerAt1 = true;
			selectedSlot = 0;
		}

		if(handler.getKeyManager().two) {
			paintPointerAt1 = false; paintPointerAt2 = true;
			selectedSlot = 1;
		}
	}
	
	public void render(Graphics g) {
		
		if(inventory[0] != null) inventory[0].render(g);
		if(inventory[1] != null) inventory[1].render(g);
		
		if(renderEnabler) {
			for (int i = 2; i < inventory.length; i++) {
				if(inventory[i] != null) inventory[i].render(g);
			}
		}
		
		if(paintPointerAt1) g.drawImage(Assets.itemPointers, handler.getWorld().getCrafting().getInventoryX() + 128, handler.getWorld().getCrafting().getInventoryY(), 66, 64, null);
		if(paintPointerAt2) g.drawImage(Assets.itemPointers, handler.getWorld().getCrafting().getInventoryX() + 190, handler.getWorld().getCrafting().getInventoryY(), 66, 64, null);
	}
	
	public void addToInventory(Item item) {
		if(inventory.length <= 6) {
			itemAdder(item);
						
			//TODO otro setposition en el que se le pueda pasar una posicion directamente, y tengamos posiciones ya guardadas de
			//distintos slots del inventario, entonces cuando se vaya a guardar que se compruebe la posicion en la que se deba guardar.
			
		}else { System.out.println("The inventory is full");}
	}
	
	public void itemAdder(Item i) {
		//siempre va a funcionar con inventory, por eso le pasamos sólo el item como parámetro.
		
		Item alreadyInInv = null;
		int index = -1;
		
		for (int c = 0; c < inventory.length; c++) {
			
			if(inventory[c] != null) {
				if(inventory[c].getId() == i.getId()) {
					
					alreadyInInv = inventory[c];
					index = c;
					break;
				}
			}
		}
		
		if(alreadyInInv != null) {
			inventory[index].increaseItemQuantity(i.getItemQuantity());
			i.setInactive();
		}
		else if(inventory[5] == null) {
			
			for(int k = 0; k < inventory.length; k++) {
				if(inventory[k] == null) {
					inventory[k] = i;		//añado el item al espacio con menor índice del inventario (el primero libre)
					length = k;
					break;
				}
			}
			
			//al entrar en el if el numero de elementos del array será 1 min y 6 max, ahora dependiendo de el numero este elemento se pintará donde su posicion del array;
			
			i.setInactive();
			
			if(length == 0) i.setPosition(handler.getWidth()/2 - i.getWidth() - 8, handler.getHeight() - i.getHeight() - 10);
			else if(length == 1)i.setPosition(handler.getWidth()/2 + i.getWidth() - 20, handler.getHeight() - i.getHeight() - 10);
			else if(length == 2) i.setPosition(handler.getWidth()/2 - i.getWidth() - 70, handler.getHeight() - i.getHeight() - 10);
			else if(length == 3) i.setPosition(handler.getWidth()/2 - i.getWidth() - 130, handler.getHeight() - i.getHeight() - 10);
			else if(length == 4) i.setPosition(handler.getWidth()/2 - i.getWidth() + 112, handler.getHeight() - i.getHeight() - 10);
			else i.setPosition(handler.getWidth()/2 - i.getWidth() + 172, handler.getHeight() - i.getHeight() - 10);
			
			i.fixItemPosition();
			
		}else { System.out.println("The inventory is full"); }
		
	}
	
	public void displayFullInv() {
		this.renderEnabler = true;
	}
	
	public void displayShortInv() {
		this.renderEnabler = false;
	}

	public void selectItem1() {
		this.selectItem1 = true;
	}

	public void deselectItem1() {
		this.selectItem1 = false;
	}
	
	public void selectItem2() {
		this.selectItem1 = true;
	}

	public void deselectItem2() {
		this.selectItem1 = false;
	}	
	
}