package game;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Obstacle {
	private ArrayList<Brick> bricks;
	private int x, y;
	public Obstacle(int x, int y, int level, ImageObserver arg0) {
		int brickH;
		bricks = new ArrayList<Brick>();
		this.x = x;
		this.y = y;
		Brick tBrick = new Brick(x,y);
		brickH = tBrick.getImgH(arg0);
		for(int i=0; i<level; i++){
			Brick nBrick = new Brick(x, y - i*brickH);
			bricks.add(nBrick);
		}
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void drawImage(Graphics2D g2d, int screenx, int screeny, ImageObserver arg0){
		for(Brick brick : bricks){
			g2d.drawImage(brick.getImage(), screenx + brick.getX(), screeny - brick.getY(), arg0);
		}
	}
}
