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
		uploadPositionBinaryFile();
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
				processTablePOSICIONES();
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
	
	public void processTablePOSICIONES() {
		int cod_mundo;
		int session;
		int player_x;
		int player_y;
		for (int i=1; i<5; i++) {
			if (GameDB.existsUserPosition(i)) {
				cod_mundo = i;
				session = GameDB.getNumberSessions(i);
				player_x = GameDB.getGamePlayerXPosition(i);
				player_y = GameDB.getGamePlayerYPosition(i);
				Position prueba = new Position(cod_mundo, session, player_x, player_y);
				savePositionBinaryFile(prueba);
				System.out.println(prueba);
			}
		}
		Game.LOGGER.log(Game.LOGGER.getLevel(), "Datos de las posiciones sobreescritos al cerrar la ventana");
	}
	private class Position implements Serializable{		
		private static final long serialVersionUID = 2938644341900658039L;
		private int cod_mundo;
		private int session;
		private int player_x;
		private int player_y;
		public Position(int cod_mundo, int session,int player_x,int player_y) {
			this.cod_mundo = cod_mundo;
			this.session = session;
			this.player_x = player_x;
			this.player_y = player_y;
		}
		@Override
		public String toString() {
			return String.format("cod_mundo: %d, session: %d, player_x: %d, player_y: %d",cod_mundo,session,player_x,player_y);
		}
		
	}
	private static void savePositionBinaryFile(Position prueba) {
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("datos.bin"))) {
            os.writeObject(prueba);
            Game.LOGGER.log(Level.FINEST, "Datos serializados correctamente");
        } catch (IOException e) {
        	Game.LOGGER.log(Level.SEVERE, "Error al serializar los datos");
        }		
	}
	private static void uploadPositionBinaryFile() {
		try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("datos.bin"))) {
			for (int i=1; i<5; i++) {
				if (GameDB.existsUserPosition(i)) {
					Position p = (Position) is.readObject();
					System.out.println(p);
				}
				
			}
        } catch (IOException e) {
        	e.printStackTrace();
            System.out.println("Error. No se pudo deserializar el objeto. " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error. No se pudo encontrar la clase asociada. " + e.getMessage());
        }
	}
}
