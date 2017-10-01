package game.Graphics;

import game.Main.Main;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHandling {
	public BufferedImage load(String path){
		try {
			return ImageIO.read(getClass().getResource(path + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public BufferedImage cropSpecialSize(int x, int y, int sizeX, int sizeY, BufferedImage sheet){
		return formatImage(sizeX, sizeY, Main.SCALE, sheet.getSubimage(x * sizeX, y * sizeY, sizeX, sizeY));
	}
	public BufferedImage crop(int x, int y, BufferedImage sheet){
		return formatImage(Main.TILESIZE, Main.TILESIZE, Main.SCALE, sheet.getSubimage(x * (Main.TILESIZE), y * (Main.TILESIZE), Main.TILESIZE, Main.TILESIZE));
	}
	public BufferedImage cropWithSpaces(int x, int y, BufferedImage sheet){
		return formatImage(Main.TILESIZE, Main.TILESIZE, Main.SCALE, sheet.getSubimage(x * (Main.TILESIZE + 1), y * (Main.TILESIZE + 1), Main.TILESIZE, Main.TILESIZE));
	}
	public BufferedImage cropSprite(int x, int y, int xSize, int ySize, BufferedImage sheet){
		return formatImage(xSize, ySize, Main.SCALE, sheet.getSubimage(x * (xSize + 1), y * (ySize + 1), xSize, ySize));
	}
	public BufferedImage[] cropSet(String sheetName, int w){
		BufferedImage sheet = load(sheetName);
		BufferedImage[] b = new BufferedImage[w];
		for(int x = 0; x < w; x++){
			b[x] = cropSpecialSize(x, 0, 1, 1, sheet);
		}
		return b;
			
	}
	public BufferedImage[][] cropSetTiles(String sheetName, int w, int h){
		BufferedImage sheet = load(sheetName);
		BufferedImage[][] b  = new BufferedImage[w][h];
		for(int y = 0; y < h; y++)
			for(int x = 0; x < w; x++)
				b[x][y] = crop(x, y, sheet);
		return b;
	}
	public BufferedImage[][] cropSetWithSpaces(String sheetName, int w, int h){
		BufferedImage sheet = load(sheetName);
		BufferedImage[][] b  = new BufferedImage[w][h];
		for(int y = 0; y < h; y++)
			for(int x = 0; x < w; x++)
				b[x][y] = cropWithSpaces(x, y, sheet);
		return b;
	}
	public BufferedImage[][] cropSpriteSet(int w, int h, int xSize, int ySize, BufferedImage sheet){
		BufferedImage[][] b  = new BufferedImage[w][h];
		for(int y = 0; y < h; y++)
			for(int x = 0; x < w; x++)
				b[x][y] = cropSprite(x, y, xSize, ySize, sheet);
		return b;
	}
	
	//I looked this up. It formats the image so it is best for the computer.
	public BufferedImage formatImage(int width, int height, int scale, BufferedImage b){
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice device = env.getDefaultScreenDevice();
	    GraphicsConfiguration config = device.getDefaultConfiguration();
	    BufferedImage buff = config.createCompatibleImage(width, height);
	    buff.setRGB(0,0,width,height,new int[width*height],0,width);
	    return b;
	}
}
