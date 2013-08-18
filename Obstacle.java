package game;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Obstacle {
	private ArrayList<Brick> bricks;
	private int x, y;
	private final int brickH;
	public Obstacle(int x, int y, int level, ImageObserver arg0) {
		bricks = new ArrayList<Brick>();
		Brick tBrick = new Brick(x,y);
		brickH = tBrick.getImgH(arg0);
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
		this.y = y;
	}
	
	public void drawImage(Graphics2D g2d, int screenx, int screeny, ImageObserver arg0){
		for(Brick brick : bricks){
			g2d.drawImage(brick.getImage(), screenx + this.getX(), screeny - brick.getY(), arg0);
		}
	}
}
