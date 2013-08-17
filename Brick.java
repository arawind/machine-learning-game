package game;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Brick {
		private int x, y;
		private Image image;
		
		public Brick(int x, int y){
			ImageIcon ii = new ImageIcon(this.getClass().getResource("wall.png"));
			image = ii.getImage();
			this.x = x;
			this.y = y;
		}
		
		public Image getImage(){
			return image;
		}
		
		public int getImgH(ImageObserver arg0){
			return image.getHeight(arg0);
		}
		
		public int getImgW(ImageObserver arg0){
			return image.getWidth(arg0);
		}
		
		public int getX(){
			return x;
		}
		
		public int getY(){
			return y;
		}
		
}