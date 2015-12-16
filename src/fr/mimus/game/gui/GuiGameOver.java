package fr.mimus.game.gui;

import org.lwjgl.input.Mouse;
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

public class GuiGameOver extends GuiBase implements GuiListener {

	Button replay = new Button("Try Again", 0, 0, 14, 200);
	Button exit = new Button("Exit", 0, 0, 14, 200);
	
	int score;
	boolean win = false;
	public GuiGameOver(int s) {
		super("Game Over !");
		this.score=s;
		win = Score.addScore(s);
		replay.setListener(this);
		this.addComponent(replay);
		exit.setListener(this);
		this.addComponent(exit);
		Mouse.setGrabbed(false);
	}
	
	public void render() {
		super.render();
		String msg = "You have lost all your pizza delivery!".toUpperCase();
		int x = Display.getWidth()/2 - msg.length()*12/2;
		glColor3f(.5f, 0, 0);
		Font.drawString(msg, x, 50, 12);
		
		msg = ("Your Score: "+score).toUpperCase();
		x = Display.getWidth()/2 - msg.length()*12/2;
		if(win) glColor3f(0, .5f, 0);
		Font.drawString(msg, x, 70, 14);
		
		glColor3f(1, 1, 1);
		msg = ("Best: "+Score.getBest()).toUpperCase();
		x = Display.getWidth()/2 - msg.length()*12/2;
		Font.drawString(msg, x, 90, 14);
		msg = ("last: "+Score.getLast()).toUpperCase();
		x = Display.getWidth()/2 - msg.length()*12/2;
		Font.drawString(msg, x, 105, 12);
		
		if(win) {
			x = Display.getWidth()/2;
			int y = (exit.getY()+exit.getHeight() + 24);
			Texture.pizzaGold.bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0); glVertex2f(x-16, y-16);
			glTexCoord2f(1, 0); glVertex2f(x+16, y-16);
			glTexCoord2f(1, 1); glVertex2f(x+16, y+16);
			glTexCoord2f(0, 1); glVertex2f(x-16, y+16);
			glEnd();
			Texture.unbind();
		}
		
	}
	
	public void update() {
		super.update();
		this.componentCenterX(replay);
		this.componentCenterY(replay);
		replay.setY(replay.getY() - replay.getHeight()/2 - 1);
		
		this.componentCenterX(exit);
		this.componentCenterY(exit);
		exit.setY(exit.getY() + exit.getHeight()/2 + 1);
	}
	
	@Override
	public void GuiClick(GuiEvent src) {
		if(src == replay) {
			Game.getInstance().loadGame();
		}
		if(src == exit) {
			Window.closeRequest();
		}
	}
	public boolean viewBackground() {
		return false;
	}

}
