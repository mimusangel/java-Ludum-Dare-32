package fr.mimus.game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.mimus.game.controlers.PlayerControler;
import fr.mimus.game.controlers.StageControler;
import fr.mimus.game.entities.EntityPlayerSP;
import fr.mimus.game.gui.GuiMainMenu;
import fr.mimus.game.inventory.Items;
import fr.mimus.game.level.Level;
import fr.mimus.game.level.Model;
import fr.mimus.game.managers.ControlsManager;
import fr.mimus.game.managers.EntitiesManager;
import fr.mimus.game.managers.ParticuleManager;
import fr.mimus.render.Font;
import fr.mimus.render.Texture;
import fr.mimus.render.gui.GuiBase;

public class Game {
	private static Game instance;
	public static Game getInstance() {
		return instance;
	}
	EntitiesManager entitiesManager;
	ControlsManager controlsManager;
	PlayerControler playerControler;
	StageControler stageControler;
	ParticuleManager particuleManager;
	
	Random rand;
	GuiBase gui = null;

	Audio music;
	public Game() {
		instance=this;
		controlsManager = new ControlsManager(this);
		System.out.println("Control Manager Loaded!");
		rand = new Random();
		Option.load();
		System.out.println("Option Loaded!");
		Score.load();
		System.out.println("Score Loaded!");
		particuleManager = new ParticuleManager();
		System.out.println("Particles Loaded!");
		
		music = Audio.music;
		music.loop();
		gui = new GuiMainMenu();
		// Load Game
		//loadGame();
	}

