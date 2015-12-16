package fr.mimus.game.managers;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.mimus.game.Option;
import fr.mimus.render.Texture;

public class ParticuleManager {
	private static ParticuleManager instance;
	public static ParticuleManager getInstance() {
		return instance;
	}
	Random rand = new Random();
	ArrayList<Particle> particles = new ArrayList<Particle>();
	
	public ParticuleManager() {
		instance=this;
	}
	
	public void render() {
		if(!Option.isParticles()) return;
		for(int i=0; i<particles.size(); i++) {
			particles.get(i).render();
		}
	}
	
	public void update() {
		if(!Option.isParticles()) return;
		for(int i=0; i<particles.size(); i++) {
			Particle p = particles.get(i);
			if(p.isDead()) {
				particles.remove(p);
			} else {
				p.update();
			}
		}
	}
	
	public void clear() {
		if(!Option.isParticles()) return;
		particles.clear();
	}
	
	public void addParticle(Vector3f start, Vector2f rot, Vector3f color, float impulse, float speed, long life) {
		if(!Option.isParticles()) return;
		particles.add(new Particle(start, rot, color, impulse, speed, life));
	}
	
	public void addParticle(Vector3f start, Vector2f rot, float impulse, float speed, long life) {
		addParticle(start, rot, new Vector3f(rand.nextInt(255)/255f, rand.nextInt(255)/255f, rand.nextInt(255)/255f),impulse, speed, life);
	}
	
	public void addRandomizedParticle(Vector3f start, float impulse, float speed, long life) {
		int rx = rand.nextInt(90) + rand.nextInt(180) - rand.nextInt(90) + rand.nextInt(180);
		int ry = rand.nextInt(90) + rand.nextInt(180) - rand.nextInt(90) + rand.nextInt(180);
		addParticle(start, new Vector2f(rx, ry), new Vector3f(rand.nextInt(255)/255f, rand.nextInt(255)/255f, rand.nextInt(255)/255f), impulse, speed, life);
	}
	
	public void addRandomizedParticles(Vector3f start, float impulse, float speed, long life, int number) {
		for(int i=0; i<number; i++) {
			addRandomizedParticle(start, impulse, speed, life);
		}
	}
	
	public void addRandomizedParticlesByTexture(Vector3f start, float impulse, float speed, long life, int number, Texture tex) {
		for(int i=0; i<number; i++) {
			int rx = rand.nextInt(90) + rand.nextInt(180) - rand.nextInt(90) + rand.nextInt(180);
			int ry = rand.nextInt(90) + rand.nextInt(180) - rand.nextInt(90) + rand.nextInt(180);
			int x =0, y = 0;
			do {
				x = rand.nextInt(tex.getWidth());
				y = rand.nextInt(tex.getHeight());
			} while(tex.getAlpha(x, y) == 0);
			Vector3f c = tex.getRGB(x, y);
			c.x/=255f;
			c.y/=255f;
			c.z/=255f;
			addParticle(start, new Vector2f(rx, ry), c, impulse, speed, life);
		}
	}
}
