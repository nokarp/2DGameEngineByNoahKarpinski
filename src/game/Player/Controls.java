package game.Player;

import game.Main.Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controls implements KeyListener{
	public static boolean up, down, left, right, talking, actionTriggered;
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
			up = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			down = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			left = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			right = true;
		if(!actionTriggered){
			if(e.getKeyChar() == 'c'){
				actionTriggered = true;
				if(Main.tm.getCurrentMessage().equals(""))
					talking = true;
				else if(Main.tm.textCount != Main.tm.text.length * Main.tm.TEXTSPEED)
					Main.tm.textCount = Main.tm.text.length * Main.tm.TEXTSPEED;
				else
					Main.tm.clearMessage();
			}	
			else if(e.getKeyChar() == 'v' && !Main.textPaused){
				actionTriggered = true;
				if(!Main.paused){
					Main.paused = true;
					Main.menuOpen = true;
				}
				else{
					Main.paused = false;
					Main.menuOpen = false;
				}
			}
		}
	}
	public void keyReleased(KeyEvent e) {
		actionTriggered = false;
		Main.mm.resetMenuMoveOption();
		if(e.getKeyCode() == KeyEvent.VK_UP)
			up = false;
		else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			down = false;
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
			left = false;
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			right = false;	
	}
	public void keyTyped(KeyEvent e) {
	}
	
}
