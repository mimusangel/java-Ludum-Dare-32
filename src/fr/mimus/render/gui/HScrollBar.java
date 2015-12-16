package fr.mimus.render.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.mimus.game.managers.ControlsManager;
import fr.mimus.render.Font;
import fr.mimus.render.Texture;
import static org.lwjgl.opengl.GL11.*;
public class HScrollBar implements GuiEvent {
	int x = 0, y = 0, maxValue = 100, value = 0, offValue = 0;
	private int size = 16, r = 5;
	private boolean viewText=true;
	private GuiListener listener;
	
	public HScrollBar(int x, int y, int size) {
		this.x=x;
		this.y=y;
		this.maxValue=size;
	}
	
	@Override
	public void render() {
		float ts = 12f/Texture.gui.getWidth();
		float tx0 = 3*ts;
		int mx = r*size+size*2;
		int v = (value*mx)/maxValue;
		if(viewText) Font.drawString(""+(value-offValue), x+this.getWidth()+2, y+4, 8);

		Texture.gui.bind();
		glBegin(GL_QUADS);
		// Cursor
		glTexCoord2f(tx0, ts); 			glVertex2f(x-8+v, y);
		glTexCoord2f(tx0+ts, ts); 		glVertex2f(x-8+v+size, y);
		glTexCoord2f(tx0+ts, ts*2); 	glVertex2f(x-8+v+size, y+size);
		glTexCoord2f(tx0, ts*2); 		glVertex2f(x-8+v, y+size);
		
		glTexCoord2f(tx0, 0); 			glVertex2f(x, y);
		glTexCoord2f(tx0+ts, 0); 		glVertex2f(x+size, y);
		glTexCoord2f(tx0+ts, ts); 		glVertex2f(x+size, y+size);
		glTexCoord2f(tx0, ts); 			glVertex2f(x, y+size);
			
		for(int i=0; i<r; i++) {
			glTexCoord2f(tx0+ts, 0); 		glVertex2f(x+size*(i+1), y);
			glTexCoord2f(tx0+ts*2, 0); 		glVertex2f(x+size*(i+1)+size, y);
			glTexCoord2f(tx0+ts*2, ts); 	glVertex2f(x+size*(i+1)+size, y+size);
			glTexCoord2f(tx0+ts, ts); 		glVertex2f(x+size*(i+1), y+size);
		}
		
		glTexCoord2f(tx0+ts*2, 0); 		glVertex2f(x+size*(r+1), y);
		glTexCoord2f(tx0+ts*3, 0); 		glVertex2f(x+size*(r+1)+size, y);
		glTexCoord2f(tx0+ts*3, ts); 	glVertex2f(x+size*(r+1)+size, y+size);
		glTexCoord2f(tx0+ts*2, ts); 	glVertex2f(x+size*(r+1), y+size);
		glEnd();
		Texture.unbind();
		
	}

	@Override
	public void update() {
		int mx = r*size+size*2;
		if(ControlsManager.getInstance().isMouseDown(0)) {
			int px = Mouse.getX();
			int py = Display.getHeight()-Mouse.getY();
			
			if(px>=x-2 && px<x+this.getWidth()+2
					&& py>=y && py<y+this.getHeight()) {
				int v = px - x;
				value = (v*maxValue)/mx;
				if(value<0) value=0;
				if(value>maxValue) value=maxValue;
				if(this.listener != null) this.listener.GuiClick(this);
				
			}
		}

	}

	@Override
	public int getHeight() {
		return size;
	}

	@Override
	public int getWidth() {
		return size*(r+1)+size;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getValue() {
		return value - offValue;
	}

	public void setValue(int value) {
		this.value = value + offValue;
	}

	public int getOffValue() {
		return offValue;
	}

	public void setOffValue(int offValue) {
		this.offValue = offValue;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public boolean isViewText() {
		return viewText;
	}

	public void setViewText(boolean viewText) {
		this.viewText = viewText;
	}

	public void setListener(GuiListener listener) {
		this.listener = listener;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
