package main.entities.statics;

import main.Handler;
import main.entities.Entity;

//Entities like trees or rocks are static entities, if they moved they'd be creatures

public abstract class StaticEntity extends Entity{

	public StaticEntity(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
	}
	
}
