       package game.GraphicPreparations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import game.Player.Controls;
import game.SpriteTypes.Sprite;
import game.Sprites.GiantRat;
import game.Sprites.Guy01;
import game.Sprites.JumpingKid;
import game.Sprites.LocationChange;
import game.Sprites.Sign;
import game.Sprites.SwimGear;

public class LocationSprites {
	public ArrayList<Sprite> sprites;
	public LocationSprites(String currentLocation, int w, int h){
		sprites = getSprites("Resources/EncodedMaps/" + currentLocation + "-Sprites", w, h);
	}
	@SuppressWarnings("resource")
	private ArrayList<Sprite> getSprites(String path, int w, int h) {
		ArrayList<Sprite> temp = new ArrayList<Sprite>();
		File f = new File(path);
		RandomAccessFile r = null;
		try {
			r = new RandomAccessFile(f, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String lineText = "";
		String[] lineSplit;
		boolean complete = false;
		while(!complete){
			try {
				lineText = r.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(lineText == null || lineText.equals(""))
				complete = true;
			else{
				lineSplit = lineText.split(",");
				temp.add(getSpriteType(lineSplit));
			}
		}
		return temp;
	}
	
	private Sprite getSpriteType(String[] lineSplit){
		switch(lineSplit[0]){
			case "SwimGear":
				return new SwimGear(Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]));
			case "Guy01":
				return new Guy01(Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]));
			case "LocationChange":
				return new LocationChange(Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]), lineSplit[3], lineSplit[4], Integer.parseInt(lineSplit[5]), Integer.parseInt(lineSplit[6]));
			case "JumpingKid":
				return new JumpingKid(Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]));
			case "Sign":
				return new Sign(Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]), lineSplit[3]);
			case "GiantRat":
				return new GiantRat(Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]));
			default:
				System.out.println("No Sprite of type " + lineSplit[0] + " found.");
				return null;
		}
	}
	public void prepareSprites(){
		for(int i = 0; i < sprites.size(); i++){
			if(sprites.get(i) != null && sprites.get(i).isActive())
					sprites.get(i).tick();
		}
		Controls.talking = false;
		Sprite temp;
		//Organizes sprites in order of ascending center y position
		for(int j = 0; j < sprites.size()-1; j++){
			for(int i = j+1; i < sprites.size(); i++){
				if(sprites.get(i).getCenterCollisionY() < sprites.get(j).getCenterCollisionY()){
					temp = sprites.get(i);
					sprites.set(i, sprites.get(j));
					sprites.set(j, temp);
				}
				// It might be necessary to check x position if they have the same y position
			}
		}
		//Potentially write to check visibility
	}
	
}
