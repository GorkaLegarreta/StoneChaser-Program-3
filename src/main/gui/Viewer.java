package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.JobAttributes.DefaultSelectionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.Game;
import main.GameDB;
import main.GameDBException;
import main.states.MenuState;

public class Viewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1092180823219722606L;
	private UserNameListModel userNameListModel;
	private JList<String> userNameJList;
	private JLabel sessions, x, y;
	public Viewer() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setTitle("Game Session Viewer");
		setSize(700, 400);
		setLocationRelativeTo(null);
		
		// Menu
		configMenuBar();
		
		// Central Panel
		configFrameDivider();
		
		// Cargar JList
		loadJList();
		
		addWindowListener(createWindowListener());
	}
	
	public void configMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fichero = new JMenu("Fichero");
		JMenuItem salir = new JMenuItem("Salir");
		fichero.add(salir);
		menuBar.add(fichero);
		
		salir.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();		
			}
		});
	}
	
	private void addComponentToLayout(JPanel form, Component component, int row, int column) {   
		  GridBagConstraints constraints = new GridBagConstraints();   
		  constraints.fill = GridBagConstraints. BOTH ;    
		  constraints.gridx = column;   
		  constraints.gridy = row;   
		  constraints.gridwidth = 1;    
		  constraints.weightx = column == 0 ? 0.2 : 0.8;   
		  constraints.weighty = 1;    
		  form.add(component, constraints); 
	}
	
	private void addComponentToRightLayout(JPanel form, Component component, int row, int column) {   
		  GridBagConstraints constraints = new GridBagConstraints();   
		  constraints.fill = GridBagConstraints. BOTH ;    
		  constraints.gridx = column;   
		  constraints.gridy = row;   
		  constraints.gridwidth = 1;    
		  constraints.weightx = 1;   
		  constraints.weighty = row == 0 ? 0.2 : 0.8;    
		  form.add(component, constraints); 
	}
	
	public void configFrameDivider() {
		JPanel panel = new JPanel(new GridBagLayout());
		Border border = BorderFactory.createTitledBorder("Current UserNames:");		
		JPanel left = new JPanel();
		left.setBorder(border);
		userNameListModel = new UserNameListModel(); 
		userNameJList = new JList<String>(userNameListModel);
		userNameJList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
//		JScrollBar scroll = new JScrollBar();
//		scroll.add(userNameJList);
		left.add(userNameJList);
		JPanel right = new JPanel(new GridBagLayout());
		border = BorderFactory.createTitledBorder("Information about User:");	
		JPanel up = new JPanel(new GridLayout(2,2));
		sessions = new JLabel("Sesiones: ");
		up.add(sessions);		
		up.add(new JPanel());
		x = new JLabel("Player_X: ");
		up.add(x);
		y = new JLabel("Player_Y: ");
		up.add(y);
		up.setBorder(border);
		
		border = BorderFactory.createTitledBorder("Inventory of the User:");
		JPanel down = new JPanel();
		down.setBorder(border);
		
		addComponentToLayout(panel,left,0,0);
		addComponentToLayout(panel,right,0,1);
		addComponentToRightLayout(right, up, 0, 0);
		addComponentToRightLayout(right, down, 1, 0);
		add(panel);
		
		userNameJList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				loadUserInformation();
				
			}
		});
	}
	
	public void loadJList() {
		for (int i=0; i<4; i++) {
			userNameListModel.addElement(GameDB.getGameUserName(i+1));
		}		
	}
	
	public void loadUserInformation() {
		int user_code = userNameJList.getSelectedIndex() + 1;
		try {
			if (GameDB.getNumberSessions(user_code) != -1) {
				sessions.setText("Sesiones: "+Integer.toString(GameDB.getNumberSessions(user_code)));
				x.setText("Player_X: "+Integer.toString(GameDB.getGamePlayerXPosition(user_code)));
				y.setText("Player_Y: "+Integer.toString(GameDB.getGamePlayerYPosition(user_code)));
			} else {
				sessions.setText("Sesiones:");
				x.setText("Player_X:");
				y.setText("Player_Y:");
			}
		} catch (GameDBException e) {
			e.printStackTrace();
		}
	}
	
	public void loadUserInventoryInformation() {
		int user_code = userNameJList.getSelectedIndex() + 1;
		
	}
	public void setTextUpperPanel() {
		
	}
	public WindowListener createWindowListener() {
		WindowListener wl = new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
							
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
				exit();
				
			}			
			@Override
			public void windowClosed(WindowEvent e) {
//				Game.getWindow().setVisibility();
//				setVisible(false);
			}			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		return wl;
	}
	
	public void exit() {
		dispose();
		Game.getWindow().setVisibility();
	}
}
