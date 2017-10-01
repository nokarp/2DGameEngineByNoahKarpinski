package game.Menus;

import game.Main.Main;

import java.awt.image.BufferedImage;

public class KeyItemIcon {
	private BufferedImage[] itemName, itemDescription;
	private BufferedImage image;
	private int xPosition, yPosition, itemNameOffset, itemDescriptionOffset;
	private boolean obtained, selected;
	public KeyItemIcon(int x, int y, String name, String description){
		xPosition = x;
		yPosition = y;
		obtained = false;
		selected = false;
		image = Main.im.crop(0, 0, Main.im.load("/Sprites/" + name.replaceAll(" ", "")));
		itemName = Main.tm.formatString(name);
		itemNameOffset = (int)((.5 * itemName.length) * (8 * Main.SCALE));
		itemDescription = Main.tm.formatString(description);
		itemDescriptionOffset = (int)((.5 * itemDescription.length) * (8 * .5 * Main.SCALE));
	}
	public void render(){
		if(obtained){
			//Renders the image of the item in the appropriate location
			Main.g.drawImage(image, (2 * xPosition * Main.T) + Main.T, (2 * yPosition * Main.T) + (2 * Main.T), Main.T, Main.T, null);
			if(selected){
				//Renders the name of the item in the text box on the pause menu
				for(int i = 0; i < itemName.length; i++)
					Main.g.drawImage(itemName[i], (int)((10 * Main.T) + (2.5 * Main.SCALE) - itemNameOffset) + ((8 * Main.SCALE) * i), 
									(8 * Main.T) - (3 * Main.SCALE), 7 * Main.SCALE, 7 * Main.SCALE, null);
				//Renders the description of the item in the text box on the pause menu
				for(int i = 0; i < itemDescription.length; i++)
					Main.g.drawImage(itemDescription[i], (int)((10 * Main.T) + (2.5 * Main.SCALE) - itemDescriptionOffset) + (int)((8 * .5 * Main.SCALE) * i), 
									(8 * Main.T) + (10 * Main.SCALE), (int)(7 * .5 * Main.SCALE), (int)(7 * .5 * Main.SCALE), null);
			}
		}	
	}
	public void obtain(){
		obtained = true;
	}
	public void setSelection(boolean state){
		selected = state;
	}
}
