package main.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import main.Handler;
import main.entities.creatures.BasicEnemy;
import main.entities.creatures.Player;

public class EntityManager {

	
	private Handler handler;
	private Player player;
	
	public ArrayList<Entity> entities;
	private Comparator<Entity> renderSorter = new Comparator<Entity>() {
		
		public int compare(Entity a, Entity b) {
			
			if(a.getY() + a.getHeight() < b.getY() + b.getHeight()){ //comprueba los "pies" o la parte de abajo de cada entidad para saber cual debe renderizarse antes
				return -1; //renderizamos b antes que a 
			}else{
				return 1; //renderizamos a antes que b
			}
		}
	};
	
	public EntityManager(Handler handler, Player player, BasicEnemy enemy) {		
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		addEntity(player);
		addEntity(enemy);
	}
	
	public void tick() {
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
			if(!e.isActive())
				entities.remove(e);
		}
		entities.sort(renderSorter);	//ordena la lista de entidades según el metodo compare
	}
	
	public void render(Graphics g) {
		for(Entity e : entities) e.render(g);			//no se usa este código arriba porque da problemas.
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	//getters & setters

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
	
}
