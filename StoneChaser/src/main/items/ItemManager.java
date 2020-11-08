package main.items;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Handler;
import main.inventory.Inventory;

public class ItemManager {

	private ArrayList<Item> toRender = new ArrayList<Item>();
	
	protected Handler handler;
	
	//colores para prueba de los items
	
	protected Color color;
	
	Color cMadera = Color.ORANGE;
	Color cHierro = Color.GREEN;
	Color cCuero = Color.magenta;
	Color cCuerda = Color.WHITE;	//cualquier color ya que para esta prueba no los vamos a usar
	Color cPiedraFuego = Color.WHITE;	//cualquier color ya que para esta prueba no los vamos a usar
	Color cPiedraAgua = Color.WHITE;	//cualquier color ya que para esta prueba no los vamos a usar
	Color cPiedraPlanta = Color.WHITE;	//cualquier color ya que para esta prueba no los vamos a usar
	
	
	protected Item i, madera, roca, hoja, hierro, piedraFuego, piedraAgua, piedraPlanta;
	protected Item[] items = new Item[7];
	
	protected Inventory inventory;
	
	public ItemManager(Handler handler) {
		this.handler = handler;
		
		inventory = new Inventory(handler);
		
		initItems();
	}	
	
	public void initItems() {
		
		items[0] = new Item("madera", cMadera, 0, 0, 1, true, 0, handler, inventory);
		items[1] = new Item("hierro", cHierro, 50, 20, 1, true, 1, handler, inventory);
		items[2] = new Item("cuero", cCuero, 300, 200, 1, true, 2, handler, inventory);
		items[3] = new Item("cuerda", cCuerda, 0, 0, 1, false, 3, handler, inventory);
		items[4] = new Item("piedraFuego", cPiedraFuego, 0, 0, 1, false, 4, handler, inventory);
		items[5] = new Item("piedraAgua", cPiedraAgua, 0, 0, 1, false, 5, handler, inventory);
		items[6] = new Item("piedraPlanta", cPiedraPlanta, 0, 0, 1, false, 6, handler, inventory);
		
		
	}
	
	public void tick() {
		
		inventory.tick();
		
		for(Item i : items) {
			if(i.isActive()) {
				
				i.tick();
				
				if(!(toRender.contains(i)))
					toRender.add(i);			
				
			}else {
				if(toRender.contains(i))
					toRender.remove(i);
			}
		}
	}
	
	public void render(Graphics g) {
		
		inventory.render(g);
		for(Item i : toRender) i.render(g); 
	}
	
}
