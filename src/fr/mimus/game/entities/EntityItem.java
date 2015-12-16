package fr.mimus.game.entities;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.mimus.game.Audio;
import fr.mimus.game.Game;
import fr.mimus.game.Item3D;
import fr.mimus.game.inventory.Items;

public class EntityItem extends Entity {

	int item, number;
	boolean dead = false;
	public EntityItem(int id, Vector3f p, int item, int number) {
		super(id, p, new Vector2f());
		this.item=item;
		this.number=number;
		this.setRadius(1f);
	}

	public void update() {
		if(isAlive()) {
			EntityPlayerSP player = Game.getInstance().getEntitiesManager().getLocalPlayer();
			this.rot.y = -player.rot.y;
			this.rot.x = player.rot.x;
			
			float dist = Math.abs(player.pos.x - pos.x) + Math.abs(player.pos.z - pos.z);
			if(dist<=player.getRadius()+this.getRadius()) {
				player.getInventory().addItem(item, number);
				Audio.pickup.play();
				dead=true;
				return;
			}
		}
	}

	public void render() {
		glPushMatrix();
		glTranslatef(pos.x, pos.y, pos.z);
		glRotatef(this.rot.y, 0, 1, 0);
		glRotatef(90, 1, 0, 0);
		glTranslatef(-pos.x, -pos.y, -pos.z);
		Item3D item3D = Item3D.pate;
		if(item == Items.PATE) {
			item3D = Item3D.pate;
		}
		if(item == Items.EGG) {
			item3D = Item3D.egg;
		}
		if(item == Items.TOMATO) {
			item3D = Item3D.tomato;
		}
		if(item == Items.STEAK) {
			item3D = Item3D.steak;
		}
		if(item == Items.PEPPER) {
			item3D = Item3D.pepper;
		}
		if(item == Items.CHEESE) {
			item3D = Item3D.cheese;
		}
		glTranslatef(pos.x-item3D.getWidth()/2, pos.y, pos.z-item3D.getHeight());
		
		item3D.render();
		glPopMatrix();

	}

	public boolean isAlive() {
		return !dead;
	}
}
