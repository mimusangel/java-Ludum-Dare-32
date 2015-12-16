package fr.mimus.game.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.opengl.Display;

import fr.mimus.game.Game;
import fr.mimus.game.entities.EntityPlayerSP;
import fr.mimus.game.inventory.Items;
import fr.mimus.render.Font;
import fr.mimus.render.Texture;
import fr.mimus.render.gui.Button;
import fr.mimus.render.gui.GuiBase;
import fr.mimus.render.gui.GuiEvent;
import fr.mimus.render.gui.GuiListener;

public class GuiNextStage extends GuiBase implements GuiListener {

	Button next = new Button("Next", 0, 0, 14, 200);
	Button buyLife = new Button("Buy Life(100)", 50, 50, 14, 250);
	Button buyArmor = new Button("Buy Armor(50)", 50, 70, 14, 250);
	Button buyTripleShoot = new Button("Bonus 3 Shot(150)", 50, 90, 14, 250);
	Button buyPizzaBacon = new Button("Buy Bacon(25)", 310, 50, 14, 250);
	Button buyPizzaCheese = new Button("Buy Cheeze(50)", 310, 70, 14, 250);
	Button buyPizzaCannibal = new Button("Buy Cannibal(100)", 310, 90, 14, 250);
	Button buyPizzaCalzone = new Button("Buy Calzone(75)", 310, 110, 14, 250);
	
	EntityPlayerSP player;
	public GuiNextStage() {
		super("Stage Win!");
		next.setListener(this);
		this.addComponent(next);

		buyLife.setListener(this);
		this.addComponent(buyLife);
		buyArmor.setListener(this);
		this.addComponent(buyArmor);
		buyTripleShoot.setListener(this);
		this.addComponent(buyTripleShoot);
		
		buyPizzaBacon.setListener(this);
		this.addComponent(buyPizzaBacon);
		buyPizzaCheese.setListener(this);
		this.addComponent(buyPizzaCheese);
		buyPizzaCannibal.setListener(this);
		this.addComponent(buyPizzaCannibal);
		buyPizzaCalzone.setListener(this);
		this.addComponent(buyPizzaCalzone);
		
		player = Game.getInstance().getEntitiesManager().getLocalPlayer();
	}
	
	public void update() {
		super.update();
		next.setY(Display.getHeight()-next.getHeight()-2);
		next.setX(Display.getWidth()-next.getWidth()-2);
		
		if(player.isTripleShot()) {
			buyTripleShoot.setLocked(true);
		} else {
			buyTripleShoot.setLocked(false);
		}
		
		if(player.getLife()>=8) {
			buyLife.setLocked(true);
		} else {
			buyLife.setLocked(false);
		}
		
		if(player.getArmor()>=4) {
			buyArmor.setLocked(true);
		} else {
			buyArmor.setLocked(false);
		}
	}
	
	public void render() {
		super.render();
		Font.drawString("Score: "+player.getScore(), 65, Display.getHeight() - 35, 14);
		Font.drawString("Your score is a money!", 65, Display.getHeight() - 20, 14);
		
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
		
		Texture.pizza0.bind();
		glBegin(GL_TRIANGLES);
		x = 6;
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
		
	}
	
	@Override
	public void GuiClick(GuiEvent src) {
		if(src == next) {
			Game.getInstance().getStageControler().next();
		}
		if(src == buyLife) {
			EntityPlayerSP player = Game.getInstance().getEntitiesManager().getLocalPlayer();
			if(player.getScore()>=100 && player.getLife() < 8) {
				int life = player.getLife();
				player.setLife(life+1);
				player.subScore(100);
			}
		}
		
		if(src == buyArmor) {
			EntityPlayerSP player = Game.getInstance().getEntitiesManager().getLocalPlayer();
			if(player.getScore()>=50 && player.getArmor()<4) {
				int armor = player.getArmor();
				player.setArmor(armor + 1);
				player.subScore(50);
			}
		}
		
		if(src == buyTripleShoot) {
			EntityPlayerSP player = Game.getInstance().getEntitiesManager().getLocalPlayer();
			if(player.getScore()>=150) {
				player.setTripleShot(true);
				player.subScore(150);
			}
		}
		
		if(src == buyPizzaBacon) {
			EntityPlayerSP player = Game.getInstance().getEntitiesManager().getLocalPlayer();
			if(player.getScore()>=25) {
				player.getInventory().addPizza(Items.PIZZA_BACON, 1);
				player.subScore(25);
			}
		}
		if(src == buyPizzaCheese) {
			EntityPlayerSP player = Game.getInstance().getEntitiesManager().getLocalPlayer();
			if(player.getScore()>=50) {
				player.getInventory().addPizza(Items.PIZZA_CHEESE, 1);
				player.subScore(50);
			}
		}
		if(src == buyPizzaCannibal) {
			EntityPlayerSP player = Game.getInstance().getEntitiesManager().getLocalPlayer();
			if(player.getScore()>=100) {
				player.getInventory().addPizza(Items.PIZZA_CANNIBAL, 1);
				player.subScore(100);
			}
		}
		if(src == buyPizzaCalzone) {
			EntityPlayerSP player = Game.getInstance().getEntitiesManager().getLocalPlayer();
			if(player.getScore()>=75) {
				player.getInventory().addPizza(Items.PIZZA_CALZONE, 1);
				player.subScore(75);
			}
		}
	}
	public boolean viewBackground() {
		return false;
	}
}
