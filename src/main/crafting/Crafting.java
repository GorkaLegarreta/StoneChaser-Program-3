package main.crafting;

import java.awt.Graphics;
import java.util.Scanner;

import main.Handler;
import main.items.Item;
import main.items.ItemManager;

public class Crafting {

	protected Handler handler;
	
	private Item resultadoCrafteo;
	
	private Item[] crafteo;
	
	private Scanner scIn = new Scanner(System.in);
	
	private int item, posicion;
	
	private int c = 0;

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
		
	}
	
	public void tick() {
		
		if(handler.getKeyManager().r && c < 1) {
			c++;
			
			System.out.println("que item quieres poner? ");
			item = scIn.nextInt();
			System.out.println("En que hueco del item lo quieres poner? ");
			posicion = scIn.nextInt();
			
			
			
			
			
			
		}
		
		craft();
		
	}
	
	
	
}
