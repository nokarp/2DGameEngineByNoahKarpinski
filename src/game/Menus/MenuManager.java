package game.Menus;

import java.awt.image.BufferedImage;
import game.Graphics.ImageHandling;
import game.Main.Main;
import game.Player.Controls;

public class MenuManager extends ImageHandling {
	private KeyItemIcon[][] items;
	private BufferedImage menuBackground, selectionBox;
	private int xPosition, yPosition;
	private boolean moved;
	public MenuManager(){
		items = new KeyItemIcon[3][4];
		items[0][0] = new KeyItemIcon(0, 0, "Swim Gear", "Allows you to swim");
		
		items[0][0].setSelection(true);
		xPosition = 0;
		yPosition = 0;
		moved = false;
		menuBackground = load("/Menu/MenuBox");
		selectionBox = load("/Menu/SelectionBox");
	}
	public void render(){
		Main.g.drawImage(menuBackground, 0, 0, Main.W, Main.H, null);
		for(int y = 0; y < items[0].length; y++){
			for(int x = 0; x < items.length; x++){
				if(items[x][y] != null)
					items[x][y].render();
			}
		}
		Main.g.drawImage(selectionBox, (2 * xPosition * Main.T) + Main.T - (2 * Main.SCALE),
						(2 * yPosition * Main.T) + (2 * Main.T) - (2 * Main.SCALE), Main.T + 4 * Main.SCALE, Main.T + 4 * Main.SCALE, null);
	}
	public void tick(){
		if(!moved && (Controls.up || Controls.down || Controls.left || Controls.right)){
			if(items[xPosition][yPosition] != null)
				items[xPosition][yPosition].setSelection(false);
			moved = true;
			if(Controls.up && yPosition > 0)
				yPosition--;
			if(Controls.down && yPosition < 3)
				yPosition++;
			if(Controls.left && xPosition > 0)
				xPosition--;
			if(Controls.right && xPosition < 2)
				xPosition++;
			if(items[xPosition][yPosition] != null)
				items[xPosition][yPosition].setSelection(true);
		}
	}
	public void enableKeyItem(int x, int y){
		items[x][y].obtain();
	}
	public boolean moved(){
		return moved;
	}
	public void resetMenuMoveOption(){
		moved = false;
	}
}
