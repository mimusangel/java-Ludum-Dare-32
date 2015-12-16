package fr.mimus.game.entities;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.mimus.game.Audio;
import fr.mimus.game.Game;
import fr.mimus.game.Item3D;
import fr.mimus.game.managers.ParticuleManager;
import fr.mimus.render.Texture;
import fr.mimus.utils.MathUtils;

public class EntityIA extends Entity {

	boolean dead = false;
	long time;
	long timeShot;
	long timeDead = 0;
	Random rand;
	Vector3f dir = new Vector3f();
	
	int armor = 0;

	Item3D skin;
	Item3D skinArmor;
	Item3D skinDead;
	public EntityIA(int id, Vector3f p, Vector2f r) {
		super(id, p, r);
		this.setHeight(2.2f);
		this.setRadius(.75f);
		time=System.currentTimeMillis()-2000;
		timeShot = System.currentTimeMillis();
		rand = new Random();
		int a = rand.nextInt(3);
		armor = rand.nextInt(2);
		if(a == 2) {
			skin = Item3D.worlda;
			skinArmor = Item3D.worldaArmor;
			skinDead = Item3D.worldaDead;
		} else if(a == 1) {
			skin = Item3D.galliano;
			skinArmor = Item3D.gallianoArmor;
			skinDead = Item3D.gallianoDead;
		} else {
			skin = Item3D.luigi;
			skinArmor = Item3D.luigiArmor;
			skinDead = Item3D.luigiDead;
		}
	}

	@Override
	public void update() {
		if(!dead) {
			EntityPlayerSP player = Game.getInstance().getEntitiesManager().getLocalPlayer();
			this.rot.y = -player.rot.y;
			this.rot.x = player.rot.x;
			
			if(System.currentTimeMillis()-time>1000) {
				time+=1000;
				float d = Math.abs(player.pos.x - pos.x) + Math.abs(player.pos.z - pos.z);
				if(d<=15) {
					this.rot.y = (float) MathUtils.getRotate(new Vector2f(player.pos.x, player.pos.z), new Vector2f(pos.x, pos.z)) -90;
					this.rot.y += rand.nextInt(6)-3; 
					dir = this.getForward();
					this.rot.y = -player.rot.y;
				} else {
					dir.x = (float) rand.nextFloat()*2f-1f;
					dir.y = (float) rand.nextFloat()*2f-1f;
					dir.z = (float) rand.nextFloat()*2f-1f;
				}
				
			} else {
				this.move(dir, .1f);
			}
			
			if(System.currentTimeMillis()-timeShot>500) {
				timeShot=System.currentTimeMillis();
				float d = Math.abs(player.pos.x - pos.x) + Math.abs(player.pos.z - pos.z);
				if(d<=30) {
					this.setHeight(1.75f);
					Vector2f r = new Vector2f();
					r.y = (float) MathUtils.getRotate(new Vector2f(player.pos.x, player.pos.z), new Vector2f(pos.x, pos.z)) -90;
					r.y += rand.nextInt(6)-3; 
					Game.getInstance().getEntitiesManager().addBullet(this, r);
					this.setHeight(2.2f);
				}
			}
		}
	}

	@Override
	public void render() {
		glPushMatrix();
			if(dead) {
				//Texture.luigidead.bind();
				glTranslatef(pos.x, pos.y, pos.z);
				//glRotatef(1, 0, 1, 0);
				glRotatef(this.rot.y, 0, 1, 0);
				glTranslatef(-pos.x, -pos.y, -pos.z);
				glTranslatef(pos.x-skinDead.getWidth()/2, pos.y, pos.z-skinDead.getHeight());

				skinDead.render();

			} else {
				
				glTranslatef(pos.x, pos.y, pos.z);
				glPushMatrix();
					glRotatef(this.rot.y, 0, 1, 0);
					glRotatef(90, 1, 0, 0);
					glTranslatef(-skin.getWidth()/2, 0, -skin.getHeight());
					if(armor>0) {
						skinArmor.render();
					} else {
						skin.render();
					}
				glPopMatrix();
				//Texture.unbind();
			}
			
			
		glPopMatrix();
	}

	public boolean isAlive(){
		if(dead && System.currentTimeMillis()-timeDead < 30000) return true;
		return !dead;
	}

	public boolean isDead(){
		return dead;
	}
	public void setDead(boolean d) {
		if(!this.dead && d) {
			Audio.hit.play();
			Game.getInstance().getEntitiesManager().addLoot(this.getPos());
			Vector3f p = new Vector3f(this.pos);
			p.y += 1f;
			ParticuleManager.getInstance().addRandomizedParticlesByTexture(p, .3f, .035f, 5000, 100, Texture.pizza0);
		}
		this.dead = d;
		if(this.dead) {
			timeDead = System.currentTimeMillis();
		}
			
	}

	@Override
	public void looseLife(int i) {
		if(armor>0) {
			armor--;
		} else {
			setDead(true);
		}
		
	}

}
