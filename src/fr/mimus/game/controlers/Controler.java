package fr.mimus.game.controlers;

import fr.mimus.game.managers.ControlsManager;

public abstract class Controler {
	ControlsManager manager;
	private boolean enable = true;

	public Controler(ControlsManager cm) {
		this.manager=cm;
	}
	
	public abstract void update();
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}
