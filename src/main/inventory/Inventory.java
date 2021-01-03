package main.inventory;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import main.Handler;
import main.gfx.Assets;
import main.items.Item;
import main.states.GameState;
import main.utilities.Position;

public class Inventory extends Thread{

	private long noDropNow, noDropBefore, enableDropMsg;
	@SuppressWarnings("unused")
	private boolean renderEnabler = false, selectItem1 = false, selectItem2 = false, 
			paintPointerAt1 = false, paintPointerAt2 = false, enableDrag = false, drawBasicInv = true, enableSwap = true,
			moveItemsEnabled = false, hasDraggedWasRun = false, draggingItem = false, indexOfCraft = false, craftingArray = false;
	
	//AtomicBoolean para ejecutar código sólo una vez cuando se requiera
	
	private AtomicBoolean wasRun = new AtomicBoolean(false);
	
	//imagen del inventario
	
	protected BufferedImage inventoryImg;
	
	public Handler handler;
	
	//objetos de Item para hacer cambios en el inventario
	
	protected Item selectedItem, lastGrabbedItem, itemToSwap;
	
	//creamos un array de 6 posiciones para el inventario y otro de 9 para el crafteo en las que pondremos items y cajas de detección de clicks
	
	private Position[] invPositions = new Position[6], craftPositions = new Position[9];
	
	//creamos los rectángulos de detección de clicks para inventario y crafteo
	
	private Rectangle[] invSlots = new Rectangle[6], craftSlots = new Rectangle[9];
	
	//enteros para manejar las posiciones en los arrays
	
	protected int selectedSlot = -1, lastGrabbedItemIndex, length, itemToSwapIndex;
	
	//array de items para los huecos del inventario
	
	private static Item[] inventory = new Item[6];
	
	public Inventory(Handler handler) {
		
		this.handler = handler;
		
		//llenamos los arrays de posiciones del inventario y de la mesa de crafteo, lo hacemos aquí porque necesitamos handler.
		
		invPositions[0] = new Position(handler.getWidth()/2 - 53, handler.getHeight() - 49);
		invPositions[1] = new Position(handler.getWidth()/2 + 10, handler.getHeight() - 49);
		invPositions[2] = new Position(handler.getWidth()/2 - 114, handler.getHeight() - 49); 
		invPositions[3] = new Position(handler.getWidth()/2 - 176, handler.getHeight() - 49); 
		invPositions[4] = new Position(handler.getWidth()/2 + 72, handler.getHeight() - 49); 
		invPositions[5] = new Position(handler.getWidth()/2 + 134, handler.getHeight() - 49);
		
		
		craftPositions[0] = new Position(handler.getWidth()/2 - 103, handler.getHeight()/2 - 148);
		craftPositions[1] = new Position(handler.getWidth()/2 - 31, handler.getHeight()/2 - 148);
		craftPositions[2] = new Position(handler.getWidth()/2 + 41, handler.getHeight()/2 - 148); 
		craftPositions[3] = new Position(handler.getWidth()/2 - 103, handler.getHeight()/2 - 83); 
		craftPositions[4] = new Position(handler.getWidth()/2 - 31, handler.getHeight()/2 - 83); 
		craftPositions[5] = new Position(handler.getWidth()/2 + 41, handler.getHeight()/2 - 83);
		craftPositions[6] = new Position(handler.getWidth()/2 - 103, handler.getHeight()/2 - 18); 
		craftPositions[7] = new Position(handler.getWidth()/2 - 31, handler.getHeight()/2 - 18); 
		craftPositions[8] = new Position(handler.getWidth()/2 + 41, handler.getHeight()/2 - 18); 
		
		//inicializamos los rectángulos aquí porque necesitamos el handler
		
		invSlots[0] = new Rectangle(invPositions[0].getX(), invPositions[0].getY(), 43, 42);
		invSlots[1] = new Rectangle(invPositions[1].getX(), invPositions[1].getY(), 43, 42);
		invSlots[2] = new Rectangle(invPositions[2].getX(), invPositions[2].getY(), 43, 42);
		invSlots[3] = new Rectangle(invPositions[3].getX(), invPositions[3].getY(), 43, 42);
		invSlots[4] = new Rectangle(invPositions[4].getX(), invPositions[4].getY(), 43, 42);
		invSlots[5] = new Rectangle(invPositions[5].getX(), invPositions[5].getY(), 43, 42);
		
		//llenamos el array de rectángulos para comprobar la posicion del raton en los slots de la mesa de crafteo
		
		craftSlots[0] = new Rectangle(craftPositions[0].getX(), craftPositions[0].getY(), 62, 60);
		craftSlots[1] = new Rectangle(craftPositions[1].getX(), craftPositions[1].getY(), 62, 60);
		craftSlots[2] = new Rectangle(craftPositions[2].getX(), craftPositions[2].getY(), 62, 60); 
		craftSlots[3] = new Rectangle(craftPositions[3].getX(), craftPositions[3].getY(), 62, 60); 
		craftSlots[4] = new Rectangle(craftPositions[4].getX(), craftPositions[4].getY(), 62, 60); 
		craftSlots[5] = new Rectangle(craftPositions[5].getX(), craftPositions[5].getY(), 62, 60);
		craftSlots[6] = new Rectangle(craftPositions[6].getX(), craftPositions[6].getY(), 62, 60); 
		craftSlots[7] = new Rectangle(craftPositions[7].getX(), craftPositions[7].getY(), 62, 60); 
		craftSlots[8] = new Rectangle(craftPositions[8].getX(), craftPositions[8].getY(), 62, 60); 
		
	}
	
