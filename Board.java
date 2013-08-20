package game;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Rectangle;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener{
	private Timer timer;
	private Timer timer2;
	private Ball ball;
	private double angle, angleInc;
	private int screenX, screenY, wallx;
	private final double TIME_ACCEL, TIME_NORM;
	private final int OFFSET_X, OFFSET_Y;
	private ArrayList<Brick> walls;
	private ArrayList<Obstacle> obstacles;
	private Obstacle obLast;
	private int score, jumpsReqd;
	public Board(int screenW, int screenH){
		TIME_NORM = 0.02;
		TIME_ACCEL = 0.08;
		OFFSET_X = 100;
		OFFSET_Y = 40+20+30; //FIXME why 30?
		
		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);
		setDoubleBuffered(true);
		
		screenX = screenW;
		screenY = screenH;		
		ball = new Ball(75, 0.22, TIME_NORM);
		angle = 0;
		wallx = 0;
		score = jumpsReqd = 0;
		timer = new Timer(5, this);
		timer.start();
		timer2 = null;
		
		angleInc = TIME_NORM;
		
		createBrickWall();
		obstacles = new ArrayList<Obstacle>();
		obLast = null;
		createObstacles();
	}

	
	private void createBrickWall() {
		int x = 0;
		walls = new ArrayList<Brick>();
		while(x<=screenX){
			Brick nWall = new Brick(x, screenY - OFFSET_Y);
			walls.add(nWall);
			x += nWall.getImgW(this);
		}		
	}

	private void startTimer2(){
		if(timer2 == null){
			timer2 = new Timer(1500, new TClass());
			timer2.start();
		}
		else{
			timer2.stop();
			timer2.start();
		}		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		animateBall(g2d);
		
		animateWall(g2d);
		
		animateObstacles(g2d);
		
		Font abc = new Font("Arial", Font.PLAIN, 18);
		FontMetrics metr = this.getFontMetrics(abc);
		String strScore = "Jumps: " + String.valueOf(score) + "  Reqd Jumps: " + String.valueOf(jumpsReqd);
		g.setColor(Color.white);
		g.setFont(abc);
		g.drawString(strScore, (screenX - metr.stringWidth(strScore))/2, abc.getSize());
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	private void createObstacles(){
		int gap, lastx, level, len = obstacles.size();
		double gapRand, levelRand;
		
		// MAX PER FRAME = 10		
		// LEVEL MAX = 5
		// MIN GAP BETWEEN OBSTACLES = 200
		
		if(len == 0){
			levelRand = Math.random();
			level = (int) Math.floor(levelRand * 10)%4 + 1;
			obstacles.add(new Obstacle(screenX, 0, level, this));
		}
		
		else{
			lastx = obstacles.get(len-1).getX();
			gapRand = Math.random();
			gap = 175 + (int) Math.floor(gapRand * 100);
			if(screenX - lastx > gap){
				levelRand = Math.random();
				level = (int) Math.floor(levelRand * 10)%4 + 1;
				obstacles.add(new Obstacle(screenX, 0, level, this ));
			}
		}
		
	}
	
	private void animateObstacles(Graphics2D g2d){
		
		int adjustX = 0;

		if(obstacles.get(0).getX() <= OFFSET_X * 0.1){
			obLast = obstacles.remove(0);
			
			if(obLast.getLevel()>2)
				jumpsReqd += 2;
			else
				jumpsReqd++;
		}
		
		if(obLast!=null){
			int tmp,x;
			tmp = checkIntersection(obLast);
			if(tmp != 0)
				adjustX = tmp;
			x = (int) (obLast.getX() - angleInc * 50) + adjustX;
			obLast.setX(x);
			obLast.setY(screenY - 10);
			obLast.drawImage(g2d, this);
		}
		
		for(Obstacle ob : obstacles){
			int tmp,x;
			tmp = checkIntersection(ob);
			if(tmp != 0)
				adjustX = tmp;
			x = (int) (ob.getX() - angleInc * 50) + adjustX;
			ob.setX(x);
			ob.setY(screenY - 10);
			ob.drawImage(g2d, this);
		}
		
		
		
		wallx -= (angleInc * 50); //50 factor to convert the time scale into integer pixels
		wallx %= screenX;
		
		
		createObstacles();
	}
	
	private int checkIntersection(Obstacle ob){

		Rectangle rball = ball.getBounds(this);
		Rectangle rob = ob.getBounds();
		if(rball.intersects(rob)){
			if(rball.x + rball.width/2 > rob.x + rob.width / 2 ){
				//ball.setxy(rob.x+rob.width, ball.getY());
				
				return -(rob.x + rob.width - rball.x);
			}
			
			else{
				angleInc = 0;
				return (rball.x + rball.width - rob.x);
			}
		}
		return 0;
	}
	
	private void animateWall(Graphics2D g2d) {
		if(wallx < screenX)
			for(Brick wall : walls){
				g2d.drawImage(wall.getImage(), (wall.getX() + wallx) % screenX, wall.getY() + ball.getImage().getHeight(this), this);
			} //Base image
		
		for(Brick wall : walls){
			g2d.drawImage(wall.getImage(), screenX + (wall.getX() + wallx) % screenX - wall.getImgW(this), wall.getY() + ball.getImage().getHeight(this), this);
		} // Looping image
		
		// wallx incrementer in animateObstacles(g2d)
				
	}

	private void animateBall(Graphics2D g2d) {
		
		ball.setxya(OFFSET_X, screenY-OFFSET_Y+ball.getH(), Math.abs(angleInc));
		AffineTransform at = AffineTransform.getTranslateInstance(ball.getX(), ball.getY());
		
		at.rotate(angle, ball.getImage().getWidth(this)*0.5, ball.getImage().getHeight(this)*0.5);
		angle += angleInc;
		if(angle >= 2*Math.PI)
			angle = 0;
		g2d.drawImage(ball.getImage(), at, this);
		
	}

	public void actionPerformed(ActionEvent e){
		repaint();
		ball.move();
	}
	
	public class TClass implements ActionListener{
		/*Timer inTimer;
		public TClass(Timer t){
			inTimer = t;
		}*/
		public void actionPerformed(ActionEvent e){
			timer2.stop();
			ball.stop();
		}
	}
	
	public class TAdapter extends KeyAdapter{
		public void keyReleased(KeyEvent e){
			int keycode = e.getKeyCode();
			if(keycode == KeyEvent.VK_RIGHT)
				angleInc = TIME_NORM;
			if(keycode == KeyEvent.VK_LEFT)
				angleInc = TIME_NORM;
		}
		public void keyPressed(KeyEvent e){
			int keycode = e.getKeyCode();
			if(keycode == KeyEvent.VK_UP){
				startTimer2();
				ball.animate();
				score++;
				//better responsiveness with this block in keyPressed than in keyReleased
			}
			if(keycode == KeyEvent.VK_RIGHT)
				angleInc = TIME_ACCEL;
			if(keycode == KeyEvent.VK_LEFT)
				angleInc = - TIME_ACCEL;
		}
	}
}
