package game.Player;

import java.util.ArrayList;

import game.Main.Main;

public class Collision {
	// Tiles that cause damage or any other effect need to be a sprite since they have an effect when the player is not moving
	// Protected and extends to location and locationGraphics and player and playerGraphics
	private static int[] tempTile = new int[2];
	private static int type, animationSwitchCount;
	private static int x, y;
	//private static boolean result1, result2, result3, result4;
		
	public static boolean collision(String direction, int speed){
		Main.p.updateHitboxs();
		/*LeftBottomHitbox[0] = Main.p.xLeft;
		LeftBottomHitbox[1] = Main.p.yLeft;
		RightBottomHitbox[0] = Main.p.xRight;
		RightBottomHitbox[1] = Main.p.yRight;
		LeftTopHitbox[0] = Main.p.xLeft;
		LeftTopHitbox[1] = Main.p.yLeft-(3 * Main.SCALE);
		RightTopHitbox[0] = Main.p.xRight;
		RightTopHitbox[1] = Main.p.yRight-(3 * Main.SCALE);
		if(!direction.equals("p")){
			result1 = checkCollision(speed, direction, LeftBottomHitbox);
			result2 = checkCollision(speed, direction, RightBottomHitbox);
			result3 = checkCollision(speed, direction, LeftTopHitbox);
			result4 = checkCollision(speed, direction, RightTopHitbox);
			if(animationSwitchCount == 4)
				Main.p.pg.animationSet = 1;
			animationSwitchCount = 0;
			return result1 || result2 || result3 || result4;
		}
		result1 = checkPlayerCollision(LeftBottomHitbox[0], LeftBottomHitbox[1]);
		result2 = checkPlayerCollision(RightBottomHitbox[0], RightBottomHitbox[1]);
		result3 = checkPlayerCollision(LeftTopHitbox[0], LeftTopHitbox[1]);
		result4 = checkPlayerCollision(RightTopHitbox[0], RightTopHitbox[1]);
		return result1 || result2 || result3 || result4;
			*/
		ArrayList<int[]> tiles = new ArrayList<int[]>();
		int tempX, tempY;
		boolean copy = false, xOverextended = false, yOverextended = false;
		
		if(direction.equals("x")){
			x = Main.p.getXStart() + speed;
			y = Main.p.getYStart();
		}
		else if (direction.equals("y")){
			x = Main.p.getXStart();
			y = Main.p.getYStart() + speed;
		}
		if(!direction.equals("p")){
			for(int yCount = 0; yCount <= Main.p.getCOLLISIONSIZEY(); yCount++){
				tempY = y + (yCount * Main.T);
				if(tempY > y + Main.p.getCOLLISIONDISTANCEY()){
					tempY = y + Main.p.getCOLLISIONDISTANCEY();
					yOverextended = true;
				}
				for(int xCount = 0; xCount <= Main.p.getCOLLISIONSIZEX(); xCount++){
					tempX = x + (xCount * Main.T);
					if(tempX > x + Main.p.getCOLLISIONDISTANCEX()){
						xOverextended = true;
						tempX = x + Main.p.getCOLLISIONDISTANCEX();
					}
					tempTile = Main.p.getCurrentTile(tempX, tempY);
					for(int i = 0; i < tiles.size(); i++){
						if(tempTile[0] == tiles.get(i)[0] && tempTile[1] == tiles.get(i)[1]){
							copy = true;
							break;
						}
					}
					if(!copy){
						if(collisionOccured())
							return true;
						tiles.add(tempTile);
					}
					copy = false;
					if(xOverextended){
						xOverextended = false;
						break;
					}
				}
				if(yOverextended)
					break;
			}
		}
		//If no tile collision is found sprite collisions are checked
		for(int i = 0; i < Main.currentLocation.ls.sprites.size(); i++){
			if(Main.currentLocation.ls.sprites.get(i) != null && Main.currentLocation.ls.sprites.get(i).playerCollision(x, y))
				return true;
		}
		return false;
	}
	private static boolean collisionOccured() {
		if( x < 0 || x + Main.p.getCOLLISIONDISTANCEX() >= (Main.currentLocation.w * Main.T) || y < Main.p.getCOLLISIONSTARTY() || y + Main.p.getCOLLISIONDISTANCEY() >= (Main.currentLocation.h * Main.T))
			return true;
		type = Main.currentLocation.lg.map[tempTile[0]][tempTile[1]].getType();
		switch(type){
			case 0: //Open tile
				break;
			case 1: //Full tile	
				return true;
			case 2: //Bottom-Left to Top-Right tile
				
				break;
			case 3: //Top-Left to Bottom-Right tile
				if(checkSquareToDownwardSlantCollision())
					return true;
				break;
			case 4: //Top tile
				if(checkSquareToSquareCollision(0,0,Main.TILESIZE,3))
					return true;
				break;
			case 5: //Bottom tile
				if(checkSquareToSquareCollision(0,14,Main.TILESIZE,Main.TILESIZE - 14))
					return true;
				break;
			case 6: //Left tile
				if(checkSquareToSquareCollision(-1,0,1,Main.TILESIZE))
					return true;
				break;
			case 7: //Right tile
				if(checkSquareToSquareCollision(Main.TILESIZE - 1, 0, Main.TILESIZE - 1, Main.TILESIZE))
					return true;
				break;
			case 10: //Water tile
				if(Main.p.swimGearObtained){
					animationSwitchCount++;
					
					break;
				}
				return true;
			case 11: //Left Side Water tile
				if(Main.p.swimGearObtained){
					animationSwitchCount++;
					break;
				}
				return true;
			case 12: //Right Side Water tile
				if(Main.p.swimGearObtained){
					animationSwitchCount++;
					break;
				}
				return true;
			case 27: //Bottom-Left to Top-Right tile and Right side
	
				break;
			case 36: //Top-Left to Bottom-Right tile and Left side
	
				break;
			case 90:
				break;
			default: //No type
				System.out.println("Collision error");
				break;
		}
		return false;
	}
	
