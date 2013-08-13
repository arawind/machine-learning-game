package game;

import java.awt.Image;
//import java.awt.event.KeyEvent;


import javax.swing.ImageIcon;


public class Ball {
	private int curHeight, maxHeight;
	private boolean up, animate;
	private Image image;;
	
	public Ball(){
		ImageIcon ii = new ImageIcon(this.getClass().getResource("ball.png"));
		image = ii.getImage();
		curHeight = 0;
		maxHeight = -100;
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
	}
	
	public void stop(){
		animate = false;
	}
	
	public void move(){
		if(animate){

			int accel = (int) Math.floor(Math.sqrt(Math.abs(curHeight - maxHeight)));
			if(up)
				curHeight -= accel;
			else
				curHeight += accel;
			if(curHeight <= maxHeight)
				up = false;
			if(curHeight >= 0)
				up = true;
		}
	}
}
