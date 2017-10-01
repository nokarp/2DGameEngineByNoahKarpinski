package game.SpriteTypes;

import game.Main.Main;
import game.Player.Collision;

public abstract class Jumper extends Stander{
	protected int characterXSize, characterYSize;
	protected boolean jumping;
	public Jumper(int xPos, int yPos, int xSize, int ySize, int characterXSize, int characterYSize, int animationSizeX, int animationSizeY, String sheetName) {
		super(xPos, yPos, xSize, ySize, animationSizeX, animationSizeY, sheetName);
		this.characterXSize = characterXSize * Main.SCALE;
		this.characterYSize = characterYSize * Main.SCALE;
		centerCollisionX =(int) (x + (this.xSize - this.characterXSize) + (.5 * this.characterXSize));
		centerCollisionY =(int) (y + (this.ySize - this.characterYSize) + (.5 * this.characterYSize));
		jumping = true;
	}
	public void updateCollisionPositions(){
		
		centerCollisionX =(int) (x + (xSize - characterXSize) + (.5 * characterXSize));
		centerCollisionY =(int) (y + (ySize - characterYSize) + (.5 * characterYSize));
		//System.out.println(centerCollisionX + " " + centerCollisionY);
	}
	public boolean playerCollision(int playerX, int playerY) {
		return Collision.checkSquareToSquareCollisionSprite(x + (xSize - characterXSize), y + (ySize - characterYSize), characterXSize, characterYSize);
	}

}
