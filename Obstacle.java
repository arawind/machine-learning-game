package game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Obstacle {
	private ArrayList<Brick> bricks;
	private int x, y;
	private final int brickH, brickW, level;
	public Obstacle(int x, int y, int level, ImageObserver arg0) {
		bricks = new ArrayList<Brick>();
		Brick tBrick = new Brick(x,y);
		brickH = tBrick.getImgH(arg0);
		brickW = tBrick.getImgW(arg0);
		this.level = level;
		this.x = x;
		this.y = y;		
		for(int i=0; i<level; i++){
			Brick nBrick = new Brick(x, y + (i-1)*brickH); // -1 for the wall bricks offset
			bricks.add(nBrick);
		}
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y - brickH * (level+1);
	}
	
	public int getLevel(){
		return level;
	}
	
	public void drawImage(Graphics2D g2d, ImageObserver arg0){
		for(Brick brick : bricks){
			g2d.drawImage(brick.getImage(), this.getX(), y + brick.getY(), arg0);
		}
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y-brickH, brickW, brickH * (level));
	}
}
