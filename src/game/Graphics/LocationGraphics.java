package game.Graphics;

import game.Main.Main;
import game.SpriteTypes.Enemy;
import game.Tile.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LocationGraphics extends ImageHandling{
	public Tile[][] map;
	private int[] currentTile;
	private int centerX, centerY, topYOffset, leftXOffset, negativeYOffset, positiveYOffset, negativeXOffset,
				positiveXOffset, xStart, xEnd, yStart, yEnd, xDrawOffset, yDrawOffset, xEdgeAdjustment, yEdgeAdjustment, currentXAdjustment, currentYAdjustment;
	public boolean corner, superCorner;
	public int getXStart(){
		return xStart - positiveXOffset;
	}
	public int getXEnd(){
		return xEnd + negativeXOffset;
	}
	public int getYStart(){
		return yStart - positiveYOffset;
	}
	public int getYEnd(){
		return yEnd + negativeYOffset;
	}
	public int getX(ArrayList<String> a){
		return Integer.parseInt((String)(a.get(0)));
	}
	public int getY(ArrayList<String> a){
		return  Integer.parseInt((String)(a.get(1)));
	}
	public int getType(ArrayList<String> a){
		return  Integer.parseInt((String)(a.get(2)));
	}
	public void render(int width, int height){
		centerX = Main.p.getActualX();
		centerY = Main.p.getActualY();
		currentTile = Main.p.getCurrentTile(centerX, centerY);
		topYOffset = centerY % Main.T;
		leftXOffset = centerX % Main.T;
		xEdgeAdjustment = 0;
		yEdgeAdjustment = 0;
		
		/* Without the draw offset the map section is rendered at the location that it would be in if the map was still. Since currentTile is in use, the position
		 * of the map is relative to the player. This causes the map to still be on screen but it ends at the players location. Adding half of the screen size
		 *  centers the image. By taking the offsets at the end, show part of a tile on screen so that there is no skipping.
		 */
		yDrawOffset =  - (currentTile[1] - (int)(.5 * (Main.HEIGHT / Main.TILESIZE))) * Main.T - topYOffset;
		xDrawOffset =  - (currentTile[0] - (int)(.5 * (Main.WIDTH / Main.TILESIZE))) * Main.T - leftXOffset;
		
		xStart = currentTile[0] - (int)(.5 * (Main.WIDTH / Main.TILESIZE));
		if(xStart < 0){
			negativeXOffset = -1 * xStart;
			xStart = 0;
		}
		else
			negativeXOffset = 0;
		xEnd = currentTile[0] + (int)(.5 * (Main.WIDTH / Main.TILESIZE));
		if(xEnd > width - 1){
			positiveXOffset = xEnd - (width - 1); 
			xEnd = width - 1; 
			xEdgeAdjustment = -(width - (int)(Main.WIDTH/Main.TILESIZE)) * Main.T;
		}
		else
			positiveXOffset = 0;
		if(width <= (int)(Main.WIDTH/Main.TILESIZE)){
			superCorner = true;
			xStart = 0;
			xEnd = width - 1;
			positiveXOffset = 0;
			negativeXOffset = 0;
		}
		yStart = currentTile[1] - (int)(.5 * (Main.HEIGHT / Main.TILESIZE));
		if(yStart < 0){
			negativeYOffset  = -1 * yStart;
			yStart = 0;
		}
		else
			negativeYOffset = 0;
		yEnd = currentTile[1] + (int)(.5 * (Main.HEIGHT / Main.TILESIZE));
		if(yEnd > height - 1){
			positiveYOffset = yEnd - (height - 1);
			yEnd = height - 1;
			yEdgeAdjustment = -(height - (int)(Main.HEIGHT/Main.TILESIZE)) * Main.T;
		}
		else
			positiveYOffset = 0;
		if(height <= (int)(Main.HEIGHT/Main.TILESIZE)){
			superCorner = true;
			yStart = 0;
			yEnd = height - 1;
			positiveYOffset = 0;
			negativeYOffset = 0;
		}
		if((negativeXOffset > 0 && negativeYOffset > 0) || (negativeXOffset > 0 && positiveYOffset > 0) || (positiveXOffset > 0 && positiveYOffset > 0) || (positiveXOffset > 0 && negativeYOffset > 0) || superCorner){
			corner = true;
		}
		else{
			corner = false;
		}
		superCorner = false;
		
		//	Render in corners the character actually moves on the screen.
		if(corner){
			if(Main.p.getXSpeed() == 0 && negativeXOffset > 0)
				Main.p.setX(Main.p.getX() - Main.p.getSPEED());
			if(Main.p.getYSpeed() == 0 && negativeYOffset > 0)
				Main.p.setY(Main.p.getY() - Main.p.getSPEED());
			renderSection(Main.p.getSPEED(), Main.p.getSPEED(), xEdgeAdjustment, yEdgeAdjustment);
		}
		//This is the left and right sides
		else if(negativeXOffset > 0 || positiveXOffset > 0){
			if(Main.p.getYSpeed() == Main.p.getSPEED() && yEnd + 1 > height - 1)
				Main.p.setY(Main.p.getY() + Main.p.getSPEED());
			else if(Main.p.getXSpeed() == 0 && negativeXOffset > 0)
				Main.p.setX(Main.p.getX() - Main.p.getSPEED());
			
			renderSection(Main.p.getSPEED(), 0, xEdgeAdjustment, yDrawOffset);
		}
		//This is the upper and lower sides 
		else if(negativeYOffset > 0 || positiveYOffset > 0){
			if(Main.p.getXSpeed() == Main.p.getSPEED() && xEnd + 1 > width - 1)
				Main.p.setX(Main.p.getX() + Main.p.getSPEED());
			else if(Main.p.getYSpeed() == 0 && negativeYOffset > 0)
				Main.p.setY(Main.p.getY() - Main.p.getSPEED());
			renderSection(0, Main.p.getSPEED(), xDrawOffset, yEdgeAdjustment);
		}	
		
		//This is when there are no corners
		else{
			if(Main.p.getXSpeed() == Main.p.getSPEED() && xEnd + 1 > width - 1)
				Main.p.setX(Main.p.getX() + Main.p.getSPEED());
			if(Main.p.getYSpeed() == Main.p.getSPEED() && yEnd + 1 > height - 1)
				Main.p.setY(Main.p.getY() + Main.p.getSPEED());
			renderSection(0, 0, xDrawOffset, yDrawOffset);		
		} 
	}
	public void renderSection(int xSpeed, int ySpeed, int xAdjustment, int yAdjustment){
		currentXAdjustment = xAdjustment;
		currentYAdjustment = yAdjustment;
		for(int y = yStart - positiveYOffset; y <= yEnd + negativeYOffset; y++){
			for(int x = xStart - positiveXOffset; x <= xEnd + negativeXOffset; x++){
				Main.p.setXSpeed(xSpeed);
				Main.p.setYSpeed(ySpeed);
				drawToScreen(map[x][y].getBufferedImage(), x, y, xAdjustment, yAdjustment);
			}
		}
		boolean playerRendered = false;
		if(Main.currentLocation.ls.sprites.get(0) == null || Main.p.getCenterCollisionY() < Main.currentLocation.ls.sprites.get(0).getCenterCollisionY()){
			Main.p.render();
			playerRendered = true;
		}
		for(int i = 0; i < Main.currentLocation.ls.sprites.size(); i++){
			if(Main.currentLocation.ls.sprites.get(i).isActive()){
				if(!playerRendered && (Main.p.getCenterCollisionY() <= Main.currentLocation.ls.sprites.get(i).getCenterCollisionY())){
					Main.p.render();
					playerRendered = true;
				}
				if(Main.currentLocation.ls.sprites.get(i).isVisible())
					Main.currentLocation.ls.sprites.get(i).render(xAdjustment, yAdjustment);
			}
		}
		if(!playerRendered)
			Main.p.render();
	}
	public void renderAboveLayer(){
		for(int y = yStart - positiveYOffset; y <= yEnd + negativeYOffset; y++){
			for(int x = xStart - positiveXOffset; x <= xEnd + negativeXOffset; x++){
				if(map[x][y].getType() >= 90)
					drawToScreen(map[x][y].getTopBufferedImage(), x, y, currentXAdjustment, currentYAdjustment);
			}
		}
	}
	public void drawToScreen(BufferedImage image, int x, int y, int xOff, int yOff){
		Main.g.drawImage(image, (x * Main.T) + xOff, (y * Main.T) + yOff, Main.T, Main.T, null);
	}
	public void renderHealthBarsAndDamage() {
		for(int i = 0; i < Main.currentLocation.ls.sprites.size(); i++){
			if(Main.currentLocation.ls.sprites.get(i) != null && Main.currentLocation.ls.sprites.get(i).isVisible() && Main.currentLocation.ls.sprites.get(i) instanceof Enemy){
				((Enemy) Main.currentLocation.ls.sprites.get(i)).renderHealthBarAndDamage(currentXAdjustment, currentYAdjustment);
			}
		}
	}
}