	public static boolean checkSquareToSquareCollision(int xStart, int yStart, int xDistance, int yDistance){
		int xStart1 = (tempTile[0] * Main.T) + (xStart * Main.SCALE);
		int yStart1 = (tempTile[1] * Main.T) + (yStart * Main.SCALE);
		int xDistance1 = xDistance * Main.SCALE;
		int yDistance1 = yDistance * Main.SCALE;
		int xStart2 = x;
		int yStart2 = y;
		int xDistance2 = Main.p.getCOLLISIONDISTANCEX();
		int yDistance2 = Main.p.getCOLLISIONDISTANCEY();
		boolean xResult = false, yResult = false;
		if((xStart1 + xDistance1) >= (xStart2 + xDistance2))
			xResult = (xStart2 + xDistance2) > xStart1;
		else
			xResult = (xStart1 + xDistance1) > xStart2;
		if((yStart1 + yDistance1) >= (yStart2 + yDistance2))
			yResult = (yStart2 + yDistance2) > yStart1;
		else
			yResult = (yStart1 + yDistance1) > yStart2;
		return xResult && yResult;
	}
	
	public static boolean checkSquareToSquareCollisionSprite(int xStart, int yStart, int xDistance, int yDistance){
		int xStart1 = xStart;
		int yStart1 = yStart;
		int xDistance1 = xDistance;
		int yDistance1 = yDistance;
		int xStart2 = x;
		int yStart2 = y;
		int xDistance2 = Main.p.getCOLLISIONDISTANCEX();
		int yDistance2 = Main.p.getCOLLISIONDISTANCEY();
		boolean xResult = false, yResult = false;
		if((xStart1 + xDistance1) >= (xStart2 + xDistance2))
			xResult = (xStart2 + xDistance2) > xStart1;
		else
			xResult = (xStart1 + xDistance1) > xStart2;
		if((yStart1 + yDistance1) >= (yStart2 + yDistance2))
			yResult = (yStart2 + yDistance2) > yStart1;
		else
			yResult = (yStart1 + yDistance1) > yStart2;
		return xResult && yResult;
	}
	
