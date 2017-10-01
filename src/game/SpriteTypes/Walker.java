package game.SpriteTypes;

import game.Main.Main;

public abstract class Walker extends Stander{
	protected int tileX, tileY, initialTileX, initialTileY, tileXLow, tileYLow, tileXHigh, tileYHigh,
	previousTileXLow, previousTileYLow, previousTileXHigh, previousTileYHigh, direction, range;
	protected boolean moving, paused;
	public abstract void move();
	public Walker(int xPos, int yPos, int xSize, int ySize, int animationSizeX, int animationSizeY, String sheetName) {
		super(xPos, yPos, xSize, ySize, animationSizeX, animationSizeY, sheetName);
		moving = false;
		paused = false;
		initialTileX = xPos;
		initialTileY = yPos;
		tileX = xPos;
		tileY = yPos;
		tileXLow = xPos;
		tileYLow = yPos;
		tileXHigh = xPos;
		tileYHigh = yPos;
		previousTileXLow = xPos + 1;
		previousTileYLow = yPos + 1;
		previousTileXHigh = xPos + 1;
		previousTileYHigh = yPos + 1;
	}
	public void tick(){
		
	}
	public boolean playerCollision(int playerX, int playerY) {
		int tempX = x, tempY = y;
		if(direction == 3)
			tempX += (int)(Main.p.getSPEED() * .5);
		else if(direction == 2)
			tempY -= (int)(Main.p.getSPEED() * .5);
		else if(direction == 1)
			tempY += (int)(Main.p.getSPEED() * .5);
		else
			tempY -= (int)(Main.p.getSPEED() * .5);
		if(playerX <= tempX + xSize && playerX >= tempX && playerY <= tempY + ySize && playerY >= tempY){
			paused = true;
			return true;
		}
		return false;
	}
}
