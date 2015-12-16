package fr.mimus.game.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.mimus.game.Game;
import fr.mimus.game.entities.EntityPlayerSP;
import fr.mimus.game.inventory.Inv;
import fr.mimus.game.inventory.Items;
import fr.mimus.game.managers.ControlsManager;
import fr.mimus.render.Font;
import fr.mimus.render.Texture;
import fr.mimus.render.gui.Button;
import fr.mimus.render.gui.GuiBase;
import fr.mimus.render.gui.GuiEvent;
import fr.mimus.render.gui.GuiListener;

public class GuiCrafting extends GuiBase implements GuiListener {

	Button craftPizzaBacon = new Button("Bacon Pizza", 0, 0, 14, 200);
	Button craftPizzaCheese = new Button("Cheese Pizza", 0, 0, 14, 200);
	Button craftPizzaCannibal = new Button("Cannibal Pizza", 0, 0, 14, 200);
	Button craftPizzaCalzone = new Button("Calzone Pizza", 0, 0, 14, 200);

	Button back = new Button("Back Game", 0, 0, 14, 200);

	EntityPlayerSP player;
	String information = "";
	public GuiCrafting(EntityPlayerSP player) {
		super("Crafting");
		this.player=player;

		back.setListener(this);
		this.addComponent(back);
		craftPizzaBacon.setListener(this);
		this.addComponent(craftPizzaBacon);
		craftPizzaCheese.setListener(this);
		this.addComponent(craftPizzaCheese);
		craftPizzaCannibal.setListener(this);
		this.addComponent(craftPizzaCannibal);
		craftPizzaCalzone.setListener(this);
		this.addComponent(craftPizzaCalzone);
	}

	public void update() {
		super.update();
		this.componentCenterX(craftPizzaBacon);
		craftPizzaBacon.setY(50);
		back.setY(Display.getHeight()-back.getHeight()-2);
		back.setX(Display.getWidth()-back.getWidth()-2);

		this.componentCenterX(craftPizzaCheese);
		craftPizzaCheese.setY(100);
		this.componentCenterX(craftPizzaCannibal);
		craftPizzaCannibal.setY(150);
		this.componentCenterX(craftPizzaCalzone);
		craftPizzaCalzone.setY(200);
		
		if(ControlsManager.getInstance().isKeyClicked(Keyboard.KEY_ESCAPE)) GuiClick(back);
	}

