package main.inventory;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Handler;
import main.items.Item;

public class Inventory {

	protected BufferedImage inventoryImg;
	
	public Handler handler;
	
	private ArrayList<Item> inventory = new ArrayList<Item>();
	
	public Inventory(Handler handler) {
		
		this.handler = handler;
	}

	public void tick() {		
		/*
		 * Se consulta el inventario (podria realentizarse con un hilo)
		 */
		if(handler.getKeyManager().e) {
			System.out.println("INVENTARIO");			
			for(Item i : inventory) {
				System.out.println(i.getItemQuantity() + " " + i.getName());
			}				
		}
	}
	
	public void render(Graphics g) {
		
	}
	
	public void addToInventory(Item item) {
		inventory.add(item);
	}
	
	
}
