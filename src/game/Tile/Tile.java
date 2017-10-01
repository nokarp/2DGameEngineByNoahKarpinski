package game.Tile;

import game.Main.Main;

import java.awt.image.BufferedImage;

public class Tile {
	private BufferedImage b[];
	private int type;
	public Tile(BufferedImage im, int t){
		type = t;
		if(type >= 90){
			b = new BufferedImage[2];
			b[0] = Main.bottomPixels[type - 90];
			b[1] = im;
		}
		else{
			b = new BufferedImage[1];
			b[0] = im;
		}
	}
	public int getType(){
		return type;
	}
	public BufferedImage getBufferedImage(){
		return b[0];
	}
	public BufferedImage getTopBufferedImage(){
		return b[1];
	}
}
