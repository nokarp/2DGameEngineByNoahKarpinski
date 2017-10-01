package game.SpriteTypes;

import game.Main.Main;


public abstract class Item extends Sprite{
	protected boolean obtained = false;
	public Item(int xPos, int yPos, int xSize, int ySize, int animationSizeX, int animationSizeY, String sheetName){
		super(xPos, yPos, xSize, ySize, animationSizeX, animationSizeY, sheetName);
	}
	public boolean isObtained(){
		return obtained;
	}
	public void collision(){
		int[] point = Main.p.getCurrentTile(Main.p.getActualX() + (((int)(Main.TILESIZE * .5)-1) * Main.SCALE), Main.p.getActualY() + ((int)(Main.TILESIZE * .5)-1));
		if(point[0] == (x / Main.T) && point[1] == (y / Main.T)){
			obtained = true;
			deactivate();
		}
	}
}
