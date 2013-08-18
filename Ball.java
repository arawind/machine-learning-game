package game;

import java.awt.Image;
//import java.awt.event.KeyEvent;


import javax.swing.ImageIcon;


public class Ball {
	private int curHeight, maxHeight;
	private final int incrHeight;
	private boolean up, animate;
	private final Image image;;
	private final double accelFactor;
	public Ball(int height, double accelFactor){
		ImageIcon ii = new ImageIcon(this.getClass().getResource("ball.png"));
		image = ii.getImage();
		curHeight = 0;
		incrHeight = -height;
		this.accelFactor = accelFactor; 
		up = true;
	}
	
	public Image getImage(){
		return image;
	}
	
	public int getY(){
		return curHeight;
	}
	
	public void animate(){
		animate = true;
		if(up == true)
			maxHeight += incrHeight; //You can accelerate the ball only before it falls down 
		// ==> You can't lift it while it's falling down
	}
	
	public void stop(){
		up = true;
		animate = false;
		maxHeight = 0;
		curHeight = 0;
	}
	
	public void move(){
		if(animate){
			int accel = (int) Math.floor(Math.pow(Math.abs(curHeight - maxHeight), accelFactor));
			if(up)
				curHeight -= accel;
			else
				curHeight += accel;
			if(curHeight <= maxHeight){
				up = false;
				curHeight += 1;
			}
			if(curHeight >= 0){
				stop();
			}
		}
	}
}