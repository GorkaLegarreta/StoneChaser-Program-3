package main.items;

import java.awt.Graphics;
import java.util.ArrayList;
import main.Handler;
import main.crafting.Crafting;
import main.gfx.Assets;
import main.inventory.Inventory;

public class ItemManager {

	private ArrayList<Item> toRender = new ArrayList<Item>();
	
	protected Handler handler;	
	
	public static Item madera, piedra, hoja, hierro, palo, casco, piedraFuego, piedraAgua, piedraPlanta, cuero, cuerda, espadaHierro;
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
		
		madera = new Item("madera", Assets.trunk, (int) (18*2), (int) (20*2), 0, handler, inventory);
		piedra = new Item("piedra", Assets.stone, (int) (17*2), (int) (10*2), 1, handler, inventory);
		cuero = new Item("cuero", Assets.cuero, (int) (25*1.75), (int) (23*1.75), 2, handler, inventory);
		cuerda = new Item("cuerda", Assets.cuerda, (int) (23*2), (int) (23*2), 3, handler, inventory);
		hierro = new Item("hierro", Assets.hierro, (int) (17*2), (int) (20*2), 4, handler, inventory);
		palo = new Item("palo", Assets.palo, (int) (18*2), (int) (19*2), 5, handler, inventory);
		casco = new Item("casco", Assets.casco, (int) (27*1.75), (int) (28*1.75), 6, handler, inventory);
		//palo = new Item("pechera", Assets.pechera, (int) (18*2), (int) (19*2), 3, handler, inventory);
		//palo = new Item("pantalones", Assets.pantalones, (int) (18*2), (int) (19*2), 3, handler, inventory);
		//palo = new Item("botas", Assets.botas, (int) (18*2), (int) (19*2), 3, handler, inventory);
		
//		piedraFuego = new Item("piedraFuego", Assets.trunk, 18, 20, 1, 2, false, handler, inventory);
//		piedraAgua = new Item("piedraAgua", Assets.trunk, 18, 20, 1, 2, false, handler, inventory);
//		piedraPlanta = new Item("piedraPlanta", Assets.trunk, 18, 20, 1, 2, false, handler, inventory);
		espadaHierro = new Item("espada de Hierro", Assets.sword, (int) (20*2), (int) (20*2), 7, handler, inventory);
		
		//offset ya implementado en createItem, poner posicion directamente. Si no restasemos el offset el item se pondría
		//con una posición en respecto a lo que se ve en pantalla, no en respecto al mapa ->
		
		items.add(madera.createItem(300, 200, 2));
		items.add(piedra.createItem(250, 150, 2));
		items.add(espadaHierro.createItem(280, 190, 1));
		items.add(cuero.createItem(220, 160, 3));
		items.add(piedra.createItem(240, 90, 1));
		items.add(cuerda.createItem(270, 110, 1));
		items.add(hierro.createItem(320, 170, 1));
		items.add(palo.createItem(340, 220, 1));
		items.add(casco.createItem(230, 75, 1));
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
		
		for(Item i : toRender) i.render(g);
		crafting.render(g);
		inventory.render(g);
		
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public Crafting getCrafting() {
		return crafting;
	}
	
}
