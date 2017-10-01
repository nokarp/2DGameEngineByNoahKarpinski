package game.SpriteTypes;

public abstract class Seeker extends Stander{
	protected int range;
	public Seeker(int xPos, int yPos, int xSize, int ySize, int animationSizeX, int animationSizeY, String sheetName) {
		super(xPos, yPos, xSize, ySize, animationSizeX, animationSizeY, sheetName);
	}
	public abstract void move();
	public void seek(){
		
	}
}
    