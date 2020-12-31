package main.inventory;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

import main.Handler;
import main.gfx.Assets;
import main.items.Item;
import main.utilities.Position;

public class Inventory extends Thread{

	private long noDropNow, noDropBefore, enableDropMsg;
	private boolean renderEnabler = false, selectItem1 = false, selectItem2 = false, 
			paintPointerAt1 = false, paintPointerAt2 = false, enableDrag = false, drawBasicInv = true, enableSwap = true,
			moveItemsEnabled = false, hasDraggedWasRun = false, isGrabbed = false, stopDragging = false, diffDrag = false, draggingItem = false;
	
	private AtomicBoolean wasRun = new AtomicBoolean(false);
	
	protected BufferedImage inventoryImg;
	
	public Handler handler;
	
	protected Item selectedItem, lastGrabbedItem, itemToSwap;
	
	//creamos un array de 6 posiciones en las que pondremos items y cajas de detección de clicks
	protected Position[] positions = new Position[6];
	
	protected int selectedSlot = -1, lastGrabbedItemIndex, length, itemToSwapIndex, lastDragX, lastDragY, newDragX, newDragY;
	
	private Item[] inventory = new Item[6];
	private Rectangle[] slotRects = new Rectangle[6];
	
	public Inventory(Handler handler) {
		
		this.handler = handler;
		
		//llenamos el array de posiciones, lo hacemos aquí porque necesitamos handler.
		
		positions[0] = new Position(handler.getWidth()/2 - 53, handler.getHeight() - 49);
		positions[1] = new Position(handler.getWidth()/2 + 10, handler.getHeight() - 49);
		positions[2] = new Position(handler.getWidth()/2 - 114, handler.getHeight() - 49); 
		positions[3] = new Position(handler.getWidth()/2 - 176, handler.getHeight() - 49); 
		positions[4] = new Position(handler.getWidth()/2 + 72, handler.getHeight() - 49); 
		positions[5] = new Position(handler.getWidth()/2 + 134, handler.getHeight() - 49);
		
		//inicializamos los rectángulos aquí porque necesitamos el handler
		
		slotRects[0] = new Rectangle(positions[0].getX(), positions[0].getY(), 43, 42);
		slotRects[1] = new Rectangle(positions[1].getX(), positions[1].getY(), 43, 42);
		slotRects[2] = new Rectangle(positions[2].getX(), positions[2].getY(), 43, 42);
		slotRects[3] = new Rectangle(positions[3].getX(), positions[3].getY(), 43, 42);
		slotRects[4] = new Rectangle(positions[4].getX(), positions[4].getY(), 43, 42);
		slotRects[5] = new Rectangle(positions[5].getX(), positions[5].getY(), 43, 42);
		
		//inicializamos lastDrag para poder a empezar a comparar con newDrag
		lastDragX = handler.getMouseManager().getDragX();
		lastDragY = handler.getMouseManager().getDragY();
	}
	
	public void tick() {
		
//		for (int i = 0; i < inventory.length; i++) {
//			if(inventory[i] != null)System.out.println(inventory[i].getName());
//		}
//		System.out.println(diffDrag);
//		System.out.println(handler.getMouseMovement().getPosition().getX() + ", " + handler.getMouseMovement().getPosition().getY());
		
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
		if(moveItemsEnabled) moveItems();
	}
	
	public void render(Graphics g) {
		
		int check = checkMouseOnInv();
		
		if(check >= 0 && !drawBasicInv) g.drawImage(Assets.itemPointers, positions[check].getX() - 12, positions[check].getY() - 12, null);
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
	}
	
	public void addToInventory(Item i) {
		//siempre trabaja con el mismo array inventory, por eso sólo pasamos un item.
		
		int searchedItemIndex = searchItem(i);
		int emptySlot = checkInventorySpaces();
		
		if(searchedItemIndex >= 0) {
			inventory[searchedItemIndex].increaseItemQuantity(i.getItemQuantity());
			i.setInactive();
			System.out.println(i.getName() + " has been picked up (hold E to open inventory, press F to drop)");	
		}
		else if(emptySlot >= 0) {
			
			inventory[emptySlot] = i;
			i.setPosition(positions[emptySlot].getX() + 22 - i.getWidth()/2, positions[emptySlot].getY() + 21 - i.getHeight()/2);	//centramos el item
			i.setInactive();
			i.fixItemPosition();
			System.out.println(i.getName() + " has been picked up (hold E to open inventory, press F to drop)");
				
		}else { System.out.println("The inventory is full"); }		
			
	}
	
