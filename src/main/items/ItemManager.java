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
	Color cEspadaHierro = Color.BLUE;
	Color cCuerda = Color.WHITE;		//cualquier color ya que para esta prueba no los vamos a usar
	Color cPiedraFuego = Color.WHITE;	//cualquier color ya que para esta prueba no los vamos a usar
	Color cPiedraAgua = Color.WHITE;	//cualquier color ya que para esta prueba no los vamos a usar
	Color cPiedraPlanta = Color.WHITE;	//cualquier color ya que para esta prueba no los vamos a usar
	
	
	public static Item i, madera, roca, hoja, hierro, piedraFuego, piedraAgua, piedraPlanta, cuero, cuerda, espadaHierro;
	protected Item[] items = new Item[8];
	
	protected Inventory inventory;
	
	public ItemManager(Handler handler) {
		this.handler = handler;
		
		inventory = new Inventory(handler);
		
		initItems();
	}	
	
	public void initItems() {
		
		madera = new Item("madera", cMadera, 0, 0, 1, true, 0, handler, inventory);
		hierro = new Item("hierro", cHierro, 50, 20, 1, true, 1, handler, inventory);
		cuero = new Item("cuero", cCuero, 300, 200, 1, true, 2, handler, inventory);
		cuerda = new Item("cuerda", cCuerda, 0, 0, 1, false, 3, handler, inventory);
		piedraFuego = new Item("piedraFuego", cPiedraFuego, 0, 0, 1, false, 4, handler, inventory);
		piedraAgua = new Item("piedraAgua", cPiedraAgua, 0, 0, 1, false, 5, handler, inventory);
		piedraPlanta = new Item("piedraPlanta", cPiedraPlanta, 0, 0, 1, false, 6, handler, inventory);
		espadaHierro = new Item("espadaHierro", cEspadaHierro, 0, 0, 1, false, 6, handler, inventory);
		
		items[0] = madera;
		items[1] = hierro;
		items[2] = cuero;
		items[3] = cuerda;
		items[4] = piedraFuego;
		items[5] = piedraAgua;
		items[6] = piedraPlanta;
		items[7] = espadaHierro;
		
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
