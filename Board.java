package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
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
	private int screenX, screenY, xinc, wallx;
	private final double ANGLE_INC_ACCEL, ANGLE_INC;
	private ArrayList<Wall> walls;
	public Board(int screenW, int screenH){
		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);
		setDoubleBuffered(true);
		screenX = screenW;
		screenY = screenH;		
		ball = new Ball();
		angle = 0;
		angleInc = 0.02;
		xinc = 5;
		wallx = 0;
		timer = new Timer(5, this);
		timer.start();
		timer2 = null;
		ANGLE_INC_ACCEL = 0.08;
		ANGLE_INC = 0.02;
		
		int x = 0;
		walls = new ArrayList<Wall>();
		while(x<=screenW){
			Wall nWall = new Wall(x,screenY - 100);
			walls.add(nWall);
			
			x += nWall.getImgH();
		}
	}
	
	public class Wall {
		private int imgH, imgW, x, y;
		private Image image;
		
		public Wall(int x, int y){
			ImageIcon ii = new ImageIcon(this.getClass().getResource("wall.png"));
			image = ii.getImage();
			imgW = imgH = 20;
			this.x = x;
			this.y = y;
		}
		
		public Image getImage(){
			return image;
		}
		
		public int getImgH(){
			return imgH;
		}
		
		public int getImgW(){
			return imgW;
		}
		
		public int getX(){
			return x;
		}
		
		public int getY(){
			return y;
		}
		
	};
	
	
	public void startTimer2(){
		if(timer2 == null){
			timer2 = new Timer(500, new TClass());
			timer2.start();
		}
		else{
			timer2.stop();
			timer2.start();
		}
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		int i;
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform at = AffineTransform.getTranslateInstance(100, screenY-100+ball.getY());
		at.rotate(angle, ball.getImage().getWidth(this)*0.5, ball.getImage().getHeight(this)*0.5);
		angle += angleInc;
		if(angle >= 2*Math.PI)
			angle = 0;
		g2d.drawImage(ball.getImage(), at, this);
		
		for(Wall wall : walls){
			g2d.drawImage(wall.getImage(), (wall.getX() + wallx) % screenX, wall.getY() + ball.getImage().getHeight(this), this);
		}
		
		//wallx -= angleInc * 100/2;
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
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
			if(keycode == KeyEvent.VK_UP){
				startTimer2();
				ball.animate();
				
			}
			if(keycode == KeyEvent.VK_RIGHT)
				angleInc = ANGLE_INC;
			if(keycode == KeyEvent.VK_LEFT)
				angleInc = ANGLE_INC;
		}
		public void keyPressed(KeyEvent e){
			int keycode = e.getKeyCode();
			if(keycode == KeyEvent.VK_RIGHT)
				angleInc = ANGLE_INC_ACCEL;
			if(keycode == KeyEvent.VK_LEFT)
				angleInc = - ANGLE_INC_ACCEL;
		}
	}
}
