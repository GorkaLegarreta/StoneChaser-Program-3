package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import javax.swing.JFrame;

public class Window {

	private JFrame frame;
	private Canvas canvas; 
	private String title;
	private int width, height;
	
	public Window(String title, int width, int height, FileHandler fh) {
		this.title = title;
		this.width = width;
		this.height = height;
		createWindow();
		//Logger
		Game.LOGGER.log(Level.FINEST, "Objeto Window creada desde su constructor");
	}
	
	private void createWindow(){
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();	
		canvas.setBackground(Color.WHITE);
		
		
	}

	public void add(Component comp) {
		frame.add(comp);
	}
	
	public Canvas getCanvas(){
		return canvas;
		
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	
}
