package game;
import java.awt.event.KeyEvent;

public class Data {
	private static int count;
	private int level, obPos, key, jumps;
	private long duration;
	public Data(int level, int obPos, int key, long duration, int jumps){
		this.level = level;
		this.obPos = obPos;
		this.key = key;
		this.jumps = jumps;
		this.duration = duration;
		count++;
	}
	public int getLevel(){
		return level;
	}
	public int getPos(){
		return obPos;
	}
	public int getKey(){
		return key;
	}
	public long getDuration(){
		return duration;
	}
	public int getJumps(){
		return jumps;
	}
	public int count(){
		return count;
	}
	
	
}
