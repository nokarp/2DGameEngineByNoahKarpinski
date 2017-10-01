package game.Sprites;

import game.Main.Main;
import game.Player.Collision;
import game.Player.Controls;
import game.SpriteTypes.Sprite;
import game.SpriteTypes.Talkable;

public class Sign extends Sprite implements Talkable{
	private String message;
	private int tileX, tileY, collisionSizeY;
	public Sign(int xPos, int yPos, String message) {
		super(xPos, yPos, 16, 16, 1, 1, "Sign");
		this.message = message;
		tileX = xPos;
		tileY = yPos;
		animationPoint[0] = 0;
		animationPoint[1] = 0;
		collisionSizeY = 4 * Main.SCALE;
		centerCollisionX = (int) (x + (.5 * xSize));
		centerCollisionY = (int) (y + (ySize- collisionSizeY) + (.5 * collisionSizeY));
	}
	public void displayText() {
		Main.tm.setCurrentMessage(message);
	}
	public void tick() {
		//Checks if the player is in front of the sign
		if(Controls.talking && Main.p.getCurrentXTile() == tileX && (Main.p.getCurrentYTile() == tileY || Main.p.getCurrentYTile() == tileY + 1))
			displayText();
	}
	
	public boolean playerCollision(int playerX, int playerY) {
		return Collision.checkSquareToSquareCollisionSprite(x, y + (ySize - collisionSizeY), 15 * Main.SCALE, collisionSizeY);
	}

}
