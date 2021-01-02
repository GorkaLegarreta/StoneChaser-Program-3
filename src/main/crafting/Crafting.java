package main.crafting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import main.Handler;
import main.gfx.Assets;
import main.items.Item;
import main.items.ItemManager;
import main.utilities.Position;

public class Crafting {

	protected Handler handler;
	
	private Item resultadoCrafteo, grabbedItem;
	
	private Item[] crafteo = new Item[9];
	
	private ArrayList<Integer> notNull = new ArrayList<Integer>();
	
	//anchura y altura real de la mesa de crafteo: 200 y 186 respectivamente.
	private int item, posicion, craftingTableWidth = 280, craftingTableHeight = 266, invSlotsWidth = 219, invSlotsHeight = 75, 
			craftingTableX, craftingTableY, inventoryX, inventoryY, grabbedItemIndex;
	
	private boolean c = false, callCraft = false;

	private float ratio = 1500000000, update;
	private long now, before = System.nanoTime();

	public Crafting(Handler handler) {
		this.handler = handler;
		
		craftingTableX = (handler.getWidth()/2) - (143); 
		craftingTableY = (handler.getHeight()/2) - (185);
		inventoryX = (int) (handler.getWidth()/2 - 384/2);
		inventoryY = (int) (handler.getHeight() - 61);
	}
	
	public void craft() {
	
		updateNotNullIndexes();
		
		if(notNull.size() == 2) {
			if(crafteo[notNull.get(0)].getId() == 4 && crafteo[notNull.get(0)].getItemQuantity() >= 2 && crafteo[notNull.get(1)].getId() == 2) {
				resultadoCrafteo = ItemManager.espadaHierro.createItem(0, 0, 1);
				resultadoCrafteo.setInactive();
				//resultadoCrafteo.setPosition(493, 168);
				resultadoCrafteo.setPosition(handler.getWorld().getInventory().getOutcomePosition().setOutcomePosition(resultadoCrafteo));
				handler.getWorld().getInventory().setCraftingOutcome(resultadoCrafteo);
			}
			
		}else {
			resultadoCrafteo = null;
			handler.getWorld().getInventory().setCraftingOutcome(resultadoCrafteo);
		}
	}
	
	public void render(Graphics g) {
		
		if(c == true) {
			g.drawImage(Assets.craftingTable, craftingTableX, craftingTableY, craftingTableWidth, craftingTableHeight, null);
			g.drawImage(Assets.inventarioDesplegado, inventoryX, inventoryY, 384, 61, null);
			g.drawImage(Assets.craftingOutcome, 530, 118, 62, 61, null);
			for (Item i : crafteo) {
				if(i != null)i.render(g);
			}
		}
	}
	
	public void tick() {
		
		if(handler.getKeyManager().e && c == false) {		//update solo funciona cuando se activa el inventario, entonces no llegará a ser un valor tan alto que crashee el programa.
															
			now = System.nanoTime();			
			update += (now - before)/ratio;									
			
			if(update >= 1) {
				before = System.nanoTime();				
				c = true;
				handler.getWorld().getInventory().displayFullInv();
				update = 0;
				handler.spotlightEnabler();
				handler.getWorld().getInventory().stopDrawBasicInv();
				handler.getWorld().getInventory().moveItemsEnabled();
				callCraft = true;
				handler.getWorld().getEntityManager().getPlayer().setPlayerInactive();
			}			
			
		}else if(handler.getKeyManager().e && c == true) {
			
			now = System.nanoTime();			
			update += (now - before)/ratio;									
			
			if(update >= 1) {
				before = System.nanoTime();				
				c = false;
				handler.getWorld().getInventory().displayShortInv();
				update = 0;
				handler.spotlightDisabler();
				handler.getWorld().getInventory().drawBasicInv();
				handler.getWorld().getInventory().moveItemsDisabled();
				callCraft = false;
				handler.getWorld().getEntityManager().getPlayer().setPlayerActive();
			}
			
		}
		
		if(c == true) {
			
		}
		
		
		
		if(callCraft) craft();	
	}
	
	public void updateNotNullIndexes() {
		
		notNull.clear();
		for (int i = 0; i < crafteo.length; i++) if(crafteo[i] != null) notNull.add(i);
		
	}
	
	public void setItemAndIndex(Item i, int n) {
		this.grabbedItem = i;
		this.grabbedItemIndex = n;
	}

	public Item[] getCrafteo() {
		return crafteo;
	}
	
	public int getCraftingTableX() {
		return craftingTableX;
	}

	public int getCraftingTableY() {
		return craftingTableY;
	}

	public int getInventoryX() {
		return inventoryX;
	}

	public int getInventoryY() {
		return inventoryY;
	}
	
}