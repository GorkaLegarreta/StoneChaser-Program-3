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
	protected Item[] items = new Item[8];
	
	protected Inventory inventory;
	protected Crafting crafting;
	
	public ItemManager(Handler handler) {
		this.handler = handler;
		
		inventory = new Inventory(handler);
		crafting = new Crafting(handler);
		
		initItems();
	}	
	
	public void initItems() {
		
		madera = new Item("Wood", Assets.trunk, (int) (300 - handler.getGameCamera().getxOffset()), (int) (200 - handler.getGameCamera().getyOffset()), 18*2, 20*2, 1, 0, true, handler, inventory);
		piedra = new Item("piedra", Assets.stone, (int) (50 - handler.getGameCamera().getxOffset()), (int) (20 - handler.getGameCamera().getyOffset()), 17*2, 10*2, 1, 1, true, handler, inventory);
		cuero = new Item("cuero", Assets.trunk, (int) (300 - handler.getGameCamera().getxOffset()), (int) (200 - handler.getGameCamera().getyOffset()), 18, 20, 1, 2, false, handler, inventory);
		cuerda = new Item("cuerda", Assets.trunk, 300, 200, 18, 20, 1, 2, false, handler, inventory);
		piedraFuego = new Item("piedraFuego", Assets.trunk, 300, 200, 18, 20, 1, 2, false, handler, inventory);
		piedraAgua = new Item("piedraAgua", Assets.trunk, 300, 200, 18, 20, 1, 2, false, handler, inventory);
		piedraPlanta = new Item("piedraPlanta", Assets.trunk, 300, 200, 18, 20, 1, 2, false, handler, inventory);
		espadaHierro = new Item("espadaHierro", Assets.trunk, 300, 200, 18, 20, 1, 2, false, handler, inventory);
		
		items[0] = madera;
		items[1] = piedra;
		items[2] = cuero;
		items[3] = cuerda;
		items[4] = piedraFuego;
		items[5] = piedraAgua;
		items[6] = piedraPlanta;
		items[7] = espadaHierro;
		
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
