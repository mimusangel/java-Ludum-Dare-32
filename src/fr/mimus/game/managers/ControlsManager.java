package fr.mimus.game.managers;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import fr.mimus.game.Game;
import fr.mimus.game.Main;
import fr.mimus.game.controlers.Controler;

public class ControlsManager {
	private static ControlsManager instance = null;
	public static ControlsManager getInstance() {
		return instance;
	}

	private boolean lastMouses[];
	private boolean mouses[];
	
	private boolean lastKeys[];
	private boolean keys[];
	
	private List<Controler> controlers;
	Game game;
	public ControlsManager(Game g) {
		instance=this;
		game=g;
		controlers = new ArrayList<Controler>();
		lastMouses = new boolean[Mouse.getButtonCount()];
		mouses = new boolean[Mouse.getButtonCount()];
		lastKeys = new boolean[Keyboard.getKeyCount()];
		keys = new boolean[Keyboard.getKeyCount()];
	}
	
	public void update() {
		for(int i = 0; i<mouses.length; i++) {
			lastMouses[i] = mouses[i];
			mouses[i] = Mouse.isButtonDown(i);
		}
		
		for(int i = 0; i<keys.length; i++) {
			lastKeys[i] = keys[i];
			keys[i] = Keyboard.isKeyDown(i);
		}
		
		for(int i = 0; i<controlers.size(); i++) {
			Controler c = controlers.get(i);
			if(c!=null && c.isEnable()) {
				c.update();
			}
		}
		if(this.isKeyClicked(Keyboard.KEY_F1)) Mouse.setGrabbed(false);
		if(this.isKeyClicked(Keyboard.KEY_F2)) Main.needScreen=true;
	}
	
	public void addControler(Controler c) {
		controlers.add(c);
	}
	
	public boolean isKeyClicked(int id) {
		if(!keys[id] && lastKeys[id]) {
			lastKeys[id] = false;
			return true;
		}
		return false;
	}
	
	public boolean isKeyDown(int id) {
		return keys[id];
	}
	
	public boolean isMouseClicked(int id) {
		if(!mouses[id] && lastMouses[id]) {
			lastKeys[id] = false;
			return true;
		}
		return false;
	}

	public boolean isMouseDown(int id) {
		return mouses[id];
	}
	public Game getGame() {
		return game;
	}

	public void dispose() {
		
		
	}
}
