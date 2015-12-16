package fr.mimus.game.gui;

import org.lwjgl.input.Mouse;

import fr.mimus.game.Game;
import fr.mimus.game.Score;
import fr.mimus.render.Font;
import fr.mimus.render.gui.Button;
import fr.mimus.render.gui.GuiBase;
import fr.mimus.render.gui.GuiEvent;
import fr.mimus.render.gui.GuiListener;
import fr.mimus.utils.Window;

public class GuiPause extends GuiBase implements GuiListener  {
	Button resume = new Button("Resume", 0, 0, 14, 200);
	Button opt = new Button("Option", 0, 0, 14, 200);
	Button quit = new Button("Exit", 0, 0, 14, 200);
	public GuiPause() {
		super("Pause");
		resume.setListener(this);
		this.addComponent(resume);
		opt.setListener(this);
		this.addComponent(opt);
		quit.setListener(this);
		this.addComponent(quit);
	}
	
	public void update() {
		super.update();
		this.componentCenterX(resume);
		this.componentCenterY(resume);
		resume.setY(resume.getY() - resume.getHeight()/2 - 1);
		
		this.componentCenterX(opt);
		this.componentCenterY(opt);
		opt.setY(opt.getY() + opt.getHeight()/2 - 2);
		
		this.componentCenterX(quit);
		this.componentCenterY(quit);
		quit.setY(quit.getY() + quit.getHeight()/2 + 21);
		
		
	}
	public void render() {
		super.render();
		Font.drawString("Score: ", 5, 25, 12);
		Font.drawString("Best: "+Score.getBest(), 15, 40, 8);
		Font.drawString("Last: "+Score.getLast(), 15, 51, 8);

		Font.drawString("Actual: "+Game.getInstance().getEntitiesManager().getLocalPlayer().getScore(), 5, 60, 12);
	}
	
	
	@Override
	public void GuiClick(GuiEvent src) {
		if(src == resume) {
			Game.getInstance().setGui(null);
			Mouse.setGrabbed(true);
		}
		if(src == opt) {
			Game.getInstance().setGui(new GuiOption(this));
		}
		if(src == quit) {
			Window.closeRequest();
		}
	}
	
	public boolean viewBackground() {
		return false;
	}
}
