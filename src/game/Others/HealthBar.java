package game.Others;

import java.awt.Color;

import game.Main.Main;

public class HealthBar {
	private int xSize, maxHealth, greenLength;
	public HealthBar(int xSize, int maxHealth){
		this.xSize = xSize * Main.SCALE;
		this.maxHealth = maxHealth;
	}
	public void render(int xPosition, int yPosition, int xAdjustment, int yAdjustment, int health){
		if(yPosition > 4 * Main.SCALE){
			greenLength = (int)(((double)(health) / maxHealth) * xSize);
			Main.g.setColor(Color.GREEN);
			Main.g.fillRect(xPosition + xAdjustment, yPosition - (4 * Main.SCALE) + yAdjustment, greenLength, 2 * Main.SCALE);
			Main.g.setColor(Color.RED);
			Main.g.fillRect(xPosition + xAdjustment + greenLength, yPosition - (4 * Main.SCALE) + yAdjustment, xSize - greenLength, 2 * Main.SCALE);
			Main.g.setColor(Color.WHITE);
			Main.g.drawRect(xPosition + xAdjustment - 1, yPosition - (4 * Main.SCALE) + yAdjustment - 1, xSize + 1, (2 * Main.SCALE) + 1);
		}
	}
}
