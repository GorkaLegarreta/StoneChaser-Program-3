package main.utilities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

public class Spotlight {

	private int sPx, sPy, sRx, sRy, width, height;
	private Graphics2D g2d;
	private float start, end, alpha;
	private int radius;
	private float[] dist;
	private Color color;
	
	
	
	public Spotlight(int sPx, int sPy, int sRx, int sRy, int width, int height, float start, float end, int radius, float alpha, Color color, Graphics g) {
		this.sPx = sPx;
		this.sPy = sPy;
		this.sRx = sRx;
		this.sRy = sRy;
		this.width = width;
		this.height = height;
		this.start = start;
		this.end = end;
		this.radius = radius;
		this.alpha = alpha;
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
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));	//modificar el valor a la dcha de SRC_OVER para cambiar la opacidad.
		
		g2d.fillRect(sRx, sRy, width, height);
					
	}	
}