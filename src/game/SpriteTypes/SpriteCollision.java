package game.SpriteTypes;

import game.Main.Main;

public class SpriteCollision {	
		public static boolean collision(int xTile, int yTile){
			int type = Main.currentLocation.lg.map[xTile][yTile].getType();
			switch(type){
				case 0: //Open tile
					return false;
				case 1: //Full tile	
					return true;
				case 2: //Bottom-Left to Top-Right tile
					return false;
				case 3: //Top-Left to Bottom-Right tile
					return false;
				case 4: //Top tile
					return true;
				case 5: //Bottom tile
					return true;
				case 6: //Left tile
					return true;
				case 7: //Right tile
					return true;
				case 10: //Water tile
					return true;
				case 27: //Bottom-Left to Top-Right tile and Right side
					return true;
				case 36: //Top-Left to Bottom-Right tile and Left side
					return true;
				default: //No type
					System.out.println("Collision error");
					return false;
			}
		}
}
