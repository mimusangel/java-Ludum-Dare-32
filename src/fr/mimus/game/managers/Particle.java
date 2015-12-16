package fr.mimus.game.managers;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.mimus.game.entities.EntityPlayerSP;

public class Particle {
	long lastTime;
	Vector3f pos;
	Vector2f rot;
	Vector3f color;
	float impulse = 0.0f;
	float impulseBase;
	
	float speed;
	long life;
	
	boolean wait = false;
	EntityPlayerSP player;
	public Particle(Vector3f start, Vector2f rot, Vector3f color, float imp, float speed, long life) {
		this.pos=new Vector3f(start);
		this.rot=new Vector2f(rot);
		this.color=new Vector3f(color);
		this.impulseBase=imp;
		this.speed=speed;
		this.life=life;
		
		player = EntitiesManager.getInstance().getLocalPlayer();
		lastTime = System.currentTimeMillis();
	}
	
	public void render() {
		if(player == null) player = EntitiesManager.getInstance().getLocalPlayer();
		
		glPushMatrix();
			glTranslatef(pos.x, pos.y, pos.z);
			glPushMatrix();
				glRotatef(-player.getRot().x, 1, 0, 0);
				glRotatef(-player.getRot().y, 0, 1, 0);
				glBegin(GL_QUADS);
				glColor4f(color.x, color.y, color.z, 1);
				glVertex3f(-.025f, -.025f, 0);
				glVertex3f(.025f, -.025f, 0);
				glVertex3f(.025f, .025f, 0);
				glVertex3f(-.025f, .025f, 0);
				glEnd();
			glPopMatrix();
		glPopMatrix();
	}
	
	public void update() {
		if(wait) return;
		if(impulse == 0.0f) {
			impulse += impulseBase;
		}
		Vector3f move = getForward();
		pos.x += move.x*speed;
		pos.y += move.z*speed;
		pos.z += move.z*speed;
		
		impulse -= speed;
		pos.y += impulse;
		
		if(pos.y<0) {
			pos.y = 0;
			wait=true;
			//impulseBase*=.90f;
			//impulse += impulseBase;
			//pos.y = 0;
		}
	}
	
	public Vector3f getForward() {
		Vector3f dir = new Vector3f();
		float cosY = (float)Math.cos(Math.toRadians(rot.y-90));
		float sinY = (float)Math.sin(Math.toRadians(rot.y-90));
		float cosX = (float)Math.cos(Math.toRadians(-rot.x));
		float sinX = (float)Math.sin(Math.toRadians(-rot.x));
	
		dir.x = cosY * cosX;
		dir.y = sinX;
		dir.z = sinY * cosX;
		
		return dir;
	}
	
	public boolean isDead() {
		return System.currentTimeMillis() - lastTime >= life;
	}
}
