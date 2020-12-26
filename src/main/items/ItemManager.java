package main.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Handler;
import main.crafting.Crafting;
import main.gfx.Assets;
import main.inventory.Inventory;

public class ItemManager {

	private ArrayList<Item> toRender = new ArrayList<Item>();
	
	protected Handler handler;
	
	//colores para prueba de los items
	
	protected Color color;
	
	Color cPiedra = Color.GREEN;
	Color cCuero = Color.magenta;
	Color cEspadaHierro = Color.BLUE;
	Color cCuerda = Color.WHITE;		//cualquier color ya que para esta prueba no los vamos a usar
	Color cPiedraFuego = Color.WHITE;	//cualquier color ya que para esta prueba no los vamos a usar
	Color cPiedraAgua = Color.WHITE;	//cualquier color ya que para esta prueba no los vamos a usar
	Color cPiedraPlanta = Color.WHITE;	//cualquier color ya que para esta prueba no los vamos a usar
	
	
	public static Item i, madera, piedra, hoja, hierro, piedraFuego, piedraAgua, piedraPlanta, cuero, cuerda, espadaHierro;
	protected ArrayList<Item> items = new ArrayList<>();
	
	protected Inventory inventory;
	protected Crafting crafting;
	
	public ItemManager(Handler handler) {
		this.handler = handler;
		
		inventory = new Inventory(handler);
		crafting = new Crafting(handler);
		
		initItems();
	}	
	
	public void initItems() {
		
		madera = new Item("Wood", Assets.trunk, 18*2, 20*2, 0, handler, inventory);
		piedra = new Item("piedra", Assets.stone, 17*2, 10*2, 1, handler, inventory);
//		cuero = new Item("cuero", Assets.trunk, 18*2, 20*2, 1, 2, false, handler, inventory);
//		cuerda = new Item("cuerda", Assets.trunk, 18, 20, 1, 2, false, handler, inventory);
//		piedraFuego = new Item("piedraFuego", Assets.trunk, 18, 20, 1, 2, false, handler, inventory);
//		piedraAgua = new Item("piedraAgua", Assets.trunk, 18, 20, 1, 2, false, handler, inventory);
//		piedraPlanta = new Item("piedraPlanta", Assets.trunk, 18, 20, 1, 2, false, handler, inventory);
//		espadaHierro = new Item("espadaHierro", Assets.trunk, 18, 20, 1, 2, false, handler, inventory);
		
		//offset ya implementado en createItem, poner posicion directamente. Si no restasemos el offset el item se pondría
		//con una posición en respecto a lo que se ve en pantalla, no en respecto al mapa.
		
		items.add(madera.createItem(300, 200, 26));
		items.add(madera.createItem(400, 250, 13));
		items.add(madera.createItem(50, 120, 4));
		items.add(piedra.createItem(100, 150, 2));
		
	}
	
	public void tick() {
		
		inventory.tick();
		crafting.tick();
		
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
		crafting.render(g);
		
	}
	
}
