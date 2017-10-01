package game.Graphics;

import game.Main.Main;
import game.Player.Controls;

import java.awt.image.BufferedImage;

public class PlayerGraphics extends ImageHandling{
	private int x, y, lastY, animationCount;
	private int animationSet;
	private BufferedImage playerSheet;
	private BufferedImage playerGraphics[][];
	public PlayerGraphics(){
		x = 0;
		y = 0;
		lastY = 1;
		playerSheet = load("/Player/PlayerSprites");
		playerGraphics = new BufferedImage[playerSheet.getWidth()/ (Main.TILESIZE + 1)][playerSheet.getHeight()/ (Main.TILESIZE + 1)]; 
		for(int y = 0; y < playerSheet.getHeight()/ (Main.TILESIZE + 1);y++){
			for(int x = 0; x < playerSheet.getWidth()/ (Main.TILESIZE + 1);x++){
				playerGraphics[x][y] = cropWithSpaces(x, y, playerSheet);
			}
		}
		//System.out.println(playerSheet.getHeight()/ (Main.TILESIZE + 1) + " " + playerSheet.getWidth()/ (Main.TILESIZE + 1));
	}
	public BufferedImage updatePlayerGraphics(){
		getDirection();
		return playerGraphics[x][y];
	}
	public void getDirection(){
		if((Controls.up && !Controls.down && !Controls.left && !Controls.right) || (Controls.up && !Controls.down && Controls.left && Controls.right)){
			y = 0;
		}
		else if(!Controls.up && Controls.down && !Controls.left && !Controls.right || (!Controls.up && Controls.down && Controls.left && Controls.right)){
			y = 1;
		}
		else if(!Controls.up && !Controls.down && Controls.left && !Controls.right || (Controls.up && Controls.down && Controls.left && !Controls.right)){
			y = 2;
		}
		else if(!Controls.up && !Controls.down && !Controls.left && Controls.right || (Controls.up && Controls.down && !Controls.left && Controls.right)){
			y = 3;
		}
		else if(Controls.up && !Controls.down && Controls.left && !Controls.right){
			y = 2;
		}
		else if(Controls.up && !Controls.down && !Controls.left && Controls.right){
			y = 3;
		}
		else if(!Controls.up && Controls.down && Controls.left && !Controls.right){
			y = 2;
		}
		else if(!Controls.up && Controls.down && !Controls.left && Controls.right){
			y = 3;
		}
		else{
			y = lastY;
			y -= (animationSet * 4);
		}
		y += (animationSet * 4);
		if(((Controls.up && Controls.down && Controls.left && Controls.right) || (!Controls.up && !Controls.down && !Controls.left && !Controls.right)
				|| (!Controls.up && !Controls.down && Controls.left && Controls.right) || (Controls.up && Controls.down && !Controls.left && !Controls.right)) && animationSet == 0)
			x = 0;
		else
			x = animation();
		lastY = y;
	}
	public int animation(){
		if(y == lastY){
			//Regular walking
			if(animationSet == 0){
				if(animationCount == 30)
					animationCount = 0;
				animationCount++;
				if(animationCount <=15)
					return 1;
				else
					return 2;
			}
			//Swimming
			else{
				if(animationCount == 30)
					animationCount = 0;
				animationCount++;
				if(animationCount <=10)
					return 0;
				else if(animationCount <= 20)
					return 1;
				else
					return 2;
			}
		}
		else{
			animationCount = 0;
		}
		return 0;
	}
	
	public void setAnimationSet(int num){
		animationSet = num;
	}
}