	public void moveItems() {
		for(int i = 0; i < slotRects.length; i++) {
			if(handler.getMouseManager().isLeftPressed() && slotRects[i].contains(handler.getMouseMovement().getPosition().getX(), handler.getMouseMovement().getPosition().getY())) {
				
				if(draggingItem) {
					itemToSwap = inventory[i];
					itemToSwapIndex = i;
					enableSwap = true;
				}else {
					if(inventory[i] != null) draggingItem = true;
					lastGrabbedItemIndex = i;
					lastGrabbedItem = inventory[i];
					enableDrag = true;
					isGrabbed = true;
				}
			}
		}
		
		if(lastGrabbedItem != null) {
			
			if(handler.getMouseManager().isLeftPressed() && enableDrag) {
				
					lastGrabbedItem.setPosition(handler.getMouseMovement().getPosition().getX() - lastGrabbedItem.getWidth()/2, handler.getMouseMovement().getPosition().getY() - lastGrabbedItem.getHeight()/2);
					wasRun.set(true);
				
				if(handler.getMouseManager().isMouseExit()) {			//si el ratón ha salido de la pantalla...
					lastGrabbedItem.setPosition(positions[lastGrabbedItemIndex].setItemPosition(lastGrabbedItem));
					enableDrag = false;
					isGrabbed = false;
				}	
				
			}else { 
				
				if(wasRun.getAndSet(false)) {
					if(slotRects[itemToSwapIndex].contains(handler.getMouseMovement().getPosition().getX(), handler.getMouseMovement().getPosition().getY()) && enableSwap) {
						
						if(itemToSwap == null) {
							inventory[lastGrabbedItemIndex] = null;	
							
							inventory[itemToSwapIndex] = lastGrabbedItem;
							lastGrabbedItem.setPosition(positions[itemToSwapIndex].getX() + 22 - lastGrabbedItem.getWidth()/2, positions[itemToSwapIndex].getY() + 21 - lastGrabbedItem.getHeight()/2);	
							
							draggingItem = false;
							enableSwap = false;
							enableDrag = false;
							
						}else {
							inventory[lastGrabbedItemIndex] = itemToSwap;
							itemToSwap.setPosition(positions[lastGrabbedItemIndex].getX() + 22 - itemToSwap.getWidth()/2, positions[lastGrabbedItemIndex].getY() + 21 - itemToSwap.getHeight()/2);	
							
							inventory[itemToSwapIndex] = lastGrabbedItem;
							lastGrabbedItem.setPosition(positions[itemToSwapIndex].getX() + 22 - lastGrabbedItem.getWidth()/2, positions[itemToSwapIndex].getY() + 21 - lastGrabbedItem.getHeight()/2);	
							
							draggingItem = false;
							enableSwap = false;
							enableDrag = false;
						}
					}else{
						enableDrag = false;
						lastGrabbedItem.setPosition(positions[lastGrabbedItemIndex].setItemPosition(lastGrabbedItem));
						draggingItem = false;
					}
				}	
			}								
		}
	}
	
	public int checkInventorySpaces() {
		for(int i = 0; i < inventory.length; i++) if(inventory[i] == null) return i;
		return - 1;
	}
	
	public int searchItem(Item i) {
		for (int c = 0; c < inventory.length; c++) if(inventory[c] != null) if(inventory[c].getId() == i.getId()) return c;
		return - 1;
	}
	
	public int checkMouseOnInv() {
		
		for(int i = 0; i < slotRects.length; i++) {
			if(slotRects[i].contains(handler.getMouseMovement().getPosition().getX(), handler.getMouseMovement().getPosition().getY())) {
				return i;
			}else if(slotRects[i].contains(handler.getMouseMovement().getPosition().getX(), handler.getMouseMovement().getPosition().getY())/* && dragChanged()*/) {
				return i;
			}
		}return -1;
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