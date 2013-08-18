package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Board extends JPanel implements ActionListener{
	private Timer timer;
	private Timer timer2;
	private Ball ball;
	private double angle, angleInc;
	private int screenX, screenY, wallx;
	private final double TIME_ACCEL, TIME_NORM;
	private final int OFFSET_X, OFFSET_Y;
	private ArrayList<Brick> walls;
	public Board(int screenW, int screenH){
		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);
		setDoubleBuffered(true);
		screenX = screenW;
		screenY = screenH;		
		ball = new Ball(75, 0.22);
		angle = 0;
		wallx = 0;
		timer = new Timer(5, this);
		timer.start();
		timer2 = null;
		TIME_NORM = 0.02;
		TIME_ACCEL = 0.08;
		angleInc = TIME_NORM;
		OFFSET_X = 100;
		OFFSET_Y = 40+20+30; //FIXME why 30?
		
		int x = 0;
		walls = new ArrayList<Brick>();
		while(x<=screenW){
			Brick nWall = new Brick(x, screenY - OFFSET_Y);
			walls.add(nWall);
			
			x += nWall.getImgW(this);
		}
	}
	
	public void startTimer2(){
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
		
		//Obstacle ob = new Obstacle(300, 0, 2, this);
		//Obstacle ob2 = new Obstacle(780, 0, 3, this);
		
		//ob.drawImage(g2d, wallx, screenY - 90, this);
		//ob2.drawImage(g2d, wallx, screenY - 110, this);
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	
	
	private void animateWall(Graphics2D g2d) {
		if(wallx < screenX)
			for(Brick wall : walls){
				g2d.drawImage(wall.getImage(), (wall.getX() + wallx) % screenX, wall.getY() + ball.getImage().getHeight(this), this);
			} //Base image
		
		for(Brick wall : walls){
			g2d.drawImage(wall.getImage(), screenX + (wall.getX() + wallx) % screenX - wall.getImgW(this), wall.getY() + ball.getImage().getHeight(this), this);
		} // Looping image
		
		//System.out.println(walls.get(0).getY() + ball.getImage().getHeight(this) + walls.get(0).getImgH(this));
		
		wallx -= (angleInc * 50)%screenX; //50 factor to convert the time scale into integer pixels		
	}

	private void animateBall(Graphics2D g2d) {

		AffineTransform at = AffineTransform.getTranslateInstance(OFFSET_X, screenY-OFFSET_Y+ball.getY());
		
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
				//better responsiveness with this block in keyPressed than in keyReleased
			}
			if(keycode == KeyEvent.VK_RIGHT)
				angleInc = TIME_ACCEL;
			if(keycode == KeyEvent.VK_LEFT)
				angleInc = - TIME_ACCEL;
		}
	}
}
