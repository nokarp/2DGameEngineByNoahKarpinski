package game.Sprites;

import game.Player.Collision;
import game.SpriteTypes.Jumper;

public class JumpingKid extends Jumper{
	private int animationCount = 0;
	public JumpingKid(int xPos, int yPos) {
		super(xPos, yPos, 16, 23, 14, 5, 4, 1, "JumpingKid");
		animationPoint[1] = 0;
	}
	public void tick() {
		animationCount++;
		if(animationCount < 60 || jumping == false)
			animationPoint[0] = 0;
		else if(animationCount < 65)
			animationPoint[0] = 1;
		else if(animationCount < 70)
			animationPoint[0] = 2;
		else if(animationCount < 80)
			animationPoint[0] = 3;
		else if(animationCount < 85)
			animationPoint[0] = 2;
		else if(animationCount < 90)
			animationPoint[0] = 1;
		else{
			animationPoint[0] = 0;
			animationCount = 0;
		}
		updateCollisionPositions();
	}
	public boolean playerCollision(int playerX, int playerY) {
		return Collision.checkSquareToSquareCollisionSprite(x, y + (ySize - characterYSize), characterXSize, characterYSize);
	}
}
