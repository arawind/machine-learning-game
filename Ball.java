package game;

import java.awt.Image;
//import java.awt.event.KeyEvent;


import javax.swing.ImageIcon;


public class Ball {
	private int curHeight, maxHeight, incrHeight;
	private boolean up, animate;
	private Image image;;
	
	public Ball(){
		ImageIcon ii = new ImageIcon(this.getClass().getResource("ball.png"));
		image = ii.getImage();
		curHeight = 0;
		incrHeight = -75;
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
		up = true;
		maxHeight += incrHeight;
	}
	
	public void stop(){
		up = true;
		animate = false;
		maxHeight = 0;
		curHeight = 0;
	}
	
	public void move(){
		if(animate){
			int accel = (int) Math.floor(Math.sqrt(Math.abs(curHeight - maxHeight)));
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