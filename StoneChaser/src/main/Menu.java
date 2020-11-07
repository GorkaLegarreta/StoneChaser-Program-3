package main;

import main.gfx.Assets;
import main.gfx.GameCamera;
import main.input.*;
import main.states.*;

public class Menu implements Runnable{

	private Window window;	
	private int width, height;
	public String title = "StoneChaser Game Menu";
	
	// States
	public State menuState;
	// Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	//Handler 
	private Handler handler;
	public Menu(int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	private void init() {
		window = new Window(title, width, height);
		window.getFrame().addKeyListener(keyManager);
		Assets.init();
		
		handler = new Handler(this); //coge el objeto de esta clase
		//gameCamera = new GameCamera(handler, 0, 0);
		
		menuState = new MenuState(handler); //nos referimos a la clase Menu, a esta misma clase

		State.setState(menuState);
	}
	



	@Override
	public void run() {
		//TODO		
	}

}
