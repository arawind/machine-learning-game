package game;

import javax.swing.JFrame;


public class Game extends JFrame {

	public Game(){
		add(new Board());
		setSize(400, 300);
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
