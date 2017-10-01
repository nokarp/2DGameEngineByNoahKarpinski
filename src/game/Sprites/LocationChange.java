package game.Sprites;

import game.Main.Main;
import game.SpriteTypes.Sprite;

public class LocationChange extends Sprite{
	protected int tileX, tileY;
	private String locationName, song;
	public LocationChange(int xPos, int yPos, String newLocation, String songName, int initialTileX, int initialTileY) {
		super(xPos, yPos, 16, 16, 1, 1, null);
		tileX = initialTileX;
		tileY = initialTileY;
		locationName = newLocation;
		song = songName;
		animationPoint[0] = 0;
		visible = false;
	}
	public void tick() {
		int[] point = Main.p.getCurrentTile(Main.p.getActualX() + (((int)(.5 *Main.TILESIZE)-1) * Main.SCALE), Main.p.getActualY() + (((int)(.875 *Main.TILESIZE)) * Main.SCALE));
		if(point[0] == x / Main.T && point[1] == y / Main.T){
			Main.beginningTransition = true;
			Main.transition = true;
			Main.paused = true;
			Main.transitionCount = 29;
			Main.locationChange = this;
		}
		
	}
	public boolean playerCollision(int playerX, int playerY) {
		return false;
	}
	public String getSong(){
		return song;
	}
	public String getLocationName(){
		return locationName;
	}
	public void activate(){
		active = true;
	}
	public int getTileX(){
		return tileX;
	}
	public int getTileY(){
		return tileY;
	}
}
