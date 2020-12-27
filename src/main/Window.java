package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import javax.swing.JFrame;
import main.states.GameState;
import main.utilities.SerializablePosition;

public class Window implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -958194006111817710L;
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
		//try {SerializablePosition.readFromBinaryFile();} catch (GameDBException e2) {e2.printStackTrace();}
	}
	
	private void createWindow() {
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
		
		WindowListener windowListener = new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {			
				try {
					SerializablePosition.gameDBTableQuery();
				} catch (GameDBException e1) {
					e1.printStackTrace();
				}
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		frame.addWindowListener(windowListener);
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
