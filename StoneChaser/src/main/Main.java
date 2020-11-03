package main;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import main.entities.creatures.Player;
import main.gfx.GameCamera;
import main.input.KeyManager;
import main.input.MouseManager;
import main.states.State;

public class Main implements Runnable{

	private boolean running;
	private Thread thread;
	private long before, now, current, sec, ratio;
	private int fps;
	
	protected int width, height, c = 0;
	protected String title;
	
	private BufferStrategy bs;
	private Graphics g;
	private Window window;
	
	//Input
	private KeyManager keyManager = new KeyManager();
	private MouseManager mouseManager;
	
	private Player player;
	
	public Main(int width, int height, String title) {
		
		this.width = width;
		this.height = height;
		this.title = title;
		
		mouseManager = new MouseManager();
		player = new Player();
		
	}
	
	private void init() {
		window = new Window(width, height, title);
		if(keyManager != null && window.getFrame() != null) {
			window.getFrame().addKeyListener(keyManager);
		}else System.out.println("fuck");
	}

	public void tick() {
		keyManager.tick();
		player.tick();
	}
	
	public void render() {
		
		bs = window.getCanvas().getBufferStrategy();
		
		if(bs == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		
		//preparo la pantalla para diobujar la nueva imagen
		
		
		g.clearRect(0, 0, width, height); //si no hicieramos esto la pantalla parpadea todo el rato.
		player.render(g);
		
		//dibujo en pantalla
		
		g.drawOval(400, 300, 200, 250); //prueba
		
		//actualizo lo dibujado
		g.dispose();
		bs.show();
		
	}
	
	public void run() {
		
		init();
		
		before = System.nanoTime();		//nanoTime mide en nanosegundos
		fps = 60;						//queremos que el juego funcione a 60 frames por segundo
		sec = 1000000000;				//1 sec son 10^9 nanosegs
		ratio = sec/60;					//60 fps == la pantalla se actualiza cada 1/60 de segundo
		
		while(running) {				
			now = System.nanoTime();
			
			current = now - before;
			
			if(current >= ratio) {
				render();				//cada 1/60 de segundo hacemos que se actualicen métodos y que se rendericen gráficos en pantalla
				tick();
				before = System.nanoTime();		
				
				c++;
			}
			
			if(c >= fps) {
				System.out.println("fps counter: " + c);
				c = 0;
			}
			
		}stop();
	}
	
	public synchronized void start() {
		if(running) 
			return;
		else 
			running = true;
			thread = new Thread(this);
			thread.start();	
	}
	
	public synchronized void stop() {
		if(!running)
			return;
		else 
			running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public KeyManager getKeyManager(){
		return keyManager;
	}	
}