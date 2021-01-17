package main.entities.statics;

import java.awt.Graphics;

import main.Handler;
import main.gfx.Assets;
import main.items.Item;

public class Tree extends StaticEntity{

	public Tree(Handler handler, float x, float y) {
		super(handler, (int) (x), (int) (y), 64*2, 111*2);
		
		bounds.x = 35; 
		bounds.y = 150;
		bounds.width = 40;
		bounds.height = 10;
	}

	
	public void tick() {
		
		
	}

	
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), 64*2, 111*2, null);
		
	}


	@Override
	public void die() {
		Item madera = new Item("madera", Assets.trunk, (int) (18*2), (int) (20*2), 0, handler, handler.getWorld().getInventory());
		madera.createItem((int) (x), (int) (y), 2);
	}
	
}