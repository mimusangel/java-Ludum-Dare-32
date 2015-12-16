package fr.mimus.game.entities;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.mimus.game.Game;
import fr.mimus.game.level.Level;

public class Entity {
	int index;
	Vector3f pos;
	Vector2f rot;
	float height = 1.75f;
	float radius = .2f;
	public Entity(int id, Vector3f p, Vector2f r) {
		this.index=id;
		this.pos=p;
		this.rot=r;
	}

	public void update() {
		
	}
	
	public void render() {
		
	}
	
	public Vector3f isCollided(Level level, Vector3f p) {
		Vector3f px0 = new Vector3f(p);
		px0.x-=radius;
		Vector3f px1 = new Vector3f(p);
		px1.x+=radius;
		Vector3f pz0 = new Vector3f(p);
		pz0.z-=radius;
		Vector3f pz1 = new Vector3f(p);
		pz1.z+=radius;
		Vector3f c0 = level.isCollided(px0);
		Vector3f c1 = level.isCollided(px1);
		Vector3f c2 = level.isCollided(pz0);
		Vector3f c3 = level.isCollided(pz1);
		if(!vector3fIsZero(c0)) return c0;
		if(!vector3fIsZero(c1)) return c1;
		if(!vector3fIsZero(c2)) return c2;
		if(!vector3fIsZero(c3)) return c3;
		return new Vector3f();
	}
	
	public boolean isBlocked(Level level, Vector3f p) {
		Vector3f px0 = new Vector3f(p);
		px0.x-=radius;
		Vector3f px1 = new Vector3f(p);
		px1.x+=radius;
		Vector3f pz0 = new Vector3f(p);
		pz0.z-=radius;
		Vector3f pz1 = new Vector3f(p);
		pz1.z+=radius;
		if(level.isBlocked(px0)) return true;
		if(level.isBlocked(px1)) return true;
		if(level.isBlocked(pz0)) return true;
		if(level.isBlocked(pz1)) return true;
		return false;
	}
	
	
	private boolean vector3fIsZero(Vector3f v) {
		return (v.z==0 && v.y==0 && v.x==0);
	}
	
	public void move(Vector3f dir, float speed) {
		Level level = Game.getInstance().getLevel();
		Vector3f nPos = new Vector3f();
		nPos.x = pos.x + dir.x * speed;
		nPos.z = pos.z + dir.z * speed;
		
		if(nPos.x<0) nPos.x = 0;
		if(nPos.y<0) nPos.y = 0;
		if(nPos.x> level.sizeX()*.5f)nPos.x = level.sizeX()*.5f;
		if(nPos.z> level.sizeZ()*.5f)nPos.z = level.sizeZ()*.5f;
		
		if(!isBlocked(level, new Vector3f(nPos.x, pos.y, nPos.z))) {
			pos.x = nPos.x;
			pos.z = nPos.z;
		} else {
			if(!isBlocked(level, new Vector3f(pos.x, pos.y, nPos.z))) {
				pos.z = nPos.z;
			}
			if(!isBlocked(level, new Vector3f(nPos.x, pos.y, pos.z))) {
				pos.x = nPos.x;
			}
		}
	}
	
	public Vector3f getForward() {
		return getForward(false);
	}
	
	public Vector3f getForward(boolean euler) {
		Vector3f dir = new Vector3f();
		float cosY = (float)Math.cos(Math.toRadians(rot.y-90));
		float sinY = (float)Math.sin(Math.toRadians(rot.y-90));
		float cosX = (float)Math.cos(Math.toRadians(-rot.x));
		float sinX = (float)Math.sin(Math.toRadians(-rot.x));
		if(euler) {
			dir.x = cosY * cosX;
			dir.y = sinX;
			dir.z = sinY * cosX;
		} else {
			dir.x = cosY;
			dir.z = sinY;
		}
		return dir;
	}
		
	public Vector3f getSide(int angle) {
		Vector3f dir = new Vector3f();
		float cosY = (float)Math.cos(Math.toRadians(rot.y+angle));
		float sinY = (float)Math.sin(Math.toRadians(rot.y+angle));
		dir.x = cosY;
		dir.z = sinY;
		return dir;
	}
	
	public boolean isAlive() {
		return true;
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector2f getRot() {
		return rot;
	}

	public void setRot(Vector2f rot) {
		this.rot = rot;
	}

	public int getIndex() {
		return index;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public void looseLife(int i) {}
}
