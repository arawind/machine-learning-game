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
	private final double accelFactor, norm;
	private double accel;
	public Ball(int height, double accelFactor, double norm){
		ImageIcon ii = new ImageIcon(this.getClass().getResource("ball.png"));
		image = ii.getImage();
		x=y=0;
		curHeight = 0;
		incrHeight = -height;
		this.accelFactor = accelFactor;
		this.norm = norm;
		accel = 1;
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
			int acc = (int) Math.floor(Math.pow(Math.abs(curHeight - maxHeight), accelFactor * accel));
			if(up)
				curHeight -= acc;
			else
				curHeight += acc;
			if(curHeight <= maxHeight){
				up = false;
				curHeight += 1;
			}
			if(curHeight >= 0){
				stop();
			}
		}
	}

	public void setxya(int x, int y, double accel) {
		this.x = x;
		this.y = y;
		this.accel = accel/norm;
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