package fr.mimus.game.entities;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.mimus.game.Game;
import fr.mimus.game.Item3D;
import fr.mimus.game.managers.ParticuleManager;
import fr.mimus.render.Texture;

public class EntityBullet extends Entity {

	boolean dead = false;
	int dist = 0;
	Entity source;
	public EntityBullet(int id, Vector3f p, Vector2f r, Entity src) {
		super(id, p, r);
		source=src;
		this.setRadius(.3f);
		Vector3f dir = this.getForward();
		pos.x += dir.x * .15f;
		pos.z += dir.z * .15f;
		
	}

	public void update() {
		if(dead) return;
		for(int i = 0; i < 10; i++) {
			if(pos.y<0 || dist > 1000) {
				dead = true;
				return;
			}
			
			if(this.isBlocked(Game.getInstance().getLevel(), pos)) {
				dead = true;
				return;
			}
			
			if(Game.getInstance().getEntitiesManager().shot(this)) {
				if(source instanceof EntityPlayerSP) {
					((EntityPlayerSP) source).addScore(10);
				}
				dead = true;
				return;
			}
			
			Vector3f dir = this.getForward();
			pos.x += dir.x * .075f;
			//pos.y -= (float) Math.sin(dist/50*Math.PI) * .05f;
			pos.z += dir.z * .075f;
			dist++;
		}
		ParticuleManager.getInstance().addRandomizedParticlesByTexture(pos, .3f, .035f, 2500, 1, Texture.pizza0);
	}

	public void render() {
		glPushMatrix();
		//pizza
		//glRotatef(rot.x, 1, 0, 0);
		//glRotatef(rot.y, 0, 1, 0);

		glTranslatef(pos.x-Item3D.pizza.getWidth()/2, 
				pos.y-Item3D.pizza.getSize()/2,
				pos.z-Item3D.pizza.getHeight()/2);
		Item3D.pizza.render();
		glColor4f(1,1,1,1);
		glPopMatrix();
	}

	public boolean isAlive() {
		return !dead;
	}

	public Entity getSource() {
		return source;
	}

	public void setSource(Entity source) {
		this.source = source;
	}
}
