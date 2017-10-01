package game.Sounds;

import java.io.IOException;

import game.Main.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer {
	public Clip clip;
	public AudioInputStream stream;
	public void playLoop(String name){
		if(!name.equals("")){
			try {
				clip = AudioSystem.getClip();
				stream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream( "/Music/"+ name + ".wav"));
				clip.open(stream);
			} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
			}
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	public void playOnce(String name){
		Clip clip = null;
		AudioInputStream stream;
		try {
			clip = AudioSystem.getClip();
			stream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream( "/Music/"+ name + ".wav"));
			clip.open(stream);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		clip.start();
	}
	public void close(){
		if(clip != null)
			clip.close();
	}
	public void closeStream(){
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