	public void render() {
		super.render();

		//Font.drawString(information, 50, Display.getHeight() - 20, 14);

		int y = Display.getHeight() - 70;
		int x = 65;
		int m = 64;
		
		Texture.pizzaBox.bind();
		glBegin(GL_QUADS);
		if(player.getArmor()>0) {
			glTexCoord2f(0f,0f); 	glVertex2f(x, y);
			glTexCoord2f(.5f,0); 	glVertex2f(x+m/2, y);
			glTexCoord2f(.5f,.5f); 	glVertex2f(x+m/2, y+m/2);
			glTexCoord2f(0f,.5f); 	glVertex2f(x, y+m/2);
		}
		if(player.getArmor()>1) {
			glTexCoord2f(.5f,0f); 	glVertex2f(x+m/2, y);
			glTexCoord2f(1f,0); 	glVertex2f(x+m, y);
			glTexCoord2f(1f,.5f); 	glVertex2f(x+m, y+m/2);
			glTexCoord2f(.5f,.5f); 	glVertex2f(x+m/2, y+m/2);
		}
		if(player.getArmor()>2) {
			glTexCoord2f(.5f,.5f); 	glVertex2f(x+m/2, y+m/2);
			glTexCoord2f(1f,.5f); 	glVertex2f(x+m, y+m/2);
			glTexCoord2f(1f,1f); 	glVertex2f(x+m, y+m);
			glTexCoord2f(.5f,1f); 	glVertex2f(x+m/2, y+m);
		}
		if(player.getArmor()>3) {
			glTexCoord2f(0f,.5f); 	glVertex2f(x, y+m/2);
			glTexCoord2f(.5f,.5f); 	glVertex2f(x+m/2, y+m/2);
			glTexCoord2f(.5f,1f); 	glVertex2f(x+m/2, y+m);
			glTexCoord2f(0f,1f); 	glVertex2f(x, y+m);
		}
		glEnd();
		
		x = 6;
		Texture.pizza0.bind();
		glBegin(GL_TRIANGLES);
		if(player.getLife()>0) {
			glTexCoord2f(0,0); 		glVertex2f(x, y);
			glTexCoord2f(.5f,.5f); 	glVertex2f(x+m/2, y+m/2);
			glTexCoord2f(0,.5f); 	glVertex2f(x, y+m/2);
		}
		if(player.getLife()>1) {
			glTexCoord2f(0,0); 		glVertex2f(x, y);
			glTexCoord2f(.5f, 0); 	glVertex2f(x+m/2, y);
			glTexCoord2f(.5f,.5f); 	glVertex2f(x+m/2, y+m/2);
		}
		if(player.getLife()>2) {
			glTexCoord2f(.5f,0); 	glVertex2f(x+m/2, y);
			glTexCoord2f(1f, 0); 	glVertex2f(x+m, y);
			glTexCoord2f(.5f,.5f); 	glVertex2f(x+m/2, y+m/2);
		}
		if(player.getLife()>3) {
			glTexCoord2f(1f,0); 	glVertex2f(x+m, y);
			glTexCoord2f(1f, .5f); 	glVertex2f(x+m, y+m/2);
			glTexCoord2f(.5f,.5f); 	glVertex2f(x+m/2, y+m/2);
		}
		if(player.getLife()>4) {
			glTexCoord2f(1f, .5f); 	glVertex2f(x+m, y+m/2);
			glTexCoord2f(1f, 1f); 	glVertex2f(x+m, y+m);
			glTexCoord2f(.5f,.5f); 	glVertex2f(x+m/2, y+m/2);
		}
		if(player.getLife()>5) {
			glTexCoord2f(1f, 1f); 	glVertex2f(x+m, y+m);
			glTexCoord2f(.5f, 1f); 	glVertex2f(x+m/2, y+m);
			glTexCoord2f(.5f,.5f); 	glVertex2f(x+m/2, y+m/2);
		}
		if(player.getLife()>6) {
			glTexCoord2f(.5f, 1f); 	glVertex2f(x+m/2, y+m);
			glTexCoord2f(0f, 1f); 	glVertex2f(x, y+m);
			glTexCoord2f(.5f,.5f); 	glVertex2f(x+m/2, y+m/2);
		}
		if(player.getLife()>7) {
			glTexCoord2f(0f, 1f); 	glVertex2f(x, y+m);
			glTexCoord2f(0, .5f); 	glVertex2f(x, y+m/2);
			glTexCoord2f(.5f,.5f); 	glVertex2f(x+m/2, y+m/2);
		}
		glEnd();

		x = 5;
		y = 50;
		for(int i= 0; i<6; i++) {
			y = 50 + i*17;
			Texture.unbind();
			if(i==1) {
				Font.drawString(""+player.getInventory().getNumberItem(Items.TOMATO), x+17, y+1, 14);
				Texture.tomato.bind();
			} else if(i==2) {
				Font.drawString(""+player.getInventory().getNumberItem(Items.STEAK), x+17, y+1, 14);
				Texture.steak.bind();
			} else if(i==3) {
				Font.drawString(""+player.getInventory().getNumberItem(Items.PEPPER), x+17, y+1, 14);
				Texture.pepper.bind();
			} else if(i==4) {
				Font.drawString(""+player.getInventory().getNumberItem(Items.CHEESE), x+17, y+1, 14);
				Texture.cheese.bind();
			} else if(i==5) {
				Font.drawString(""+player.getInventory().getNumberItem(Items.EGG), x+17, y+1, 14);
				Texture.egg.bind();
			} else {
				Font.drawString(""+player.getInventory().getNumberItem(Items.PATE), x+17, y+1, 14);
				Texture.pate.bind();
			}
			glBegin(GL_QUADS);
			glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
			glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
			glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
			glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
			glEnd();
		}

		x = 2;
		y = Display.getHeight() - 190;
		for(int i=0; i<4; i++) {
			y = (Display.getHeight() - 190) + i*17;
			Font.drawString(""+player.getInventory().getNumberPizza(i), x+17, y+1, 14);
			if(i==1) {
				Texture.pizza1.bind();
			} else if(i==2) {
				Texture.pizza2.bind();
			} else if(i==3) {
				Texture.pizza3.bind();
			} else {
				Texture.pizza0.bind();
			}
			glBegin(GL_QUADS);
			glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
			glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
			glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
			glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
			glEnd();
		}


		y = craftPizzaBacon.getY() + 5;
		x = craftPizzaBacon.getX() + craftPizzaBacon.getWidth() + 5;
		Font.drawString("Battle Pizza".toUpperCase(), x, y, 9);
		y = craftPizzaCheese.getY() + 5;
		Font.drawString("Heal(1)".toUpperCase(), x, y, 9);
		Font.drawString("+Lower speed".toUpperCase(), x, y+11, 9);
		y = craftPizzaCannibal.getY() + 5;
		Font.drawString("Heal(2)".toUpperCase(), x, y, 9);
		y = craftPizzaCalzone.getY() + 5;
		Font.drawString("Upper speed".toUpperCase(), x, y, 9);

		y = 75;
		x = craftPizzaBacon.getX();
		Font.drawString("1", x+17, y+1, 14);
		Texture.pate.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();

		x = craftPizzaBacon.getX()+40;
		Font.drawString("1", x+17, y+1, 14);
		Texture.tomato.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();

		x = craftPizzaBacon.getX()+80;
		Font.drawString("1", x+17, y+1, 14);
		Texture.steak.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();
		
		
		y = 125;
		x = craftPizzaBacon.getX();
		Font.drawString("1", x+17, y+1, 14);
		Texture.pate.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();

		x = craftPizzaBacon.getX()+40;
		Font.drawString("1", x+17, y+1, 14);
		Texture.tomato.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();

		x = craftPizzaBacon.getX()+80;
		Font.drawString("3", x+17, y+1, 14);
		Texture.cheese.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();
		
		y = 175;
		x = craftPizzaBacon.getX();
		Font.drawString("1", x+17, y+1, 14);
		Texture.pate.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();

		x = craftPizzaBacon.getX()+40;
		Font.drawString("1", x+17, y+1, 14);
		Texture.tomato.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();

		x = craftPizzaBacon.getX()+80;
		Font.drawString("1", x+17, y+1, 14);
		Texture.cheese.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();
		
		x = craftPizzaBacon.getX()+120;
		Font.drawString("2", x+17, y+1, 14);
		Texture.steak.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();
		
		x = craftPizzaBacon.getX()+160;
		Font.drawString("2", x+17, y+1, 14);
		Texture.pepper.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();
		
		
		y = 225;
		x = craftPizzaBacon.getX();
		Font.drawString("1", x+17, y+1, 14);
		Texture.pate.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();

		x = craftPizzaBacon.getX()+40;
		Font.drawString("1", x+17, y+1, 14);
		Texture.tomato.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();

		x = craftPizzaBacon.getX()+80;
		Font.drawString("1", x+17, y+1, 14);
		Texture.cheese.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();
		
		x = craftPizzaBacon.getX()+120;
		Font.drawString("1", x+17, y+1, 14);
		Texture.steak.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();
		
		x = craftPizzaBacon.getX()+160;
		Font.drawString("1", x+17, y+1, 14);
		Texture.egg.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0f, 0f); 	glVertex2f(x, y);
		glTexCoord2f(1f, 0f); 	glVertex2f(x+16, y);
		glTexCoord2f(1f, 1f); 	glVertex2f(x+16, y+16);
		glTexCoord2f(0f, 1f); 	glVertex2f(x, y+16);
		glEnd();
		
