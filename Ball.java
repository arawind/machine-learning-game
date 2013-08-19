package game;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;


public class Ball {
	private int curHeight, maxHeight,x,y;
	private final int incrHeight;
	private boolean up, animate;
	private final Image image;;
	private final double accelFactor;
	public Ball(int height, double accelFactor){
		ImageIcon ii = new ImageIcon(this.getClass().getResource("ball.png"));
		image = ii.getImage();
		x=y=0;
		curHeight = 0;
		incrHeight = -height;
		this.accelFactor = accelFactor; 
		up = true;
	}
	
	public Image getImage(){
		return image;
	}
	
	public int getH(){
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

	public void setxy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Rectangle getBounds(ImageObserver arg0){
		return new Rectangle(x, y, image.getWidth(arg0),image.getHeight(arg0));
	}
	
}