package fr.mimus.game.entities;

import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.mimus.game.Game;
import fr.mimus.game.Item3D;
import fr.mimus.game.gui.GuiGameOver;
import fr.mimus.game.inventory.Inv;
import fr.mimus.game.inventory.Items;

public class EntityPlayerSP extends Entity {
	String name;
	int life = 10;
	int armor = 4;
	Inv inventory;
	int score;
	
	float offsetSpeed = 0;
	boolean tripleShot = false;
	public EntityPlayerSP(int id, Vector3f p, Vector2f r, String n) {
		super(id, p, r);
		this.name=n;
		life = 8;
		inventory = new Inv();
		inventory.init();
		this.setRadius(.35f);
	}

	public Inv getInventory() {
		return inventory;
	}

	@Override
	public void update() {
		//setLife(8);
		if(life<=0) {
			int totalScore = this.getScore();
			for(int i=0; i<6; i++) {
				totalScore+=this.getInventory().getNumberItem(i)*2;
			}

			for(int i=0; i<4; i++) {
				totalScore+=this.getInventory().getNumberPizza(i)*5;
			}
			totalScore+= (Game.getInstance().getStageControler().getStage()*10);
			Game.getInstance().setGui(new GuiGameOver(totalScore));
		}
	}

	@Override
	public void render() {
		glRotatef(rot.getX(),1,0,0);
		glRotatef(rot.getY(),0,1,0);
		glTranslatef(-pos.getX(), -pos.getY()-height, -pos.getZ());

		if(this.getInventory().getNumberPizza(Items.PIZZA_BACON)>0) {
			glPushMatrix();		
			Vector3f ppos = new Vector3f(pos);
			Vector3f add = this.getSide(0);
			ppos.x += add.x*.4f;
			ppos.y += add.y*.4f;
			ppos.z += add.z*.4f;
			glTranslatef(ppos.getX(), ppos.getY()+height*.88f, ppos.getZ());
			glPushMatrix();
			//glRotatef(-40,1,0,0);		
			glRotatef(-rot.getY()-90,0,1,0);
			glRotatef(rot.getX(),0,0,1);
			glRotatef(20,1,0,0);
			glTranslatef(-(Item3D.pizza.getWidth()/2), 
					-(Item3D.pizza.getSize()/2), 
					-(Item3D.pizza.getHeight()/2));
			Item3D.pizza.render();
			glColor4f(1,1,1,1);
			glPopMatrix();
			glPopMatrix();
		}
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public void subScore(int score) {
		this.score -= score;
	}

	public float getOffsetSpeed() {
		return offsetSpeed;
	}

	public void setOffsetSpeed(float offsetSpeed) {
		this.offsetSpeed = offsetSpeed;
	}
	
	public void addOffsetSpeed(float offsetSpeed) {
		this.offsetSpeed += offsetSpeed;
	}
	public void subOffsetSpeed(float offsetSpeed) {
		this.offsetSpeed -= offsetSpeed;
	}

	public void looseLife(int i) {
		for(int j=0; j<i; j++) {
			if(armor>0) {
				armor--;
			}else{
				life--;
			}
		}
		
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public boolean isTripleShot() {
		return tripleShot;
	}

	public void setTripleShot(boolean tripleShot) {
		this.tripleShot = tripleShot;
	}

}
