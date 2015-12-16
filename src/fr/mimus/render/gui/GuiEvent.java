package fr.mimus.render.gui;

public interface GuiEvent {
	public void render();
	public void update();
	
	public int getX();
	public void setX(int x);
	public int getY();
	public void setY(int y);
	
	public int getHeight();
	public int getWidth();
}
