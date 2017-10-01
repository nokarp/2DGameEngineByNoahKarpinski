package game.SpriteTypes;

import java.util.ArrayList;

import game.Others.DamageNumbers;
import game.Others.HealthBar;

public abstract class Enemy extends Sprite{
	protected int health, maxHealth, xTile, yTile;
	protected boolean engaged;
	public ArrayList<DamageNumbers> damageNumbers;
	private HealthBar healthBar;
	public abstract void tick();
	public Enemy(int xPos, int yPos, int xSize, int ySize, int animationSizeX, int animationSizeY, String sheetName, int maxHealth) {
		super(xPos, yPos, xSize, ySize, animationSizeX, animationSizeY, sheetName);
		xTile = xPos;
		yTile = yPos;
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		engaged = false;
		healthBar = new HealthBar(xSize, maxHealth);
		damageNumbers = new ArrayList<DamageNumbers>(); 	
	}
	public void render(int x, int y, int xAdjustment, int yAdjustment){
		super.render(xAdjustment, yAdjustment);
	}
	public void renderHealthBarAndDamage(int xAdjustment, int yAdjustment){
		healthBar.render(this.x, this.y, xAdjustment, yAdjustment, health);
		for(int i = 0; i < damageNumbers.size(); i++){
			damageNumbers.get(i).render(i, this.x, this.y, xAdjustment, yAdjustment, xSize);
			if(damageNumbers.get(i).isComplete()){
				damageNumbers.remove(i);
				i--;
			}
		}
	}
	public void damageEnemy(int damage){
		health -= damage;
		if(health <= 0)
			deactivate();
		else
			damageNumbers.add(new DamageNumbers(damage));
	}
	public boolean playerCollision(int playerX, int playerY) {
		return false;
	}
}
