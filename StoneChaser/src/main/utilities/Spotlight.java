package main.utilities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

public class Spotlight {

	private int sPx, sPy;
	private Graphics g;
	private Graphics2D g2d;
	private float start, end;
	private int radius;
	private float[] dist;
	private Color color;
	
	public Spotlight(int sPx, int sPy, float start, float end, int radius, Color color, Graphics g) {
		this.sPx = sPx;
		this.sPy = sPy;
		this.start = start;
		this.end = end;
		this.radius = radius;
		this.color = color;
		this.g2d = (Graphics2D) g;
		
		createSpotlight();
		
	}
	
	public void createSpotlight() {
		
		Point2D center = new Point2D.Float(sPx, sPy);		//cogemos la x y la y & hacemos un vector float 2d
		
		dist = new float[] {start, end};								//dist y colors tienen que tener el mismo número de variables elementos en el array
		
		Color[] colors = {color, Color.BLACK};
		
		RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
		
		g2d.setPaint(p);
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));	//modificar el valor a la dcha de SRC_OVER para cambiar la opacidad.
		
		g2d.fillRect(0, 0, 700, 400);
	
		g2d.dispose();
		
//			float[] dist = {start, 0.5f, 0,7f};
//			Color[] colors = {new Color(0, 0, 0, 0), Color.BLACK};
//			Color[] colors = {Color.RED, Color.BLACK};
//			RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
//			RadialGradientPaint p = new RadialGradientPaint(center, 10000, dist, colors); //si hacemos que el radio del centro vaya creciendo, podemos hacer que la luz vaya expandiéndose hasta llenar la pantalla.
//			RadialGradientPaint p = new RadialGradientPaint(center, 100, dist, colors); //túnel
//			g2d.setPaint(p);
//			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));	//modificar el valor a la dcha de SRC_OVER para cambiar la opacidad.
//			g2d.fillRect(0, 0, 700, 400);
//			g2d.dispose();	
			
	}
	
}
