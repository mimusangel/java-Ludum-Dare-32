package fr.mimus.game;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio {

	public static Audio hit = new Audio("res/sounds/hit.wav", 0);
	public static Audio fire = new Audio("res/sounds/fire.wav", 0);
	public static Audio pickup = new Audio("res/sounds/pickup.wav", 0);
	public static Audio eat = new Audio("res/sounds/eat.wav", 0);
	public static Audio music = new Audio("res/sounds/music2.wav", 1);
	
	Clip clip;
	FloatControl gainControl;
	String path;
	int type = 0;
	
	public Audio(Audio a) {
		clip=a.clip;
		gainControl=a.gainControl;
		path=a.path;
		type=a.type;
	}
	
	public Audio(String p, int t) {
		path=p;
		type=t;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(p));
			clip = AudioSystem.getClip();
			clip.open(ais);
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			setGain(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	public Audio setGain(int p) {
		p = 100-p;
		float m = gainControl.getMaximum() + gainControl.getMinimum();
		float v = ((float) p * m) / 100f;
		setGain(v);
		return this;
	}

	public Audio setGain(float g) {
		if(g<gainControl.getMinimum()) g=gainControl.getMinimum();
		if(g>gainControl.getMaximum()) g=gainControl.getMaximum();
		gainControl.setValue(g);
		return this;
	}
	
	public void defineGain() {
		if(type == 1) {
			setGain(Option.getVolume_music());
		} else {
			setGain(Option.getVolume_effect());
		}
	}
	
	public Audio play() {
		defineGain();
		clip.setFramePosition(0);
		clip.start();
		return this;
	}
	
	public Audio loop() {
		defineGain();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		return this;
	}
	
	public Audio stop() {
		clip.close();
		return this;
	}
}
