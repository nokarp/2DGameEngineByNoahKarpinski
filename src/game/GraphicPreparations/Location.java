package game.GraphicPreparations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import game.Graphics.LocationGraphics;
import game.Main.Main;
import game.Tile.Tile;

public class Location {
	public int w,h, tileMapW, tileMapH, tileSet;
	private String currentLocation, song;
	public LocationGraphics lg;
	public LocationSprites ls;
	private ArrayList<ArrayList<String>> locations = new ArrayList<ArrayList<String>>();
	private BufferedImage[][] mapTiles;
	public Location(String mapName, String songName){
		currentLocation = mapName;
		song = songName;
		if(!song.equals(""))
			Main.mp.playLoop(song);
		lg = new LocationGraphics();
		File f = new File(getCurrentLocation() + "-Tiles");
		locations = getTileIDs(f);
		String tileSetString;
		if(tileSet < 10)
			tileSetString = "0" + tileSet;
		else
			tileSetString = String.valueOf(tileSet);
		mapTiles = Main.im.cropSetTiles("/TileSets/TileSet" + tileSetString, tileMapW, tileMapH);	
		lg.map = new Tile[w][h];
		lg.map = createMap();
		ls = new LocationSprites(currentLocation, w, h);
	}
	
	//Returns file address of the current location
	public String getCurrentLocation(){
		return "Resources/encodedMaps/" + currentLocation;
	}
	
	/*Retrieves the tile identification numbers from the file f and stores them in a 2D array list. Also, the 
	 *height and width of the map in terms of tiles is stored in h and w respectively.
	 */
	@SuppressWarnings("resource")
	public ArrayList<ArrayList<String>> getTileIDs(File f){
		RandomAccessFile r = null;
		try {
			r = new RandomAccessFile(f, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		long line = 0;
		String lineText = "";
		boolean complete = false;
		ArrayList<ArrayList<String>> a = new ArrayList<ArrayList<String>>();
		int lineCount = 0;
		@SuppressWarnings("unused")
		String arrayPart = "";
		while(!complete){
			try {
				line = r.getFilePointer();
				lineText = r.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(lineText == null || lineText.equals(""))
				complete = true;
			else{
				if(line == 0){
					String[] mapInfo = lineText.split("-");
					tileMapW = Integer.parseInt(mapInfo[0]);
					tileMapH = Integer.parseInt(mapInfo[1]);
					tileSet = Integer.parseInt(mapInfo[2]);
				}
				else{
					lineText = lineText.replaceAll(" ", "");
					String [] z = lineText.split(",");
					for(int i = 0; i < z.length; i++)
						a.add(formList(z[i]));
					lineCount++;
				}
			}
		}
		h = lineCount;
		w = a.size() / h;
		return a;
		
	}
	
	//Splits the the tile identification numbers into an array list by the dashes.
	public ArrayList<String> formList(String s){
		String[] sArray = s.split("-");
		ArrayList<String> a = new ArrayList<String>();
		for(int i = 0; i < sArray.length; i++)
			a.add(sArray[i]);
		return a;
	}
	
	//Creates the map of the tiles in a 2d array of tiles.
	public Tile[][] createMap(){
		Tile[][] t = new Tile[w][h];
		ArrayList<String> s = new ArrayList<String>();
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){
				s = locations.get(x + (y * w));
				t[x][y] = new Tile(mapTiles[getX(s)][getY(s)], getType(s));
			}
		}
		return t;
	}
	public void playLocationSong(){
		Main.mp.playLoop(song);
	}
	
	public int getX(ArrayList<String> a){
		return Integer.parseInt((String)(a.get(0)));
	}
	public int getY(ArrayList<String> a){
		return  Integer.parseInt((String)(a.get(1)));
	}
	public int getType(ArrayList<String> a){
		return  Integer.parseInt((String)(a.get(2)));
	}
	public String getName(){
		return currentLocation;
	}
}
