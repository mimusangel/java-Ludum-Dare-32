package fr.mimus.game.gui;

import org.lwjgl.input.Keyboard;

import fr.mimus.game.Game;
import fr.mimus.game.Option;
import fr.mimus.game.managers.ControlsManager;
import fr.mimus.render.Font;
import fr.mimus.render.gui.Button;
import fr.mimus.render.gui.GuiBase;
import fr.mimus.render.gui.GuiEvent;
import fr.mimus.render.gui.GuiListener;
import fr.mimus.render.gui.HScrollBar;

public class GuiOption extends GuiBase implements GuiListener {

	GuiBase back;
	Button quit = new Button("Back", 0, 0, 14, 200);

	Button keyboard = new Button("AZERTY", 0, 0, 14, 200);
	HScrollBar effect = new HScrollBar(0, 0, 100);
	HScrollBar music = new HScrollBar(0, 0, 100);
	Button particles = new Button("Particles: On", 0, 0, 14, 200);
	public GuiOption(GuiBase b) {
		super("Option");
		back=b;

		quit.setListener(this);
		this.addComponent(quit);

		keyboard.setListener(this);
		this.addComponent(keyboard);
		effect.setListener(this);
		this.addComponent(effect);
		music.setListener(this);
		this.addComponent(music);
		
		particles.setListener(this);
		this.addComponent(particles);
		
		effect.setValue(Option.getVolume_effect());
		music.setValue(Option.getVolume_music());
		
	}
	
	public void render() {
		super.render();
		
		Font.drawString("Keyboard Type".toUpperCase(), keyboard.getX()+5, keyboard.getY()- 14, 12);
		Font.drawString("Sound Effect".toUpperCase(), effect.getX()-40, effect.getY()- 14, 12);
		Font.drawString("Music".toUpperCase(), music.getX()-40, music.getY()- 14, 12);
		Font.drawString("Particles:".toUpperCase(), particles.getX()+5, particles.getY()- 14, 12);
	}
	
	public void update() {
		super.update();
		if(Option.isKey_qwerty()) {
			keyboard.setText("QWERTY");
		} else {
			keyboard.setText("AZERTY");
		}
		if(Option.isParticles()) {
			particles.setText("On");
		} else {
			particles.setText("Off");
		}
		
		this.componentCenterX(keyboard);
		this.componentCenterY(keyboard);
		keyboard.setY(keyboard.getY() - 100);
		
		this.componentCenterX(effect);
		this.componentCenterY(effect);
		effect.setY(effect.getY() - 60);
		
		this.componentCenterX(music);
		this.componentCenterY(music);
		music.setY(music.getY() - 20);
		
		this.componentCenterX(particles);
		this.componentCenterY(particles);
		particles.setY(particles.getY() + 20);
		
		this.componentCenterX(quit);
		this.componentCenterY(quit);
		quit.setY(quit.getY() + 60);
		
		if(ControlsManager.getInstance().isKeyClicked(Keyboard.KEY_ESCAPE)) GuiClick(quit);
	}
	
	
	@Override
	public void GuiClick(GuiEvent src) {
		if(src == quit) {
			Option.save();
			Game.getInstance().setGui(back);
		}
		if(src == keyboard) {
			Option.setKey_qwerty(!Option.isKey_qwerty());
		}
		if(src == effect) {
			Option.setVolume_effect(effect.getValue());
		}
		if(src == music) {
			Option.setVolume_music(music.getValue());
		}
		if(src == particles) {
			Option.setParticles(!Option.isParticles());
		}
	}
	public boolean viewBackground() {
		return back.viewBackground();
	}

}
