package game.Sprites;

import game.Main.Main;
import game.Player.Controls;
import game.SpriteTypes.SpriteCollision;
import game.SpriteTypes.Talkable;
import game.SpriteTypes.Walker;

public class Guy01 extends Walker implements Talkable{
	private int moveCount, pauseDuration, animationCount,
				centerConstant = ((int)(Main.TILESIZE * .5) - 1) * Main.SCALE;
	public Guy01(int xPos, int yPos, int range){ 
		super(xPos, yPos, 16, 16, 3, 4, "Guy01");
		direction = (int)(Math.random()*4);
		pauseDuration = (int)(Math.random() * 121);
		animationCount = 0;
		moveCount = 0;
		this.range = range;
	}
	public void move(){
		if(!paused){
			if((tileXLow == previousTileXLow && tileYLow == previousTileYLow) || (tileXHigh == previousTileXHigh && tileYHigh == previousTileYHigh)){
				if(direction == 3)
					x += (int)(Main.p.getSPEED() * .5);
				else if(direction == 2)
					x -= (int)(Main.p.getSPEED() * .5);
				else if(direction == 1)
					y += (int)(Main.p.getSPEED() * .5);
				else
					y -= (int)(Main.p.getSPEED() * .5);
				if(!(tileXLow == previousTileXLow && tileYLow == previousTileYLow)){
					previousTileXHigh = tileXHigh;
					previousTileYHigh = tileYHigh;
				}
				if(!(tileXHigh == previousTileXHigh && tileYHigh == previousTileYHigh)){
					previousTileXLow = tileXLow;
					previousTileYLow = tileYLow;
				}
				tileXLow = x / Main.T;
				tileYLow = (y + Main.T-Main.SCALE) / Main.T;
				tileXHigh = (x + Main.T-Main.SCALE) / Main.T;
				tileYHigh = y / Main.T;
				moveCount = 0;
				pauseDuration = (int)(Math.random() * 121);
			}
			else{
				moveCount++;
				tileX = (x + centerConstant) / Main.T;
				tileY = (y + centerConstant) / Main.T;
				moving = false;
				if(moveCount == 60 + pauseDuration){
					moving = true;
					do{
						direction = (int)(Math.random()*4);
					}while(outOfBounds());
					previousTileXLow = tileXLow;
					previousTileYLow = tileYLow;
					previousTileXHigh = tileXHigh;
					previousTileYHigh = tileYHigh;
				}
			}
		}
	}
	public boolean outOfBounds(){
		int tempTileX = tileX, tempTileY = tileY;
		if(direction == 3)
			tempTileX++;
		else if(direction == 2)
			tempTileX--;
		else if(direction == 1)
			tempTileY++;
		else
			tempTileY--;
		if((tempTileX > initialTileX + range || tempTileY > initialTileY + range || 
			tempTileX < initialTileX - range || tempTileY < initialTileY - range) || SpriteCollision.collision(tempTileX, tempTileY))
			return true;
		return false;
	}
	public void displayText() {
		Main.tm.setCurrentMessage("This phrase is a test. Hopefully the second line works correctly.");
	}
	public void tick(){
		if(!moving || paused)
			animationPoint[0] = 0;
		else{
			if(animationCount == 30)
				animationCount = 0;
			animationCount++;
			if(animationCount <=15)
				animationPoint[0] = 1;
			else
				animationPoint[0] = 2;
			animationPoint[1] = direction;
		}
		paused = false;
		move();
		int tempX = Main.p.getCurrentXTile(), tempY = Main.p.getCurrentYTile();
		if(Controls.talking && tempX >= tileX - 1  && tempX <= tileX + 1 && tempY >= tileY - 1 && tempY <= tileY + 1){
			if(tempX < tileX)
				animationPoint[1] = 2; 
			else if(tempX > tileX)
				animationPoint[1] = 3; 
			else if(tempY < tileY)
				animationPoint[1] = 0; 
			else
				animationPoint[1] = 1; 
			displayText();
		}
	}

}
