package main.crafting;

import java.awt.Graphics;
import java.util.Scanner;

import main.Handler;
import main.gfx.Assets;
import main.items.Item;
import main.items.ItemManager;

public class Crafting {

	protected Handler handler;
	
	private Item resultadoCrafteo;
	
	private Item[] crafteo;
	
	private Scanner scIn = new Scanner(System.in);
	
	private int item, posicion, craftingTableWidth = 200, craftingTableHeight = 186;
	
	private boolean c = false, callCraft = false;

	private float ratio = 1500000000, update;
	private long now, before = System.nanoTime();

	public Crafting(Handler handler) {
		this.handler = handler;
		init();
	}
	
	public void init() {
		crafteo = new Item[9];
	}
	
	public void craft() {
		
		if(crafteo[0] == null && crafteo[1] == ItemManager.hierro && crafteo[2] == null && 
				crafteo[3] == null && crafteo[4] == ItemManager.hierro && crafteo[5] == null && 
				crafteo[6] == null && crafteo[7] == ItemManager.cuero && crafteo[8] == null) {
			
			resultadoCrafteo = ItemManager.espadaHierro;
			
		}
		
	}
	
	public void render(Graphics g) {
		if(c == true) {
			g.drawImage(Assets.inventory, (handler.getWidth()/2) - (craftingTableWidth), (handler.getHeight()/2) - (craftingTableHeight), craftingTableWidth*2, craftingTableHeight*2, null);
			
			
		}
	}
	
	public void tick() {
		
		if(handler.getKeyManager().r && c == false) {		//update solo funciona cuando se activa el inventario, entonces no llegará a ser un valor tan alto que crashee el programa.
															
			now = System.nanoTime();			
			update += (now - before)/ratio;									
			
			if(update >= 1) {
				before = System.nanoTime();				
				c = true;
				update = 0;
				handler.spotlightEnabler();
				callCraft = true;
				
			}			
			
		}else if(handler.getKeyManager().r && c == true) {
			
			now = System.nanoTime();			
			update += (now - before)/ratio;									
			
			if(update >= 1) {
				before = System.nanoTime();				
				c = false;
				update = 0;
				handler.spotlightDisabler();
				callCraft = false;
			}
			
		}
		
		System.out.println(update);
		
		if(callCraft) {
			craft();
			
		}
		
		
		
	}
	
	
	
}
