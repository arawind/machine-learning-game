package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import javax.swing.Timer;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener{
	private Timer timer;
	private Timer timer2;
	private Ball ball;
	private double angle;
	public Board(){
		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);
		setDoubleBuffered(true);
		ball = new Ball();
		angle = 0;
		timer = new Timer(5, this);
		timer.start();
	}
	
	public void startTimer2(){
		timer2 = new Timer(1500, new TClass());
		timer2.start();
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform at = AffineTransform.getTranslateInstance(200, 100+ball.getY());
		at.rotate(angle, ball.getImage().getWidth(this)*0.5, ball.getImage().getHeight(this)*0.5);
		angle += 0.02;
		
		g2d.drawImage(ball.getImage(), at, this);
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
		}
	}
}
