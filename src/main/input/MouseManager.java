package main.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener{

	private boolean leftPressed, rightPressed, mouseExit = false, hasDragged = false;
	private int mouseX, clickX, dragX;
	private int mouseY, clickY, dragY;
	
	public void mouseDragged(MouseEvent e) {
		dragX = e.getX();
		dragY = e.getY();
	}
	
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		leftPressed=false;
		rightPressed=false;
	}

	
	public void mouseClicked(MouseEvent e) {
		clickX = e.getX();
		clickY = e.getY();		
	}

	
	public void mouseEntered(MouseEvent e) {
		mouseExit = false;
		
	}

	
	public void mouseExited(MouseEvent e) {
		//si nuestro ratón sale de la pantalla se runnea éste código
		mouseExit = true;
		
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = true;
		else if(e.getButton() == MouseEvent.BUTTON3) //right mouse button, BUTTON2 = scroll wheel button
			rightPressed = true;
		
	}
	
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = false;
		else if(e.getButton() == MouseEvent.BUTTON3) //right mouse button, BUTTON2 = scroll wheel button
			rightPressed = false;
		
	}	
	
	//Getters
	
	public boolean isLeftPressed() {
		return leftPressed; 
	}
	
	public boolean isRightPressed() {
		return rightPressed;
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	public void setMouseX(int x) {
		this.mouseX = x;
	}
	
	public void setMouseY(int y) {
		this.mouseY = y;
	}
	
	public int getClickX() {
		return clickX;
	}
	
	public int getClickY() {
		return clickY;
	}

	public int getDragX() {
		return dragX;
	}

	public int getDragY() {
		return dragY;
	}
	
	public void setDragX(int x) {
		this.dragX = x;  
	}

	public void setDragY(int y) {
		this.dragY = y;
	}
	
	public boolean isMouseExit() {
		return mouseExit;
	}

}
