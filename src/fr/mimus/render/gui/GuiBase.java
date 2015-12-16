package fr.mimus.render.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.mimus.render.Font;

public abstract class GuiBase {
	private List<GuiEvent> components = new ArrayList<GuiEvent>();
	private String title = null;
	
	public GuiBase() {
		Mouse.setGrabbed(false);
		}
	public GuiBase(String t) {
		title = t;
		Mouse.setGrabbed(false);
	}
	
	public void update() {
		for(int i=0; i<components.size(); i++) components.get(i).update();
	}
	
	public void render() {
		if(title != null) {
			int size = 27;
			int x = Display.getWidth()/2 - (title.length()*size)/2;
			Font.drawString(title.toUpperCase(), x, 5, size);
		}
		for(int i=0; i<components.size(); i++) components.get(i).render();
	}
	
	public void addComponent(GuiEvent event) {
		components.add(event);
	}
	
	public void componentCenterX(GuiEvent e) {
		int x = Display.getWidth()/2 - e.getWidth()/2;
		e.setX(x);
	}
	
	public void componentCenterY(GuiEvent e) {
		int y = Display.getHeight()/2 - e.getHeight()/2;
		e.setY(y);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean viewBackground() {
		return true;
	}
}