		Texture.unbind();
		
		Font.drawString("Cost 2 score point per craft!", 65, Display.getHeight() - 20, 14);
	}

	@Override
	public void GuiClick(GuiEvent src) {
		if(src == back) {
			Game.getInstance().setGui(null);
			Mouse.setGrabbed(true);
		}
		if(src == craftPizzaBacon) {
			Inv inv = player.getInventory();
			if(inv.getNumberItem(Items.PATE)>0 
					&& inv.getNumberItem(Items.TOMATO)>0
					&& inv.getNumberItem(Items.STEAK)>0) {
				inv.subItem(Items.PATE, 1);
				inv.subItem(Items.TOMATO, 1);
				inv.subItem(Items.STEAK, 1);
				inv.addPizza(Items.PIZZA_BACON, 1);
				player.subScore(2);
				information = "You have craft bacon pizza.";
			} else {
				information = "You don't have a ingredient!";
			}
		}

		if(src == craftPizzaCheese) {
			Inv inv = player.getInventory();
			if(inv.getNumberItem(Items.PATE)>0 
					&& inv.getNumberItem(Items.TOMATO)>0
					&& inv.getNumberItem(Items.CHEESE)>2) {
				inv.subItem(Items.PATE, 1);
				inv.subItem(Items.TOMATO, 1);
				inv.subItem(Items.CHEESE, 3);
				inv.addPizza(Items.PIZZA_CHEESE, 1);
				player.subScore(2);
				information = "You have craft bacon pizza.";
			} else {
				information = "You don't have a ingredient!";
			}
		}
		if(src == craftPizzaCannibal) {
			Inv inv = player.getInventory();
			if(inv.getNumberItem(Items.PATE)>0 
					&& inv.getNumberItem(Items.TOMATO)>0
					&& inv.getNumberItem(Items.CHEESE)>0
					&& inv.getNumberItem(Items.STEAK)>1
					&& inv.getNumberItem(Items.PEPPER)>1) {
				inv.subItem(Items.PATE, 1);
				inv.subItem(Items.TOMATO, 1);
				inv.subItem(Items.CHEESE, 1);
				inv.subItem(Items.STEAK, 2);
				inv.subItem(Items.PEPPER, 2);
				inv.addPizza(Items.PIZZA_CANNIBAL, 1);
				player.subScore(2);
				information = "You have craft bacon pizza.";
			} else {
				information = "You don't have a ingredient!";
			}
		}
		if(src == craftPizzaCalzone) {
			Inv inv = player.getInventory();
			if(inv.getNumberItem(Items.PATE)>0 
					&& inv.getNumberItem(Items.TOMATO)>0
					&& inv.getNumberItem(Items.CHEESE)>0
					&& inv.getNumberItem(Items.STEAK)>0
					&& inv.getNumberItem(Items.EGG)>0) {
				inv.subItem(Items.PATE, 1);
				inv.subItem(Items.TOMATO, 1);
				inv.subItem(Items.CHEESE, 1);
				inv.subItem(Items.STEAK, 1);
				inv.subItem(Items.EGG, 1);
				inv.addPizza(Items.PIZZA_CALZONE, 1);
				player.subScore(2);
				information = "You have craft bacon pizza.";
			} else {
				information = "You don't have a ingredient!";
			}
		}
	}
	public boolean viewBackground() {
		return false;
	}

}
