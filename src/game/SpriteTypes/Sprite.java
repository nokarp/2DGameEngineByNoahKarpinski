package game.SpriteTypes;

import game.Main.Main;

import java.awt.image.BufferedImage;

public abstract class Sprite {
	protected boolean active, visible;
	protected int x, y, centerCollisionX, centerCollisionY, collisionStartX, collisionStartY, collisionDistanceX, collisionDistanceY,
	collisionXLeft, collisionYLeft, collisionXRight, collisionYRight, xSize, ySize;
	protected int[] animationPoint;
	protected BufferedImage[][] b;
	public abstract void tick();
	public abstract boolean playerCollision(int playerX, int playerY);
	public Sprite(int xPos, int yPos, int xSize, int ySize, int animationSizeX, int animationSizeY, String sheetName){
		animationPoint = new int[2];
		if(animationSizeX == 1)
			animationPoint[0] = 0;
		if(animationSizeY == 1)
			animationPoint[1] = 0;
		if(xSize > Main.TILESIZE)
			x = (xPos * Main.T) - (Main.SCALE * (xSize - Main.TILESIZE));
		else
			x = xPos * Main.T;
		if(ySize > Main.TILESIZE)
			y = (yPos * Main.T) - (Main.SCALE * (ySize - Main.TILESIZE));
		else
			y = yPos * Main.T;
		this.xSize = xSize * Main.SCALE;
		this.ySize = ySize * Main.SCALE;
		
		//By default a sprites entire image is the collision box.
		centerCollisionX = (int) (x + (.5 * this.xSize));
		centerCollisionY = (int) (y + (.5 * this.ySize));
		//System.out.println(centerCollisionX + " " + centerCollisionY);
		//This is for location change since it doesn't have any graphics
		if(sheetName != null)
			b = Main.im.cropSpriteSet(animationSizeX, animationSizeY,  xSize, ySize, Main.im.load("/Sprites/" + sheetName));
		
		activate();
	}
	
	public void updateCenterPoints(){
		centerCollisionX = (int) (x + (.5 * this.xSize));
		centerCollisionY = (int) (y + (.5 * this.ySize));
	}
	
	public void render(int xAdjustment, int yAdjustment){
		Main.g.drawImage(this.getBufferedImage(), this.getX() + xAdjustment, this.getY() + yAdjustment,
				this.getXSize(), this.getYSize(), null);	
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getXSize(){
		return xSize;
	}
	public int getYSize(){
		return ySize;
	}
	public int getCenterCollisionX(){
		return centerCollisionX;
	}
	public int getCenterCollisionY(){
		return centerCollisionY;
	}
	public boolean isVisible(){
		return visible;
	}
	public boolean isActive() {
		return active;
	}
	public void deactivate(){
		active = false;
		visible = false;
	}
	public void activate(){
		active = true;
		visible = true;
	}
	public BufferedImage getBufferedImage(){
		return b[animationPoint[0]][animationPoint[1]];
	}
	
}
