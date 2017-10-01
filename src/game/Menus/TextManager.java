package game.Menus;

import game.Graphics.ImageHandling;
import game.Main.Main;

import java.awt.image.BufferedImage;

public class TextManager extends ImageHandling{
	private BufferedImage[][] textBoxes, menuBoxes, letters;
	private BufferedImage[] hearts, numbers, health;
	public BufferedImage[] text;
	private BufferedImage space;
	public int textCount;
	private int soundCount, lastMaxHealth, lastHealth, previousHeartIndex;
	private double healthSplit;
	private final int TEXTSIZE = (int)(.75 * (7 * Main.SCALE));
			//(int)(((Main.SCALE - 1)/2.0)*(Main.TILESIZE) - (Main.SCALE - 1));
	// The 4 might need to be changed if the scale is changed.
	private final int LINELENGTH = (Main.WIDTH / 6) - 4;
	public final int TEXTSPEED = 3;
	private String currentMessage;
	public TextManager(){
		textCount = 0;
		soundCount = 0;
		getNumbers();
		health = new BufferedImage[5];
		lastMaxHealth = Main.p.getMaxHealth();
		lastHealth = Main.p.getHealth();
		healthSplit = Main.p.getMaxHealth() / 20.0;
		setHeartIndex();
		setHeartNumbers();
		textBoxes = new BufferedImage[(Main.WIDTH /Main.TILESIZE) - 1][Main.MENUTILEWIDTH];
		menuBoxes = new BufferedImage[Main.MENUTILEWIDTH][(Main.HEIGHT/Main.TILESIZE)];
		BufferedImage image = Main.im.load("/Menu/TextBox");
		BufferedImage[] subs = new BufferedImage[9];
		currentMessage = "";
		int row, column;
		for(int i = 0; i < 9; i++){
			row = i/3;
			column = i%3;
			subs[i] = image.getSubimage(column * Main.TILESIZE, row * Main.TILESIZE, Main.TILESIZE, Main.TILESIZE);
		}
		for(int y = 0; y < Main.MENUTILEWIDTH; y++){
			for(int x = 0; x < (Main.WIDTH / Main.TILESIZE) - 1; x++){
				if(x == 0 && y == 0)
					textBoxes[x][y] = subs[0];
				else if(x == 0 && y == Main.MENUTILEWIDTH - 1)
					textBoxes[x][y] = subs[6];
				else if(x == 0)
					textBoxes[x][y] = subs[3];
				else if(x == (Main.WIDTH / Main.TILESIZE) - 2 && y == 0)
					textBoxes[x][y] = subs[2];
				else if(y == 0)
					textBoxes[x][y] = subs[1];
				else if(x == (Main.WIDTH / Main.TILESIZE) - 2 && y == Main.MENUTILEWIDTH - 1)
					textBoxes[x][y] = subs[8];
				else if(y == Main.MENUTILEWIDTH - 1)
					textBoxes[x][y] = subs[7];
				else if(x == (Main.WIDTH / Main.TILESIZE) - 2)
					textBoxes[x][y] = subs[5];
				else
					textBoxes[x][y] = subs[4];
			}
		}
		for(int y = 0; y < (Main.HEIGHT/Main.TILESIZE); y++){
			for(int x = 0; x < Main.MENUTILEWIDTH; x++){
				if(y == 0 && x == 0)
					menuBoxes[x][y] = subs[0];
				else if(y == 0 && x == Main.MENUTILEWIDTH - 1)
					menuBoxes[x][y] = subs[2];
				else if(y == 0)
					menuBoxes[x][y] = subs[1];
				else if(y == (Main.HEIGHT/Main.TILESIZE) - 1 && x == 0)
					menuBoxes[x][y] = subs[6];
				else if(y == (Main.HEIGHT/Main.TILESIZE) - 1 && x == Main.MENUTILEWIDTH - 1)
					menuBoxes[x][y] = subs[8];
				else if(y == (Main.HEIGHT/Main.TILESIZE) - 1)
					menuBoxes[x][y] = subs[7];
				else if(x == 0)
					menuBoxes[x][y] = subs[3];
				else if(x == Main.MENUTILEWIDTH - 1)
					menuBoxes[x][y] = subs[5];
				else
					menuBoxes[x][y] = subs[4];
			}
		}
		getLetters();
		getHearts();
		health[2] = numbers[10];
	}
	public void render(){
		for(int y = 0; y < (Main.HEIGHT/Main.TILESIZE); y++){
			for(int x = 0; x < Main.MENUTILEWIDTH; x++){
				Main.g.drawImage(menuBoxes[x][y], Main.W + (x * Main.T), (y * Main.T), Main.T, Main.T, null);
			}
		}
		
		if(!currentMessage.equals("")){
			for(int y = 0; y < Main.MENUTILEWIDTH; y++){
				for(int x = 0; x < (Main.WIDTH /Main.TILESIZE) - 1; x++){
					Main.g.drawImage(textBoxes[x][y], x * Main.T + (int)(0.5 * Main.T), Main.H + (y * Main.T) - ((Main.MENUWIDTH * Main.SCALE) + (int)(0.5 * Main.T)), Main.T, Main.T, null);
				}
			}
			Main.paused = true;
			Main.textPaused = true;
			displayMessageText(currentMessage);
		}
		Main.g.drawImage(hearts[getHeartImage()], Main.W + (int)(1.5 * Main.T) - (11 * Main.SCALE), (int)(0.5 * Main.T), 22 * Main.SCALE, 22 * Main.SCALE, null);
		if(Main.p.getHealth() == lastHealth && Main.p.getMaxHealth() == lastMaxHealth)
			renderHealth();
		else{
			lastMaxHealth = Main.p.getMaxHealth();
			lastHealth = Main.p.getHealth();
			setHeartNumbers();
			renderHealth();
		}
	}
	public BufferedImage[] formatNumbers(int num){
		BufferedImage[] temp = new BufferedImage[getDigits(num)];
		int number = num;
		for(int i = 0; i < temp.length; i++){
			temp[temp.length - 1 - i] = numbers[number % 10];
			number /= 10;
		}
		return temp;
	}
	private int getDigits(int num){
		int amount = 0;
		int number = num;
		while(number > 0){
			amount++;
			number /= 10;
		}
		return amount;
	}
	private void setHeartNumbers() {
		health[0] = numbers[lastHealth / 10];
		health[1] = numbers[lastHealth % 10];
		health[3] = numbers[lastMaxHealth / 10];
		health[4] = numbers[lastMaxHealth % 10];
	}
	private int getHeartImage() {
		healthSplit = Main.p.getMaxHealth() / 20.0;
		if(!(Main.p.getHealth() == lastHealth))
			setHeartIndex();
		if(previousHeartIndex < 0)
			previousHeartIndex++;
		return previousHeartIndex;
	}
	private void setHeartIndex() {
		previousHeartIndex = (int)(Math.ceil((Main.p.getMaxHealth() - Main.p.getHealth()) / healthSplit))-1;
	}
	private void renderHealth(){
		for(int i = 0; i < health.length; i++)
			Main.g.drawImage(health[i], Main.W + Main.T + (int)(7 * .5 * Main.SCALE) * i, Main.T - Main.SCALE, (int)(5 * .5 * Main.SCALE), (int)(9 * .5 * Main.SCALE), null);
	}
	public void displayMessageText(String s){
		text = formatString(s);
		if(!((textCount / TEXTSPEED) == text.length)){
			soundCount++;
			if(soundCount == 6){
				Main.mp.playOnce("typing");
				soundCount = 0;
			}
		}
		if((textCount / TEXTSPEED) < text.length)
			textCount++;
		int line = 1, lineCount = 0, tempCount;
		for(int i = 0; i < textCount / TEXTSPEED; i++){
			tempCount = lineCount;
			while(i + (tempCount - lineCount) < text.length && !text[i + (tempCount - lineCount)].equals(space))
				tempCount++;
			if(tempCount >= LINELENGTH){
				lineCount = 0;
				line++;
			}
			Main.g.drawImage(text[i], (Main.T) + ((lineCount) * 6 * Main.SCALE),
							(int)((line) * (8 * Main.SCALE)) + (Main.HEIGHT * Main.SCALE) - ((Main.MENUWIDTH * Main.SCALE) + (8 * Main.SCALE)),
							TEXTSIZE, TEXTSIZE, null);
			lineCount++;
		}
	}
	public void displayMenuText(){
		
	}
	public BufferedImage[] formatString(String t){
		String sub;
		int letterValue;
		BufferedImage phrase[] = new BufferedImage[t.length()];
		for(int i = 0; i < t.length(); i++){
			sub = t.substring(i, i+1);
			sub = sub.toLowerCase();
			if(!(sub.compareTo("a") < 0)){
				letterValue = sub.compareTo("a");
				phrase[i] = letters[letterValue%6][letterValue/6];
			}
			else{
				if(sub.equals("."))
					phrase[i] = letters[2][4];
				else if(sub.equals("?"))
					phrase[i] = letters[3][4];
				else if(sub.equals("!"))
					phrase[i] = letters[4][4];
				else if(sub.equals(" "))
					phrase[i] = letters[5][4];
				else
					phrase[i] = letters[5][4];
			}
		}
		return phrase;
	}
	public String getCurrentMessage(){
		return currentMessage;
	}
	public void setCurrentMessage(String message){
		currentMessage = message;
	}
	public void clearMessage(){
		Main.paused = false;
		Main.textPaused = false;
		textCount = 0;
		currentMessage = "";
	}
	private void getLetters(){
		letters = new BufferedImage[6][5];
		BufferedImage image = Main.im.load("/Menu/Letters");
		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 6; x++){
				letters[x][y] = image.getSubimage(x * 8, y * 8, 7, 7);
			}
		}
		space = letters[5][4];
	}
	private void getHearts() {
		hearts = new BufferedImage[20];
		BufferedImage image = Main.im.load("/Menu/Hearts");
		for(int i = 0; i < hearts.length; i++)
			hearts[i] = image.getSubimage((i % 5) * 23, (i / 5) * 23, 22, 22);
	}
	private void getNumbers() {
		numbers = new BufferedImage[12];
		BufferedImage image = Main.im.load("/Menu/Numbers");
		for(int i = 0; i < numbers.length; i++){
			numbers[i] = image.getSubimage((i % 4) * 6, (int)(i / 4) * 10, 5, 9);
		}
	}
}
