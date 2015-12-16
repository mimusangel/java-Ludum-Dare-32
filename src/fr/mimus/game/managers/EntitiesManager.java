package fr.mimus.game.managers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.mimus.game.Game;
import fr.mimus.game.entities.Entity;
import fr.mimus.game.entities.EntityBullet;
import fr.mimus.game.entities.EntityIA;
import fr.mimus.game.entities.EntityItem;
import fr.mimus.game.entities.EntityPlayerSP;

public class EntitiesManager {
	private static EntitiesManager instance = null;
	public static EntitiesManager getInstance() {
		return instance;
	}
	
	Map<Integer, Entity> entities = null;
	EntityPlayerSP localPlayer;
	Game game;
	Random rand;
	public EntitiesManager(Game g) {
		instance=this;
		rand = new Random();
		game=g;
		entities = new ConcurrentHashMap<Integer, Entity>();
		
	}
	
	public void render() {
		getLocalPlayer().render();
		for(Entity e : entities.values()) {
			if(e != null) {
				if(!(e instanceof EntityPlayerSP)) e.render();
			}
		}
	}
	
	public void update() {
		for(Entry<Integer, Entity> ent : entities.entrySet()) {
			Entity e = ent.getValue();
			if(e != null) {
				if(!e.isAlive()) {
					entities.remove(ent.getKey());
				}
			}
		}
		for(Entity e : entities.values()) {
			if(e != null) {
				if(e.isAlive()) {
					e.update();
				}
				
			}
		}
	}
	
	public void dispose() {
		clear();
		entities = null;
		localPlayer = null;
	}
	
	public EntityPlayerSP getLocalPlayer() {
		if(localPlayer == null) {
			localPlayer = new EntityPlayerSP(0, new Vector3f(0,0,0), new Vector2f(0,135f), "localPlayer");
			entities.put(0, localPlayer);
		}
		return localPlayer;
	}

	public Game getGame() {
		return game;
	}

	public int getNewID() {
		int id;
		do {
			id=rand.nextInt(Integer.MAX_VALUE-1)+1;
		} while(entities.get(id) != null);
		return id;
	}
	
	public void addEntityIA(float x, float y, float z) {
		int id = getNewID();
		entities.put(id, new EntityIA(id, new Vector3f(x, y, z), new Vector2f()));
	}
	
	public void addTripleBullet(Entity e) {
		int id = getNewID();
		Vector3f v = new Vector3f(e.getPos());
		v.setY(e.getPos().y + e.getHeight()*.88f);
		entities.put(id, new EntityBullet(id, v, new Vector2f(e.getRot()), e));
		
		id = getNewID();
		Vector2f r = new Vector2f(e.getRot());
		r.y += 6;
		entities.put(id, new EntityBullet(id, new Vector3f(v), r, e));
		
		id = getNewID();
		r = new Vector2f(e.getRot());
		r.y -= 6;
		entities.put(id, new EntityBullet(id, new Vector3f(v), r, e));
	}
	
	public void addBullet(Entity e) {
		int id = getNewID();
		Vector3f v = new Vector3f(e.getPos());
		v.setY(e.getPos().y + e.getHeight()*.88f);
		entities.put(id, new EntityBullet(id, v, new Vector2f(e.getRot()), e));
	}

	public void addBullet(Entity e, Vector2f rot) {
		int id = getNewID();
		Vector3f v = new Vector3f(e.getPos());
		v.setY(e.getPos().y + e.getHeight()*.88f);
		entities.put(id, new EntityBullet(id, v, new Vector2f(rot), e));
	}

	public boolean shot(EntityBullet entity) {
		for(Entity e : entities.values()) {
			if(e != null && !(e instanceof EntityBullet) && !(e instanceof EntityItem) && e != entity && e != entity.getSource()) {
				if(entity.getPos().y>=e.getPos().y && entity.getPos().y<e.getPos().y + e.getHeight()) {
					float dist = Math.abs((e.getPos().x+e.getRadius()/2) - (entity.getPos().x+entity.getRadius()/2)) 
							+ Math.abs((e.getPos().z+e.getRadius()/2) - (entity.getPos().z+entity.getRadius()/2));
					
					if(dist<= e.getRadius()+entity.getRadius()) {
						if(e instanceof EntityIA && e.isAlive()) {
							((EntityIA) e).looseLife(1);
							return true;
						}
						if(e instanceof EntityPlayerSP && ((EntityPlayerSP) e).getLife()>0) {
							getLocalPlayer().looseLife(1);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public int getEntitySize() {
		return entities.size();
	}

	public void clear() {
		entities.clear();
	}
	

	public boolean hasEntityIA() {
		int eia = 0;
		for(Entity e : entities.values()) {
			if(e instanceof EntityIA) {
				if(!((EntityIA) e).isDead()) eia++;
			}
		}
		return eia>0;
	}
	
	
	public int getEntitySizeALive() {
		int s = 0;
		for(Entity e : entities.values()) {
			if(e != null) {
				if(e instanceof EntityIA) {
					if(!((EntityIA) e).isDead()) {
						s++;
					}
				} else {
					if(e.isAlive()) {
						s++;
					}
				}
			}
		}
		return s;
	}
	
	public void addLoot(Vector3f pos) {
		int id = getNewID();
		Vector3f p = new Vector3f(pos);
		p.x += (rand.nextFloat()*2)-1;
		p.z += (rand.nextFloat()*2)-1;
		entities.put(id, new EntityItem(id, p, rand.nextInt(6), rand.nextInt(3)+1));
	}

	public void clearExceptPlayer() {
		entities.clear();
		entities.put(0, localPlayer);
	}
}
