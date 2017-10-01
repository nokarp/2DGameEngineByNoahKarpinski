package game.Sprites;

import game.Main.Main;
import game.SpriteTypes.Enemy;

public class GiantRat extends Enemy{
	private int animationCount, pause;
	private boolean hitInvincible;
	public GiantRat(int xPos, int yPos) {
		super(xPos, yPos, 17, 17, 3, 4, "GiantRat", 10);
		animationCount = 0;
		animationPoint[0] = 0;
		animationPoint[1] = 0;
	}
	public void tick() {
		if(hitInvincible){
			pause++;
			if(pause == 60){
				hitInvincible = false;
				pause = 0;
			}
		}
		if(Main.p.getCurrentXTile() == xTile && !hitInvincible){
			hitInvincible = true;
			damageEnemy(1);
		}
		animationCount++;
		if(animationCount <= 10)
			animationPoint[0] = 0;
		else if(animationCount <= 20)
			animationPoint[0] = 1;
		else if(animationCount <= 30)
			animationPoint[0] = 2;
		else if(animationCount <= 40)
			animationPoint[0] = 1;
		else{
			animationPoint[0] = 0;
			if(animationCount == 50){
				animationCount = 0;
			}
		}
	}
}
