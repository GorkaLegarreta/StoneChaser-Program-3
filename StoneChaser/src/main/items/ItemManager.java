package main.items;

import java.awt.Graphics;
import java.util.ArrayList;

import main.Handler;

public class ItemManager {

	private ArrayList<Item> toRender = new ArrayList<Item>();
	private ArrayList<Item> items = new ArrayList<Item>();
	
	private Handler handler;
	
	public ItemManager(Handler handler) {
		this.handler = handler;
		
	}	
	
	public void tick() {
		
		
		
		for(Item i : items) {
			if(i.isActive()) {
				
				i.tick();	
				
				if(!(toRender.contains(i)))
					toRender.add(i);
				
				
				
				
				
				
				
			}	
		}
	}
	
	public void render(Graphics g) {
		
		for(Item i : items) i.render(g); 
	}

	public void addItem(Item i) {
		items.add(i);
		i.active = false;
	}
	
	public void dropItem(Item i) {
		i.setActive();
	}
	
}
