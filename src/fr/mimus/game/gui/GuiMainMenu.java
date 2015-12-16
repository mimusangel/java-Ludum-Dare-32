package fr.mimus.game.gui;

import org.lwjgl.opengl.Display;

import fr.mimus.game.Game;
import fr.mimus.game.Score;
import fr.mimus.render.Font;
import fr.mimus.render.Texture;
import fr.mimus.render.gui.Button;
import fr.mimus.render.gui.GuiBase;
import fr.mimus.render.gui.GuiEvent;
import fr.mimus.render.gui.GuiListener;
import fr.mimus.utils.Window;
import static org.lwjgl.opengl.GL11.*;

public class GuiMainMenu extends GuiBase implements GuiListener {

	Button start = new Button("Start", 0, 0, 14, 200);
	Button opt = new Button("Option", 0, 0, 14, 200);
	Button quit = new Button("Exit", 0, 0, 14, 200);
	public GuiMainMenu() {
		super("Pizza Predator");
		start.setListener(this);
		this.addComponent(start);
		opt.setListener(this);
		this.addComponent(opt);
		quit.setListener(this);
		this.addComponent(quit);
	}
	
	public void render() {
		super.render();
		Font.drawString("Score: ", 5, 25, 12);
		Font.drawString("Best: "+Score.getBest(), 15, 40, 8);
		Font.drawString("Last: "+Score.getLast(), 15, 51, 8);
		

		Font.drawString("@Mimus_Angel", Display.getWidth()-150, Display.getHeight()-20, 12);
		
		Texture.backmenu.bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0,0); 			glVertex2f(0,0);
			glTexCoord2f(1,0); 			glVertex2f(Display.getWidth(),0);
			glTexCoord2f(1,1); 			glVertex2f(Display.getWidth(),Display.getHeight());
			glTexCoord2f(0,1); 			glVertex2f(0,Display.getHeight());
			glEnd();
		Texture.unbind();
	}
	
	public void update() {
		super.update();
		this.componentCenterX(start);
		this.componentCenterY(start);
		start.setY(start.getY() - start.getHeight()/2 - 1);
		
		this.componentCenterX(opt);
		this.componentCenterY(opt);
		opt.setY(opt.getY() + opt.getHeight()/2 - 2);
		
		this.componentCenterX(quit);
		this.componentCenterY(quit);
		quit.setY(quit.getY() + quit.getHeight()/2 + 21);
	}
	
	
	@Override
	public void GuiClick(GuiEvent src) {
		if(src == start) {
			Game.getInstance().loadGame();
		}
		if(src == opt) {
			Game.getInstance().setGui(new GuiOption(this));
		}
		if(src == quit) {
			Window.closeRequest();
		}
	}

}
