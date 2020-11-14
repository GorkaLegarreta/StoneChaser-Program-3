
package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

public class Window {

	private JFrame frame;
	private Canvas canvas;
	private Logger LOGGER = Logger.getLogger(Window.class.getName()); 
	private String title;
	private int width, height;
	
	public Window(String title, int width, int height, FileHandler fh) {
		this.title = title;
		this.width = width;
		this.height = height;
		createWindow();
		LOGGER.addHandler(fh);
		
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
		//Logger
		LOGGER.log(Level.FINE, "Ventana de Stone Chaser inicializada");
		LOGGER.info("Ventana de Stone Chaser inicializada");
	}
	
	public Canvas getCanvas(){
		return canvas;
		
	}
	
	public JFrame getFrame(){
		return frame;
	}
}