	public void render3D() {
		if(gui != null && gui.viewBackground()) return;
		if(entitiesManager!= null) entitiesManager.render();
		//		glBegin(GL_LINES);
		//			int size = 256;
		//			for(int i = 0 ; i<=size; i++) {
		//				glVertex3f(i/4f,0,0);
		//				glVertex3f(i/4f,0,size/4f);
		//				glVertex3f(0,0,i/4f);
		//				glVertex3f(size/4f,0,i/4f);
		//			}
		//		glEnd();
		if(getLevel()!=null) getLevel().render();
		glColor4f(1,1,1,1);

		Texture.background.bind();
		float s0 = -128;
		float s1 = Texture.background.getWidth()/10-s0;
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);	 	glVertex3f(s0,0,s0);
		glTexCoord2f(1,0);	 	glVertex3f(s1,0,s0);
		glTexCoord2f(1,1);	 	glVertex3f(s1,0,s1);
		glTexCoord2f(0,1);	 	glVertex3f(s0,0,s1);
		glEnd();
		Texture.unbind();
		Texture.sky.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);	 	glVertex3f(s0-128,40,s0-128);
		glTexCoord2f(1,0);	 	glVertex3f(s1+128,40,s0-128);
		glTexCoord2f(1,1);	 	glVertex3f(s1+128,40,s1+128);
		glTexCoord2f(0,1);	 	glVertex3f(s0-128,40,s1+128);
		glEnd();



		Texture.backhome1.bind();
		float x0 = 0;
		glBegin(GL_QUADS);
		glTexCoord2f(0,1);	 	glVertex3f(x0,0,-10);
		glTexCoord2f(1,1);	 	glVertex3f(60+x0,0,-10);
		glTexCoord2f(1,0);	 	glVertex3f(60+x0,60,-10);
		glTexCoord2f(0,0);	 	glVertex3f(x0,60,-10);

		x0 = 50;
		glTexCoord2f(0,1);	 	glVertex3f(x0,0,-10);
		glTexCoord2f(1,1);	 	glVertex3f(60+x0,0,-10);
		glTexCoord2f(1,0);	 	glVertex3f(60+x0,60,-10);
		glTexCoord2f(0,0);	 	glVertex3f(x0,60,-10);

		x0 = -50;
		glTexCoord2f(0,1);	 	glVertex3f(x0,0,-10);
		glTexCoord2f(1,1);	 	glVertex3f(60+x0,0,-10);
		glTexCoord2f(1,0);	 	glVertex3f(60+x0,60,-10);
		glTexCoord2f(0,0);	 	glVertex3f(x0,60,-10);

		x0 = -18;
		glTexCoord2f(0,1);	 	glVertex3f(x0,0,-40);
		glTexCoord2f(1,1);	 	glVertex3f(60+x0,0,-40);
		glTexCoord2f(1,0);	 	glVertex3f(60+x0,60,-40);
		glTexCoord2f(0,0);	 	glVertex3f(x0,60,-40);

		x0 = 30;
		glTexCoord2f(0,1);	 	glVertex3f(x0,0,-40);
		glTexCoord2f(1,1);	 	glVertex3f(60+x0,0,-40);
		glTexCoord2f(1,0);	 	glVertex3f(60+x0,60,-40);
		glTexCoord2f(0,0);	 	glVertex3f(x0,60,-40);
		glEnd();
		Texture.unbind();
		glBegin(GL_QUADS);
		float my = 30.3f;
		x0 = 12.675f;
		glColor3f(.5f, .5f, .5f);
		glVertex3f(x0,0,-10f);
		glVertex3f(x0,0,-30f);
		glVertex3f(x0,my,-30f);
		glVertex3f(x0,my,-10f);

		x0 = 62.675f;
		glVertex3f(x0,0,-10f);
		glVertex3f(x0,0,-30f);
		glVertex3f(x0,my,-30f);
		glVertex3f(x0,my,-10f);

		x0 = 33.675f;
		glColor3f(.5f, .5f, .5f);
		glVertex3f(x0,0,-10f);
		glVertex3f(x0,0,-30f);
		glVertex3f(x0,my,-30f);
		glVertex3f(x0,my,-10f);

		x0 = -16.25f;
		glColor3f(.5f, .5f, .5f);
		glVertex3f(x0,0,-10f);
		glVertex3f(x0,0,-30f);
		glVertex3f(x0,my,-30f);
		glVertex3f(x0,my,-10f);

		x0 = 87.670f;
		my = 13.2f;
		glColor3f(.5f, .5f, .5f);
		glVertex3f(x0,0,-10f);
		glVertex3f(x0,0,-30f);
		glVertex3f(x0,my,-30f);
		glVertex3f(x0,my,-10f);

		x0 = 37.670f;
		my = 13.2f;
		glColor3f(.5f, .5f, .5f);
		glVertex3f(x0,0,-10f);
		glVertex3f(x0,0,-30f);
		glVertex3f(x0,my,-30f);
		glVertex3f(x0,my,-10f);

		x0 = 51.070f;
		glColor3f(.5f, .5f, .5f);
		glVertex3f(x0,0,-10f);
		glVertex3f(x0,0,-25f);
		glVertex3f(x0,my,-25f);
		glVertex3f(x0,my,-10f);

		x0 = 1.070f;
		glColor3f(.5f, .5f, .5f);
		glVertex3f(x0,0,-10f);
		glVertex3f(x0,0,-25f);
		glVertex3f(x0,my,-25f);
		glVertex3f(x0,my,-10f);
		glEnd();

		for(int i=0; i<20; i++) {
			glPushMatrix();
			glTranslatef(-.2f,0,4+i*(Item3D.fence.getWidth()-2));
			if(i<8) renderFence(90);
			if(i>8) {
				glTranslatef(64f, 0, -5);
				renderFence(90);
				glTranslatef(-64f, 0, 5);
			}
			glTranslatef(72.6f,0,0);
			renderFence(90);
			glTranslatef(-72.4f,0,-(4 +i*(Item3D.fence.getWidth()-2)));
			glTranslatef(4+i*(Item3D.fence.getWidth()-2),0,64.2f);
			if(i<8) renderFence(0);
			glPopMatrix();
		}

		particuleManager.render();
		if(getLevel()!=null) getLevel().renderGlass();
	}

	public void renderFence(int rotateY) {
		glPushMatrix();
		glScalef(.75f, .45f, .75f);
		glRotatef(90, 1, 0, 0);
		glRotatef(rotateY, 0, 0, 1);
		glTranslatef(-(Item3D.fence.getWidth()/2),0,-Item3D.fence.getHeight());
		Item3D.fence.render();
		glPopMatrix();
	}


	public void render2D(int fps, int ticks) {
		glColor4f(1,1,1,1);
		Font.drawString("FPS: "+fps, 5, 5, 9);
		Font.drawString("TPS: "+ticks, 5, 15, 9);

		if(gui != null) {
			gui.render();
		} else {
			EntityPlayerSP player = entitiesManager.getLocalPlayer();
			if(player != null) {
				/*Font.drawString("X: "+player.getPos().x, 5, 25, 9);
				Font.drawString("Y: "+player.getPos().y, 5, 35, 9);
				Font.drawString("Z: "+player.getPos().z, 5, 45, 9);
				Font.drawString("RX: "+player.getRot().x, 5, 60, 9);
				Font.drawString("RY: "+player.getRot().y, 5, 70, 9);
				Font.drawString("e: "+entitiesManager.getEntitySize()+"("+entitiesManager.getEntitySizeALive()+")", 5, 80, 9);
				Font.drawString("l: "+player.getLife(), 5, 90, 9);
				Font.drawString("s: "+player.getScore(), 5, 100, 9);*/


				int y = Display.getHeight() - 70;
				int x = 6;
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

				x = Display.getWidth() - 50;
				y = 5;
				for(int i= 0; i<6; i++) {
					y = 5 + i*17;
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


				y = (Display.getHeight() - 20);
				if(player.getOffsetSpeed()<=-.15f) {
					Font.drawString("FAT", x, y, 14);
					glColor3f(.5f, 0, 0);
					Font.drawString("FAT", x-1, y-1, 14);
					Font.drawString("FAT", x-1, y, 14);
					Font.drawString("FAT", x-1, y+1, 14);
					Font.drawString("FAT", x, y-1, 14);
					Font.drawString("FAT", x, y, 14);
					Font.drawString("FAT", x, y+1, 14);
					Font.drawString("FAT", x+1, y-1, 14);
					Font.drawString("FAT", x+1, y, 14);
					Font.drawString("FAT", x+1, y+1, 14);
					glColor3f(1, 1, 1);
				}
				if(player.getOffsetSpeed()>=.08f) {
					x -= 30;
					Font.drawString("SPEED", x, y, 14);
					glColor3f(0, .5f, 0);
					Font.drawString("SPEED", x-1, y-1, 14);
					Font.drawString("SPEED", x-1, y, 14);
					Font.drawString("SPEED", x-1, y+1, 14);
					Font.drawString("SPEED", x, y-1, 14);
					Font.drawString("SPEED", x, y, 14);
					Font.drawString("SPEED", x, y+1, 14);
					Font.drawString("SPEED", x+1, y-1, 14);
					Font.drawString("SPEED", x+1, y, 14);
					Font.drawString("SPEED", x+1, y+1, 14);
					glColor3f(1, 1, 1);
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

				Texture.unbind();
			}

			Font.drawString("+", Display.getWidth()/2-4, Display.getHeight()/2-4, 7);
		}
	}

	public GuiBase getGui() {
		return gui;
	}

	public void setGui(GuiBase gui) {
		this.gui = gui;
	}

	public void update() {
		music.defineGain();
		if(controlsManager!= null) controlsManager.update();
		if(gui != null) {
			if(playerControler != null && playerControler.isEnable()) playerControler.setEnable(false);
			gui.update();
		} else {
			if(playerControler != null && !playerControler.isEnable()) playerControler.setEnable(true);
			if(entitiesManager!= null) entitiesManager.update();
			particuleManager.update();
		}
	}

	public void loadGame() {
		entitiesManager = new EntitiesManager(this);
		playerControler = new PlayerControler(controlsManager);
		controlsManager.addControler(playerControler);
		stageControler = new StageControler(controlsManager);
		controlsManager.addControler(stageControler);


		// Spawn
		// getEntitiesManager().addEntityIA(x*.5f, 0f, z*.5f);
		stageControler.startStage();
		entitiesManager.getLocalPlayer().setLife(8);
		gui = null;
		Mouse.setGrabbed(true);

	}


	public void dispose() {
		if(entitiesManager != null) {
			entitiesManager.dispose();
			entitiesManager=null;
		}
		if(controlsManager != null) {
			controlsManager.dispose();
			controlsManager=null;
		}
		if(getLevel() != null) {
			getLevel().dispose();
		}
		Item3D.disposeAll();
		Model.disposeAll();
	}

	public EntitiesManager getEntitiesManager() {
		return entitiesManager;
	}

	public ControlsManager getControlsManager() {
		return controlsManager;
	}

	public Level getLevel() {
		if(stageControler == null) return null;
		return stageControler.getLevel();
	}

	public StageControler getStageControler() {
		return stageControler;
	}
}
