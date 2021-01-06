package main.crafting;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import main.Handler;
import main.gfx.Assets;
import main.items.Item;
import main.items.ItemManager;

public class Crafting {

	protected Handler handler;
	
	private Item craftingOutcome, grabbedItem;
	
	private Item[] crafteo = new Item[9];
	
	private ArrayList<Integer> notNull = new ArrayList<Integer>();
	
	//anchura y altura real de la mesa de crafteo: 200 y 186 respectivamente.
	private int item, posicion, craftingTableWidth = 280, craftingTableHeight = 266, invSlotsWidth = 219, invSlotsHeight = 75, 
			craftingTableX, craftingTableY, inventoryX, inventoryY, grabbedItemIndex;
	
	private boolean c = false, craft = false;

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
		
		
		if(notNull.size() == 2 && notNull.get(0) == 4 && notNull.get(1) == 7 && crafteo[notNull.get(0)].getName() == "hierro" && 
				crafteo[notNull.get(0)].getItemQuantity() >= 2 && crafteo[notNull.get(1)].getName() == "palo") {
			
			craftingOutcome = ItemManager.espadaHierro.createItem(0, 0, 1);
			craftingOutcome.setInactive();
			craftingOutcome.fixItemPosition();
			craftingOutcome.setPosition(handler.getWorld().getInventory().getOutcomePosition().setOutcomePosition(craftingOutcome));
			handler.getWorld().getInventory().setCraftingOutcome(craftingOutcome);
			if(craft) toBeDecreased(1);
		}else {
			craftingOutcome = null;
			handler.getWorld().getInventory().setCraftingOutcome(craftingOutcome);
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
		
		checkItemQuantities();
		
		if(handler.getKeyManager().e && c == false) {		//update solo funciona cuando se activa el inventario, entonces no llegará a ser un valor tan alto que crashee el programa.
															
			now = System.nanoTime();			
			update += (now - before)/ratio;									
			
			if(update >= 1) {
				before = System.nanoTime();				
				c = true;
				update = 0;
				handler.spotlightEnabler();
				Item.adderEnabled = false;
				handler.getWorld().getInventory().stopDrawBasicInv();
				handler.getWorld().getInventory().moveItemsEnabled();
				handler.getWorld().getEntityManager().getPlayer().setPlayerInactive();
			}			
			
		}else if(handler.getKeyManager().e && c == true) {
			
			now = System.nanoTime();			
			update += (now - before)/ratio;									
			
			if(update >= 1) {
				before = System.nanoTime();				
				c = false;
				update = 0;
				handler.spotlightDisabler();
				Item.adderEnabled = true;				
				putItemsBackInInv();
				handler.getWorld().getInventory().drawBasicInv();
				handler.getWorld().getInventory().moveItemsDisabled();
				handler.getWorld().getEntityManager().getPlayer().setPlayerActive();
			}
			
		}
		
		if(c == true) {
			craft();
		}
		
		
		
			
	}
	
	public void putItemsBackInInv() {
		for (Item item : crafteo) if(item != null) handler.getWorld().getInventory().addToInventory(item);
		for (int i = 0; i < crafteo.length; i++) crafteo[i] = null;
	}
	
	public void updateNotNullIndexes() {
		
		notNull.clear();
		for (int i = 0; i < crafteo.length; i++) if(crafteo[i] != null) notNull.add(i);
		
	}
	
	public void checkItemQuantities() {
		
		for (int i = 0; i < crafteo.length; i++) {
			if(crafteo[i] != null && crafteo[i].getItemQuantity() < 1) crafteo[i] = null;
		}	
	}
	
	public void setItemAndIndex(Item i, int n) {
		this.grabbedItem = i;
		this.grabbedItemIndex = n;
	}

	public void toBeDecreased(int i) {
		if( i == 1 ) {
			crafteo[notNull.get(0)].decreaseItemQuantity(2);
			crafteo[notNull.get(1)].decreaseItemQuantity(1);
		}
		craft = false;		//despues de craftear volvemos a poner esta variable en falso para que si se quiere craftear otra vez se tenga que volver a pulsar el boton de craftear
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
	
	public void setCraftOn() {
		this.craft = true;
	}
	
}