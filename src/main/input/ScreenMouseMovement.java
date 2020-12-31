package main.input;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;

import javax.swing.SwingUtilities;

import main.utilities.Position;

public class ScreenMouseMovement extends MouseAdapter {

	private Point position;
	private Canvas c;
	
	public ScreenMouseMovement(Canvas c) {
		position = new Point();
		this.c = c;
		updatePosition(c);
	}
	
	public void tick() {
		if(c != null) updatePosition(c);
	}
	
	private void updatePosition(Canvas c) {
		Point initialPosition = MouseInfo.getPointerInfo().getLocation();
		
		SwingUtilities.convertPointFromScreen(initialPosition, c);
		position.setLocation(initialPosition.getX(), initialPosition.getY());
	}
	
	public Position getPosition() {
		return new Position((int) position.getX(), (int) position.getY());
	}
}