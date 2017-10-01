package game.Player;

import java.awt.image.BufferedImage;

import game.Graphics.PlayerGraphics;
import game.Main.Main;

public class Player {
	private final int SPEED = Main.SCALE;
	private int xSpeed = SPEED;
	private int ySpeed = SPEED;
	private int health, maxHealth, invincibleCount, centerCollisionX, centerCollisionY;
	private int x, y, actualX, actualY, xStart, yStart; 
	private final int COLLISIONSTARTX = 1 * Main.SCALE, COLLISIONSTARTY = 13 * Main.SCALE, 
			COLLISIONDISTANCEX = 13 * Main.SCALE, COLLISIONDISTANCEY = 2 * Main.SCALE;
	private final int PLAYERHEIGHT = 16, PLAYERWIDTH = 16;
	private final int COLLISIONSIZEX = (int)((COLLISIONDISTANCEX + 1) / Main.T) + 1;
	private final int COLLISIONSIZEY = (int)((COLLISIONDISTANCEY + 1) / Main.T) + 1;
	private int[] currentTile;
	public boolean swimGearObtained, invincible;
	public BufferedImage playerImage;
	public PlayerGraphics pg = new PlayerGraphics();
	public Player(){
		maxHealth = 10;
		health = maxHealth;
		invincibleCount = 0;
		playerImage = pg.updatePlayerGraphics();
		currentTile = new int[2];
		currentTile[0] = 0;
		currentTile[1] = 0;
		x = findX(0,Main.WIDTH);
		y = findY(0,Main.HEIGHT);
		actualX = 0 * Main.T;
		actualY = 0 * Main.T;
	}
	public void tick(){	
		//Gradual health increase should be in tick so that it does not happen when the game is paused.
		if(Main.currentLocation.lg.map[Main.p.getCurrentXTile()][Main.p.getCurrentYTile()].getType() == 10)
			Main.p.pg.setAnimationSet(1);
		else
			Main.p.pg.setAnimationSet(0);
		
		if(invincible){
			invincibleCount++;
			if(invincibleCount == Main.FPS){
				invincibleCount = 0;
				invincible = false;
			}	
		}
		if(Controls.up && !Collision.collision("y", -1 * SPEED)){
			actualY -= SPEED;
			y -= ySpeed;
		}
		if(Controls.down && !Collision.collision("y", SPEED)){
			actualY += SPEED;
			y += ySpeed;
		}
		if(Controls.left && !Collision.collision("x", -1 * SPEED)){
			actualX = actualX - SPEED;
			x -= xSpeed;
		}
		if(Controls.right && !Collision.collision("x", SPEED)){
			actualX = actualX + SPEED;
			x += xSpeed;
		}
		if(!Controls.up && !Controls.down && !Controls.left && !Controls.right)
			Collision.collision("p", 0);
		playerImage = pg.updatePlayerGraphics();
		currentTile = getCurrentTile(Main.p.getActualX() + (((int)(.5 * PLAYERWIDTH) - 1) * Main.SCALE), Main.p.actualY + (((int)(.5 * PLAYERHEIGHT) - 1) * Main.SCALE));
		//System.out.println(Main.p.xSPEED);
		//System.out.println(x + " " + y);
		//System.out.println(actualX + " " + actualY);
	}
	public void render(){
		Main.g.drawImage(playerImage, x, y, Main.T, Main.T, null);	
	}
	
	public void updateHitboxs(){
		xStart = actualX + COLLISIONSTARTX;
		yStart = actualY + COLLISIONSTARTY;
		centerCollisionX = (int) (actualX + COLLISIONSTARTX + (.5 * COLLISIONDISTANCEX));
		centerCollisionY = (int) (actualY + COLLISIONSTARTY + (.5 * COLLISIONDISTANCEY));
	}
	public int[] getCurrentTile(int x1, int y1){
		int[] x = {x1/Main.T, y1/Main.T};
		return x;
	}
	public int findX(int tileX, int width){
		if(tileX - (int)(.5 * (Main.WIDTH / Main.TILESIZE)) > 0 && tileX + (int)(.5 * (Main.WIDTH / Main.TILESIZE)) < width-1)
			return (int)(.5 * (Main.WIDTH / Main.TILESIZE)) * Main.T;
		return tileX * Main.T;
	}
	public int findY(int tileY, int height){
		if(tileY - (int)(.5 * (Main.HEIGHT / Main.TILESIZE)) > 0 && tileY + (int)(.5 * (Main.HEIGHT / Main.TILESIZE)) < height-1)
			return (int)(.5 * (Main.HEIGHT / Main.TILESIZE)) * Main.T;
		return tileY * Main.T;
	}
	public void initializeNewLocation(int tileX, int tileY){
		x = findX(tileX, Main.currentLocation.w);
		y = findY(tileY, Main.currentLocation.h);
		actualX = tileX * Main.T;
		actualY = tileY * Main.T;
	}
	public void damagePlayer(int damage){
		if(!invincible){
			Main.mp.playOnce("playerGrunt");
			health -= damage;
			if(health <= 0){
				health = 0;
				System.exit(0);
			}
			else{
				invincible = true;
			}
		}
	}
	public int getHealth(){
		return health;
	}
	public int getMaxHealth(){
		return maxHealth;
	}
	public int getCurrentXTile(){
		return currentTile[0];
	}
	public int getCurrentYTile(){
		return currentTile[1];
	}
	public int getPlayerHeight(){
		return PLAYERHEIGHT;
	}
	public int getPlayerWidth(){
		return PLAYERWIDTH;
	}
	public int getCenterCollisionX() {
		return centerCollisionX;
	}
	public int getCenterCollisionY() {
		return centerCollisionY;
	}
	public int getActualX() {
		return actualX;
	}
	public void setActualX(int value){
		actualX = value;
	}
	public int getActualY(){
		return actualY;
	}
	public void setActualY(int value){
		actualY = value;
	}
	public int getX(){
		return x;
	}
	public void setX(int value){
		x = value;
	}
	public int getY(){
		return y;
	}
	public void setY(int value){
		y = value;
	}
	public int getXStart(){
		return xStart;
	}
	public int getYStart(){
		return yStart;
	}
	public int getCOLLISIONSIZEX(){
		return COLLISIONSIZEX;
	}
	public int getCOLLISIONSIZEY(){
		return COLLISIONSIZEY;
	}
	public int getCOLLISIONDISTANCEX(){
		return COLLISIONDISTANCEX;
	}
	public int getCOLLISIONDISTANCEY(){
		return COLLISIONDISTANCEY;
	}
	public int getCOLLISIONSTARTX(){
		return COLLISIONSTARTX;
	}
	public int getCOLLISIONSTARTY(){
		return COLLISIONSTARTY;
	}
	public int getSPEED(){
		return SPEED;
	}
	public int getXSpeed(){
		return xSpeed;
	}
	public void setXSpeed(int speed){
		xSpeed = speed;
	}
	public int getYSpeed(){
		return ySpeed;
	}
	public void setYSpeed(int speed){
		ySpeed = speed;
	}
}
