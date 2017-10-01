
package game.Main;

import game.GraphicPreparations.Location;
import game.Graphics.ImageHandling;
import game.Graphics.LocationGraphics;
import game.Menus.MenuManager;
import game.Menus.TextManager;
import game.Player.Controls;
import game.Player.Player;
import game.Sounds.MusicPlayer;
import game.Sprites.LocationChange;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends Canvas implements Runnable, WindowListener{

	/* Bug List:
	 * 
	 * When the game first starts up. The first transition will have a lot of lag. I think this may be due to the audio.
	 * It could also be because of storing a location in the locations array list.
	 * 
	 * Right collision is different from left collision.
	 * Collision for the 4 point: Right = 100001000010000 and Left = 101010101010101010
	 * 
	 * One time, for some reason the background graphics were jumpy. After I closed eclipse they were fine. My only thought is
	 * that there were multiple screens open or the map encoder was open.
	 * 
	 * 
	 * 
	 * FIXED: When the player changes from corner to a side or middle, when returning the actual x/y and x/y
	 * are off by the speed each time it is done.
	 */
	
	/* Assumptions:
	 * 
	 * Each location will have a unique name.
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Both the width and the height have be even when divided by the tile size. And, of course have no remainder.
	public static final int WIDTH = 224;
	public static final int HEIGHT = 160;
	public static final int TILESIZE = 16;
	public static final int MENUTILEWIDTH = 3;
	public static final int MENUWIDTH = MENUTILEWIDTH * TILESIZE;
	public static final int SCALE = getScale();
	public static final int FPS = 60;
	public static final int W = WIDTH * SCALE;
	public static final int H = HEIGHT * SCALE;
	public static final int T = TILESIZE * SCALE;
	
	public static boolean paused = false, menuOpen = false, transition = false, beginningTransition = false, textPaused = false;
	public static int transitionCount = 29;
	public static BufferedImage[] transitions, bottomPixels;
	public static LocationChange locationChange;
	private boolean running = false;
	public Thread thread;
	// The 6 and 28 account for the sections outside of the window.
	public static Dimension d = new Dimension(W + (MENUWIDTH * SCALE) + 6, H + 28);
	public static BufferStrategy b;
	
	public static Player p;
	public static ArrayList<Location> locations;
	public static Location currentLocation;
	public static LocationGraphics lg;
	public static Graphics g;
	public static ImageHandling im;
	public static TextManager tm;
	public static MenuManager mm;
	public static MusicPlayer mp;
	public static JFrame frame;
	public static JPanel panel;

	public static void main(String[] args) {
		Main game = new Main();
		game.setPreferredSize(d);
		game.setMinimumSize(d);
		game.setMaximumSize(d);
		frame = new JFrame();
		panel = new JPanel();
		frame.setTitle("Game V0.02");
		frame.setMinimumSize(d);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.add(game);
		frame.requestFocus();
		frame.setVisible(true);
		game.start();	
	}
	private static int getScale() {
		int min = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / (WIDTH + MENUWIDTH));
		int h = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / HEIGHT);
		if(min > h)
			min = h;
		return min;
	}
	public synchronized void start(){
		
		if(running)
			return;
		initialize();
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public void initialize(){
		frame.addWindowListener(this);
		im = new ImageHandling();
		p = new Player();
		mp = new MusicPlayer();
		bottomPixels = im.cropSet("/Other/BottomLevelColors", 10);
		currentLocation = new Location("TestMap", "Forest");
		locations = new ArrayList<Location>();
		locations.add(currentLocation);
		tm = new TextManager();
		mm = new MenuManager();
		this.addKeyListener(new Controls());
		transitions = im.cropSet("/Transitions/WhiteTransition",30);
	}
	public void run() {
		long newTime, lastTime, time1, time2, time3, time4;
		double deltaTime = 0;
		newTime = System.nanoTime();
		while(running){
			lastTime = newTime;
			newTime = System.nanoTime();
			deltaTime += (newTime - lastTime)/1000000000.0;
			if(deltaTime >= 1.0/FPS){
				deltaTime -= (1.0/FPS);	
				time1 = System.nanoTime();
				tick();
				time2 = System.nanoTime() - time1;
				System.out.println("Tick Time: " + time2);
				time3 = System.nanoTime();
				render(time3);  
				time4 = System.nanoTime() - time3;
				System.out.println("Render Time: " + time4);
			}
		}
		stop();	
	}
	public void render(long initialTime){
		b = this.getBufferStrategy();
		if(b == null){
			createBufferStrategy(3);
			return;
		}
		long ptime = System.nanoTime();
		long time = ptime - initialTime;
		System.out.println("Buffer Strategy: " + time);
		g = b.getDrawGraphics();
		g.fillRect(0, 0, W + (MENUWIDTH * SCALE), H + (MENUWIDTH * SCALE));
		time = System.nanoTime() - ptime;
		System.out.println("getDrawGraphics: " + time);
		ptime = System.nanoTime();
		//currentLocation.lg.render(Main.currentLocation.w, Main.currentLocation.h);
		time = System.nanoTime() - ptime;
		System.out.println("Standard Render: " + time);
		ptime = System.nanoTime();
		currentLocation.lg.renderHealthBarsAndDamage();
		currentLocation.lg.renderAboveLayer();
		time = System.nanoTime() - ptime;
		System.out.println("Render Health and Above Layer: " + time);
		ptime = System.nanoTime();
		renderMenus();
		time = System.nanoTime() - ptime;
		System.out.println("Render Menus: " + time);
		ptime = System.nanoTime();
		if(transition)
			transition();
		time = System.nanoTime() - ptime;
		System.out.println("Transition: " + time);
		ptime = System.nanoTime();
		g.dispose();
		time = System.nanoTime() - ptime;
		System.out.println("Dispose: " + time);
		ptime = System.nanoTime();
		b.show();
		time = System.nanoTime() - ptime;
		System.out.println("Show: " + time);
	}
	public void renderMenus(){
		tm.render();
		if(menuOpen)
			mm.render();		
	}
	public void tick(){
		if(!paused){
			p.tick();
			currentLocation.ls.prepareSprites();
		}
		if(menuOpen)
			mm.tick();
	}
	private void transition() {
		
		if(transitionCount == 29 && !beginningTransition){
			transition = false;
			paused = false;
		}
		else if(transitionCount == -10){
			g.drawImage(transitions[0], 0, 0, W, H, null);
			beginningTransition = false;
			transitionCount = 1;
		}	
		else if(transitionCount == 0){
			mp.close();                      
			if(findLocation() == -1){
				currentLocation = new Location(locationChange.getLocationName(), locationChange.getSong());
				p.initializeNewLocation(locationChange.getTileX(), locationChange.getTileY());
				locations.add(currentLocation);
			}
			else{
				currentLocation = locations.get(findLocation());
				currentLocation.playLocationSong();
				p.initializeNewLocation(locationChange.getTileX(), locationChange.getTileY());
			}
			g.drawImage(transitions[transitionCount], 0, 0, W, H, null);
			transitionCount--;
		}
		else if(beginningTransition){
			int count;
			if(transitionCount < 0)
				count = 0;
			else
				count = transitionCount;
			g.drawImage(transitions[count], 0, 0, W, H, null);
			transitionCount--;
		}
		else{
			g.drawImage(transitions[transitionCount], 0, 0, W, H, null);
			transitionCount++;
		}
	}
	private int findLocation() {
		int index = -1;
		for(int i = 0; i < locations.size(); i++){
			if(locations.get(i).getName().equals(locationChange.getLocationName())){
				index = i;
				break;
			}
		}
		return index;
	}
	public synchronized void stop(){
		if(!running)
			return;
		running = false; 
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public void windowActivated(WindowEvent arg0) {
	}
	public void windowClosed(WindowEvent arg0) {
	}
	public void windowClosing(WindowEvent arg0) {
		mp.closeStream();
		//JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?");
	}
	public void windowDeactivated(WindowEvent arg0) {
	}
	public void windowDeiconified(WindowEvent arg0) {
	}
	public void windowIconified(WindowEvent arg0) {
	}
	public void windowOpened(WindowEvent arg0) {	
	}
}
