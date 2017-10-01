package game.Others;

import game.Main.Main;

import java.awt.image.BufferedImage;

public class DamageNumbers {
	private BufferedImage[] numbers;
	private int count;
	private boolean complete;
	public DamageNumbers(int number){
		numbers = Main.tm.formatNumbers(number);
		count = 0;
		complete = false;
	}
	public void render(int index, int x, int y, int xAdjustment, int yAdjustment, int xSize){
		count++;
		for(int i = 0; i < numbers.length; i++)
			Main.g.drawImage(numbers[i], x + (int)((xSize * .5) - ((.25 * 6 * Main.SCALE) * (numbers.length)) + (.5 * 6 * Main.SCALE * i)) + xAdjustment,
							y - (int)(9 * .5 * Main.SCALE) + yAdjustment - (int)((getXPosition()) * Main.SCALE), (int)(5 * .5 * Main.SCALE), (int)(9 * .5 * Main.SCALE), null);
		if(count == 45)
			complete = true;
	}
	private int getXPosition(){
		if(count <= 30)
			return count / 3;
		else
			return 10;
	}
	public boolean isComplete(){
		return complete;
	}
}