	public void tick() {
		
//		for (int i = 0; i < inventory.length; i++) {
//			if(inventory[i] != null)System.out.println(inventory[i].getName());
//		}
//		System.out.println(diffDrag);
//		System.out.println(handler.getMouseMovement().getPosition().getX() + ", " + handler.getMouseMovement().getPosition().getY());
		
		if(handler.getKeyManager().f) {
			
			if(selectedSlot > -1) {
				if(inventory[selectedSlot] != null) {	
							
					inventory[selectedSlot].unFixItemPosition();
					inventory[selectedSlot].setActive();
					inventory[selectedSlot].setPosition((GameState.getPlayerXPosition() + ThreadLocalRandom.current().nextInt(85, 120 + 1)), (GameState.getPlayerYPosition() + ThreadLocalRandom.current().nextInt(0, 120 + 1)));
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
		if(moveItemsEnabled) moveItems();
	}
	
	public void render(Graphics g) {
		
		int checkInv = checkMouseOnInv();
		int checkCraft = checkMouseOnCraft();
		
		if(checkInv >= 0 && !drawBasicInv) g.drawImage(Assets.itemPointers, invPositions[checkInv].getX() - 12, invPositions[checkInv].getY() - 12, null);
		if(checkCraft >= 0 && !drawBasicInv) g.drawImage(Assets.itemPointers, craftPositions[checkCraft].getX() - 1, craftPositions[checkCraft].getY() - 4, null);
		if(drawBasicInv) g.drawImage(Assets.inventarioPlegado, (int) (handler.getWidth()/2 - 142/2), (int) (handler.getHeight() - 61), 142, 61, null);
		
		if(inventory[0] != null) inventory[0].render(g);
		if(inventory[1] != null) inventory[1].render(g);
		
		if(renderEnabler) {
			for (int i = 2; i < inventory.length; i++) {
				if(inventory[i] != null) inventory[i].render(g);
			}
		}
		//solo se usa en el inventarioPlegado:
		if(paintPointerAt1 && drawBasicInv) g.drawImage(Assets.itemPointers, handler.getWorld().getCrafting().getInventoryX() + 128, handler.getWorld().getCrafting().getInventoryY(), 66, 64, null);
		if(paintPointerAt2 && drawBasicInv) g.drawImage(Assets.itemPointers, handler.getWorld().getCrafting().getInventoryX() + 190, handler.getWorld().getCrafting().getInventoryY(), 66, 64, null);
		
//		g.setColor(Color.RED);
//		g.fillRect(handler.getWidth()/2 - 103, handler.getHeight()/2 - 148, 62, 60);
//		g.fillRect(handler.getWidth()/2 - 31, handler.getHeight()/2 - 148, 62, 60);
//		g.fillRect(handler.getWidth()/2 + 41, handler.getHeight()/2 - 148, 62, 60);
//		g.fillRect(handler.getWidth()/2 - 103, handler.getHeight()/2 - 83, 62, 60);
//		g.fillRect(handler.getWidth()/2 - 31, handler.getHeight()/2 - 83, 62, 60);
//		g.fillRect(handler.getWidth()/2 + 41, handler.getHeight()/2 - 83, 62, 60);
//		g.fillRect(handler.getWidth()/2 - 103, handler.getHeight()/2 - 18, 62, 60);
//		g.fillRect(handler.getWidth()/2 - 31, handler.getHeight()/2 - 18, 62, 60);
//		g.fillRect(handler.getWidth()/2 + 41, handler.getHeight()/2 - 18, 62, 60);
	}
	
	public void addToInventory(Item i) {
		//siempre trabaja con el mismo array inventory, por eso sólo pasamos un item.
		
		int searchedItemIndex = searchItemIndex(i);
		int emptySlot = checkInventorySpaces();
		
		if(searchedItemIndex >= 0) {
			inventory[searchedItemIndex].increaseItemQuantity(i.getItemQuantity());
			i.setInactive();
			System.out.println(i.getName() + " has been picked up (hold E to open inventory, press F to drop)");	
		}
		else if(emptySlot >= 0) {
			
			inventory[emptySlot] = i;
			i.setPosition(invPositions[emptySlot].getX() + 22 - i.getWidth()/2, invPositions[emptySlot].getY() + 21 - i.getHeight()/2);	//centramos el item
			i.setInactive();
			i.fixItemPosition();
			System.out.println(i.getName() + " has been picked up (hold E to open inventory, press F to drop)");
				
		}else { System.out.println("The inventory is full"); }		
			
	}
	
	public boolean invSlotsMoving(int i) {
		return handler.getMouseManager().isLeftPressed() && invSlots[i].contains(handler.getMouseMovement().getPosition().getX(), handler.getMouseMovement().getPosition().getY());
	}
	
	public boolean craftSlotsMoving(int i) {
		return handler.getMouseManager().isLeftPressed() && craftSlots[i].contains(handler.getMouseMovement().getPosition().getX(), handler.getMouseMovement().getPosition().getY());
	}
	
	public void moveItems() {
		for(int i = 0; i < craftSlots.length; i++) {
			
			if(i < invSlots.length && invSlotsMoving(i)) {
				
				if(draggingItem) {
					itemToSwap = inventory[i];
					itemToSwapIndex = i;
					enableSwap = true;
					indexOfCraft = false;
				}else {
					if(inventory[i] != null) draggingItem = true;
					lastGrabbedItemIndex = i;
					lastGrabbedItem = inventory[i];
					enableDrag = true;
					craftingArray = false;
				}
				
			}else if(craftSlotsMoving(i)) {
				
				if(draggingItem) {
					itemToSwap = handler.getWorld().getCrafting().getCrafteo()[i];
					itemToSwapIndex = i;
					enableSwap = true;
					indexOfCraft = true;
				}else {
					if(handler.getWorld().getCrafting().getCrafteo()[i] != null) draggingItem = true;
					lastGrabbedItemIndex = i;
					lastGrabbedItem = handler.getWorld().getCrafting().getCrafteo()[i];
					enableDrag = true;
					indexOfCraft = true;
					craftingArray = true;
				}
			}	
		}
		
		if(lastGrabbedItem != null) {
			
			if(handler.getMouseManager().isLeftPressed() && enableDrag) {
				
					lastGrabbedItem.setPosition(handler.getMouseMovement().getPosition().getX() - lastGrabbedItem.getWidth()/2, handler.getMouseMovement().getPosition().getY() - lastGrabbedItem.getHeight()/2);
					wasRun.set(true);
				
				if(handler.getMouseManager().isMouseExit()) {			//si el ratón ha salido de la pantalla...
					if(!craftingArray){
						inventoryPositionsMouseExitWindow();
						
					}else{
						craftingPositionsMouseExitWindow();
					}
				}	
				
			}else { 
				
				if(wasRun.getAndSet(false)) {
//					
					if(!craftingArray && movingInvSlots()) {
							
						if(itemToSwap == null) {
							inventory[lastGrabbedItemIndex] = null;	
							inventory[itemToSwapIndex] = lastGrabbedItem;
							lastGrabbedItem.setPosition(invPositions[itemToSwapIndex].getX() + 22 - lastGrabbedItem.getWidth()/2, invPositions[itemToSwapIndex].getY() + 21 - lastGrabbedItem.getHeight()/2);	
							
							setToFalseEnablers();
							
						}else {
							inventory[lastGrabbedItemIndex] = itemToSwap;
							itemToSwap.setPosition(invPositions[lastGrabbedItemIndex].getX() + 22 - itemToSwap.getWidth()/2, invPositions[lastGrabbedItemIndex].getY() + 21 - itemToSwap.getHeight()/2);	
							inventory[itemToSwapIndex] = lastGrabbedItem;
							lastGrabbedItem.setPosition(invPositions[itemToSwapIndex].getX() + 22 - lastGrabbedItem.getWidth()/2, invPositions[itemToSwapIndex].getY() + 21 - lastGrabbedItem.getHeight()/2);	
							
							setToFalseEnablers();
						}
						
					}else if(!craftingArray && movingCraftSlots()) {
						//cambios del inventario a la mesa de crafteo
						if(itemToSwap == null) {
							inventory[lastGrabbedItemIndex] = null;	
							
							handler.getWorld().getCrafting().getCrafteo()[itemToSwapIndex] = lastGrabbedItem;
							lastGrabbedItem.setPosition(craftPositions[itemToSwapIndex].getX() + 31 - lastGrabbedItem.getWidth()/2, craftPositions[itemToSwapIndex].getY() + 30 - lastGrabbedItem.getHeight()/2);	
							setToFalseEnablers();
							
						}else {
							inventory[lastGrabbedItemIndex] = itemToSwap;
							itemToSwap.setPosition(invPositions[lastGrabbedItemIndex].getX() + 22 - itemToSwap.getWidth()/2, invPositions[lastGrabbedItemIndex].getY() + 21 - itemToSwap.getHeight()/2);	
							
							handler.getWorld().getCrafting().getCrafteo()[itemToSwapIndex] = lastGrabbedItem;
							lastGrabbedItem.setPosition(craftPositions[itemToSwapIndex].getX() + 31 - lastGrabbedItem.getWidth()/2, craftPositions[itemToSwapIndex].getY() + 30 - lastGrabbedItem.getHeight()/2);	
							setToFalseEnablers();
						}
						
					}else if(craftingArray && movingInvSlots()) {
						//cambios de la mesa de crafteo al inventario
						if(itemToSwap == null) {
							handler.getWorld().getCrafting().getCrafteo()[lastGrabbedItemIndex] = null;	
							
							inventory[itemToSwapIndex] = lastGrabbedItem;
							lastGrabbedItem.setPosition(invPositions[itemToSwapIndex].getX() + 22 - lastGrabbedItem.getWidth()/2, invPositions[itemToSwapIndex].getY() + 21 - lastGrabbedItem.getHeight()/2);	
							setToFalseEnablers();
							
						}else {
							handler.getWorld().getCrafting().getCrafteo()[lastGrabbedItemIndex] = itemToSwap;
							itemToSwap.setPosition(craftPositions[lastGrabbedItemIndex].getX() + 31 - itemToSwap.getWidth()/2, craftPositions[lastGrabbedItemIndex].getY() + 30 - itemToSwap.getHeight()/2);	
						
							inventory[itemToSwapIndex] = lastGrabbedItem;
							lastGrabbedItem.setPosition(invPositions[itemToSwapIndex].getX() + 22 - lastGrabbedItem.getWidth()/2, invPositions[itemToSwapIndex].getY() + 21 - lastGrabbedItem.getHeight()/2);	
							setToFalseEnablers();
						}
					
					}else if(craftingArray && movingCraftSlots()) {
						//cambios en la mesa de crafteo
						if(itemToSwap == null) {
							handler.getWorld().getCrafting().getCrafteo()[lastGrabbedItemIndex] = null;	
							
							handler.getWorld().getCrafting().getCrafteo()[itemToSwapIndex] = lastGrabbedItem;
							lastGrabbedItem.setPosition(craftPositions[itemToSwapIndex].getX() + 31 - lastGrabbedItem.getWidth()/2, craftPositions[itemToSwapIndex].getY() + 30 - lastGrabbedItem.getHeight()/2);	
							setToFalseEnablers();
							
						}else {
							handler.getWorld().getCrafting().getCrafteo()[lastGrabbedItemIndex] = itemToSwap;
							itemToSwap.setPosition(craftPositions[lastGrabbedItemIndex].getX() + 31 - itemToSwap.getWidth()/2, craftPositions[lastGrabbedItemIndex].getY() + 30 - itemToSwap.getHeight()/2);	
							
							handler.getWorld().getCrafting().getCrafteo()[itemToSwapIndex] = lastGrabbedItem;
							lastGrabbedItem.setPosition(craftPositions[itemToSwapIndex].getX() + 31 - lastGrabbedItem.getWidth()/2, craftPositions[itemToSwapIndex].getY() + 30 - lastGrabbedItem.getHeight()/2);	
							setToFalseEnablers();
						}
					
					}else if(!craftingArray){
						inventoryPositionsMouseExitWindow();
					}else{
						craftingPositionsMouseExitWindow();
					}
				}	
			}								
		}
	}
	
	public boolean movingInvSlots() {
		return !indexOfCraft && invSlots[itemToSwapIndex].contains(handler.getMouseMovement().getPosition().getX(), handler.getMouseMovement().getPosition().getY()) && enableSwap;
	}
	
	public boolean movingCraftSlots() {
		return indexOfCraft && craftSlots[itemToSwapIndex].contains(handler.getMouseMovement().getPosition().getX(), handler.getMouseMovement().getPosition().getY()) && enableSwap;
	}
	
	public void setToFalseEnablers() {
		draggingItem = false;
		enableSwap = false;
		enableDrag = false;
	}
	
	public void inventoryPositionsMouseExitWindow() {
		enableDrag = false;
		lastGrabbedItem.setPosition(invPositions[lastGrabbedItemIndex].setInvPosition(lastGrabbedItem));
		draggingItem = false;
	}
	
	public void craftingPositionsMouseExitWindow() {
		enableDrag = false;
		lastGrabbedItem.setPosition(craftPositions[lastGrabbedItemIndex].setCraftPosition(lastGrabbedItem));
		draggingItem = false;
	}
	public int checkInventorySpaces() {
		for(int i = 0; i < inventory.length; i++) if(inventory[i] == null) return i;
		return - 1;
	}
	
	public int searchItemIndex(Item i) {
		for (int c = 0; c < inventory.length; c++) if(inventory[c] != null) if(inventory[c].getId() == i.getId()) return c;
		return - 1;
	}
	
	public int checkMouseOnInv() {
		
		for(int i = 0; i < invSlots.length; i++) {
			if(invSlots[i].contains(handler.getMouseMovement().getPosition().getX(), handler.getMouseMovement().getPosition().getY())) {
				return i;
			}
		}return -1;
	}
	
	public int checkMouseOnCraft() {
		
		for(int i = 0; i < craftSlots.length; i++) {
			if(craftSlots[i].contains(handler.getMouseMovement().getPosition().getX(), handler.getMouseMovement().getPosition().getY())) {
				return i;
			}
		}return -1;
	}
	
	public static Item[] getItemArray() {
		return inventory;
	}
//	public boolean dragChanged() {
//		newDragX = handler.getMouseMovement().getPosition().getX();
//		newDragY = handler.getMouseMovement().getPosition().getY();
//		
//		if(newDragX != lastDragX || newDragY != lastDragY) {
//			lastDragX = newDragX;
//			lastDragY = newDragY;
//			return true;
//		}else {
//			return false;
//		}
//	}
	
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

	public void drawBasicInv() {
		this.drawBasicInv = true; 
	}

	public void stopDrawBasicInv() {
		this.drawBasicInv = false; 
	}	
	
	public void moveItemsEnabled() {
		this.moveItemsEnabled = true; 
	}
	
	public void moveItemsDisabled() {
		this.moveItemsEnabled = false; 
	}
}