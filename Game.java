package game;

import javax.swing.JFrame;


public class Game extends JFrame {

	public Game(){
		add(new Board(640,480));
		setSize(640,480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Ball");
		setVisible(true);
		setResizable(false);
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