	private static boolean checkSquareToDownwardSlantCollision(){
		int xStart = x % Main.T;
		int yStart = y % Main.T;
		int xEnd = (x + Main.p.getCOLLISIONDISTANCEX()) % Main.T;
		int yEnd = (y + Main.p.getCOLLISIONDISTANCEY()) % Main.T;
		boolean result = false;
		if(xEnd >= yEnd)
			result = yEnd > xStart;
		else
			result = xEnd > yStart;
		//I need to create something that forces the character over in the slant 
		return result;
	}
	/*private static boolean checkCollision(int speed, String direction, int[] hitbox){
		int directionInteger = getDirection(direction);		
		if(speed < 0){
			if(getAndCheckEquation(hitbox[directionInteger] - Main.p.SPEED, hitbox[Math.abs(directionInteger - 1)], directionInteger))
				return true;
		}
		else{
			if(getAndCheckEquation(hitbox[directionInteger] + Main.p.SPEED, hitbox[Math.abs(directionInteger - 1)], directionInteger))
				return true;
		}
		return false;
	}
	private static int getDirection(String s){
		if(s.equalsIgnoreCase("x"))
			return 0;
		return 1;
	}
	private static boolean getAndCheckEquation(int changer, int constant, int order){
		int x,y, originalX, originalY;
		if(order == 0){
			x = changer;
			y = constant;
		}
		else{
			x = constant;
			y = changer;
		}
		if( x < 0 || x >= (Main.currentLocation.w * Main.T) || y < 13 *Main.SCALE  || y >= (Main.currentLocation.h * Main.T))
			return true;
		currentTile = Main.p.getCurrentTile(x, y);
		originalX = x;
		originalY = y;
		x = x % (Main.T);
		y = y % (Main.T);
		//When making types remember that the x-y plane is flipped upside down
		type = Main.currentLocation.lg.map[currentTile[0]][currentTile[1]].getType();
		switch(type){
			case 0: //Open tile
				Main.p.pg.animationSet = 0;
				break;
			case 1: //Full tile	
				return true;
			case 2: //Bottom-Left to Top-Right tile
				if(y == -x + Main.T || y == -x + Main.T + Main.SCALE || y == -x + Main.T - Main.SCALE)
					return true;
				break;
			case 3: //Top-Left to Bottom-Right tile
				if(y == x || y == x + Main.SCALE || y == x - Main.SCALE)
					return true;
				break;
			case 4: //Top tile
				if(y <= 3 * Main.SCALE)
					return true;
				break;
			case 5: //Bottom tile
				if(y >= 14 * Main.SCALE)
					return true;
				break;
			case 6: //Left tile
				if(x <= 3 * Main.SCALE)
					return true;
				break;
			case 7: //Right tile
				if(x >= 14 * Main.SCALE)
					return true;
				break;
			case 10: //Water tile
				if(Main.p.swimGearObtained){
					animationSwitchCount++;
					Main.p.damagePlayer(1);
					break;
				}
				return true;
			case 11: //Left Side Water tile
				if(Main.p.swimGearObtained){
					animationSwitchCount++;
					break;
				}
				else if((!Main.p.swimGearObtained && x < 11 * Main.SCALE))
					break;
				return true;
			case 12: //Right Side Water tile
				if(Main.p.swimGearObtained){
					animationSwitchCount++;
					break;
				}
				else if((!Main.p.swimGearObtained && x > 4 * Main.SCALE))
					break;
				return true;
			case 27: //Bottom-Left to Top-Right tile and Right side
				if((y == -x + Main.T) || x >= 14 * Main.SCALE)
					return true;
				break;
			case 36: //Top-Left to Bottom-Right tile and Left side
				if((y == x) || x <= 3 * Main.SCALE)
					return true;
				break;
			case 90:
				break;
			default: //No type
				System.out.println("Collision error");
				break;
		}
		return checkPlayerCollision(originalX, originalY);
	}*/
}
