package game.Sprites;

import java.awt.image.BufferedImage;

import game.Main.Main;
import game.SpriteTypes.Item;

public class SwimGear extends Item{
	private int animationCount = 0;
	private static BufferedImage menuImage;
	public SwimGear(int xPos, int yPos){
		super(xPos, yPos, 16, 16, 3, 1, "SwimGear");
		menuImage = b[1][0];
	}
	public void tick(){
		if(animationCount == 100) 
			animationCount = 0;
		animationCount++;
		if(animationCount <=25)
			animationPoint[0] = 0;
		else if(animationCount <=50)
			animationPoint[0] = 1;
		else if(animationCount <=75)
			animationPoint[0] = 2;
		else
			animationPoint[0] = 1;
		int[] point = Main.p.getCurrentTile(Main.p.getActualX() + (((int)(.5 *Main.TILESIZE) - 1) * Main.SCALE), Main.p.getActualY()+ (((int)(.5 *Main.TILESIZE) - 1) * Main.SCALE));
		if(point[0] == (int)(x / Main.T) && point[1] == (int)(y / Main.T)){
			Main.mm.enableKeyItem(0, 0);
			obtained = true;
			deactivate();
			Main.p.swimGearObtained = true;
		}
	}
	public boolean playerCollision(int playerX, int playerY) {
		return false;
	}
	public static BufferedImage getMenuImage(){
		return menuImage;
	}
}
 