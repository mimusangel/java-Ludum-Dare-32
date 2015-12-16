package fr.mimus.render.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.mimus.game.managers.ControlsManager;
import fr.mimus.render.Font;
import fr.mimus.render.Texture;
import static org.lwjgl.opengl.GL11.*;


public class Button implements GuiEvent {
	String text;
	int x, y;
	int size;
	int bsize;
	
	float etat = 0f;
	int txtOff = 0;
	
	GuiListener listener;
	boolean locked;

	public Button(String txt, int x, int y, int size, int bsize) {
		this.text=txt;
		this.x=x;
		this.y=y;
		this.size=size;
		this.bsize=bsize;
	}

	public void update(){
		if(locked) return;
		float ts = 12f/Texture.gui.getWidth();
		int csize = size*12/8;
		etat = 0f;
		
		int mx = Mouse.getX(); 
		int my = Display.getHeight() - Mouse.getY();
		
		txtOff = 0;
		if(mx >= x
			&& mx < x+bsize
			&& my >= y
			&& my < y+csize) {
			etat = ts;
			if(Mouse.isButtonDown(0)){
				etat = ts*2;
				txtOff = 1;
			}
			if(ControlsManager.getInstance().isMouseClicked(0)){
				if(this.listener != null) this.listener.GuiClick(this);
			}
		}
	}
	
	public void render() {
		float ts = 12f/Texture.gui.getWidth();
		int csize = size*12/8;
		int tsize = text.length()*size;
		//int bsize = text.length()*csize+csize*2;
		if(locked) glColor4f(1,1,1,.5f);
		Font.drawString(text, x+(bsize/2-tsize/2)+txtOff, y+(csize/2-size/2)+txtOff, size);
		
		Texture.gui.bind();
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, etat);			glVertex2f(x, y);
		glTexCoord2f(ts, etat);			glVertex2f(x+csize, y);
		glTexCoord2f(ts, etat+ts);		glVertex2f(x+csize, y+csize);
		glTexCoord2f(0, etat+ts);		glVertex2f(x, y+csize);
		
		for(int i=0; i<bsize-csize*2; i+=csize) {
			glTexCoord2f(ts, etat);			glVertex2f(x+i+csize, y);
			glTexCoord2f(ts*2, etat);		glVertex2f(x+i+csize*2, y);
			glTexCoord2f(ts*2, etat+ts);	glVertex2f(x+i+csize*2, y+csize);
			glTexCoord2f(ts, etat+ts);		glVertex2f(x+i+csize, y+csize);
		}
		
		glTexCoord2f(ts*2, etat);		glVertex2f(x+bsize-csize, y);
		glTexCoord2f(ts*3, etat);		glVertex2f(x+bsize, y);
		glTexCoord2f(ts*3, etat+ts);	glVertex2f(x+bsize, y+csize);
		glTexCoord2f(ts*2, etat+ts);	glVertex2f(x+bsize-csize, y+csize);
		
		glEnd();
		Texture.unbind();
		if(locked) glColor4f(1,1,1,1f);
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		this.x=x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y=y;
	}

	@Override
	public int getHeight() {
		return size*12/7;
	}

	@Override
	public int getWidth() {
		return bsize;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getBsize() {
		return bsize;
	}

	public void setBsize(int bsize) {
		this.bsize = bsize;
	}

	public GuiListener getListener() {
		return listener;
	}

	public void setListener(GuiListener listener) {
		this.listener = listener;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
