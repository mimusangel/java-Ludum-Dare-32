package fr.mimus.game.level;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glDeleteLists;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import fr.mimus.render.Renderer;
import fr.mimus.render.Texture;

public class Level {	
	int[] pixels = null;
	int width;
	int height;
	int index;
	int indexGlass;
	Vector3f spawn = new Vector3f();
	
	ArrayList<ModelRender> models = new ArrayList<ModelRender>();
	
	public Level(String path) {
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			width=image.getWidth();
			height=image.getHeight();
			if(width != 128 && height != 1280) {
				System.err.println("Level Format Not Valid! "+path);
			}
			pixels = new int[width*height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			build();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void build() {
		index = glGenLists(1);
		glNewList(index, GL_COMPILE);
		Texture.texture.bind();
		glBegin(GL_QUADS);
		for(int i = 0; i<pixels.length; i++) {
			int x = i % sizeX();
			int y = i / (sizeX() * sizeZ());
			int z = i / sizeX() - y * 128;
			if(pixels[i] != 0xffffffff) {
				int r = (pixels[i] & 0xff0000) >> 16;
				int g = (pixels[i] & 0xff00) >> 8;
				int b = (pixels[i] & 0xff);

				if(r==0 && g==0 && b==0) {
					Renderer.cube(new Vector3f(x*.5f, y*.5f, z*.5f), new Vector3f(.5f, .5f, .5f), new Vector4f(1,1,1,1), 9);
				}
				if(r==128 && g==128 && b==128) {
					Renderer.cube(new Vector3f(x*.5f, y*.5f, z*.5f), new Vector3f(.5f, .5f, .5f), new Vector4f(1,1,1,1), 3);
				}
				if(r==50 && g==50 && b==50) {
					Renderer.cube(new Vector3f(x*.5f, y*.5f, z*.5f), new Vector3f(.5f, .5f, .5f), new Vector4f(1,1,1,1), 10);
				}
				if(r==75 && g==75 && b==75) {
					Renderer.cube(new Vector3f(x*.5f, y*.5f, z*.5f), new Vector3f(.5f, .5f, .5f), new Vector4f(1,1,1,1), 11);
				}
				if(r==128 && g==106 && b==74) {
					Renderer.cube(new Vector3f(x*.5f, y*.5f, z*.5f), new Vector3f(.5f, .5f, .5f), new Vector4f(1,1,1,1), 12);
				}
				if(r==128 && g==64 && b==0) {
					Renderer.cube(new Vector3f(x*.5f, y*.5f, z*.5f), new Vector3f(.5f, .5f, .5f), new Vector4f(1,1,1,1), 4);
				}
				
				//106
				if((r==0 && g==255 && b==0) 
					|| (r==128 && g==0 && b==128) 
					|| (r==255 && g==255 && b==0)
					|| (r==255 && g==204 && b==153)
					|| (r==103 && g==69 && b==61)
					|| (r==204 && g==163 && b==255)
					|| (r==128 && g==0 && b==0)) {
					if((r==128 && g==0 && b==128)) spawn = (new Vector3f(x*.5f, y*.5f, z*.5f)); 
					Renderer.plane(new Vector3f(x*.5f, y*.5f, z*.5f), new Vector3f(.5f, 0, .5f), new Vector4f(.5f,.5f,.5f,1), 0);
					//Game.getInstance().getEntitiesManager().addEntityIA(x*.5f, 0f, z*.5f);
				}
				
			} else {
				if(y == 0) {
					Renderer.plane(new Vector3f(x*.5f, y*.5f, z*.5f), new Vector3f(.5f, 0, .5f), new Vector4f(.5f,.5f,.5f,1), 0);
				}
			}
		}
		
		/*for(int i = 0; i<pixels.length; i++) {
			int x = i % sizeX();
			int y = i / (sizeX() * sizeZ());
			int z = i / sizeX() - y * 128;
			if(pixels[i] != 0xffffffff) {
				int r = (pixels[i] & 0xff0000) >> 16;
				int g = (pixels[i] & 0xff00) >> 8;
				int b = (pixels[i] & 0xff);
				if(r==0 && g==0 && b==255) {
					Renderer.quad(new Vector3f(x*.5f, y*.5f, z*.5f+.25f), new Vector3f(x*.5f+.5f, y*.5f+.5f, z*.5f+.25f), new Vector4f(1,1,1,.5f), 2);
				}
				if(r==0 && g==0 && b==128) {
					Renderer.quad(new Vector3f(x*.5f+.25f, y*.5f, z*.5f), new Vector3f(x*.5f+.25f, y*.5f+.5f, z*.5f+.5f), new Vector4f(1,1,1,.5f), 2);
				}
			}
		}*/
		
		glEnd();
		Texture.unbind();
		glEndList();
		
		indexGlass = glGenLists(1);
		glNewList(indexGlass, GL_COMPILE);
		Texture.texture.bind();
		glBegin(GL_QUADS);
		for(int i = 0; i<pixels.length; i++) {
			int x = i % sizeX();
			int y = i / (sizeX() * sizeZ());
			int z = i / sizeX() - y * 128;
			if(pixels[i] != 0xffffffff) {
				int r = (pixels[i] & 0xff0000) >> 16;
				int g = (pixels[i] & 0xff00) >> 8;
				int b = (pixels[i] & 0xff);
				if(r==0 && g==0 && b==255) {
					Renderer.quad(new Vector3f(x*.5f, y*.5f, z*.5f+.25f), new Vector3f(x*.5f+.5f, y*.5f+.5f, z*.5f+.25f), new Vector4f(1,1,1,.5f), 2);
				}
				if(r==0 && g==0 && b==128) {
					Renderer.quad(new Vector3f(x*.5f+.25f, y*.5f, z*.5f), new Vector3f(x*.5f+.25f, y*.5f+.5f, z*.5f+.5f), new Vector4f(1,1,1,.5f), 2);
				}
			}
		}
		
		glEnd();
		Texture.unbind();
		glEndList();
		
		for(int i = 0; i<pixels.length; i++) {
			int x = i % sizeX();
			int y = i / (sizeX() * sizeZ());
			int z = i / sizeX() - y * 128;
			if(pixels[i] != 0xffffffff) {
				int r = (pixels[i] & 0xff0000) >> 16;
				int g = (pixels[i] & 0xff00) >> 8;
				int b = (pixels[i] & 0xff);
				if(r==255 && g==255 && b==0) {
					models.add(new ModelRender(Model.bar.getIndex(), new Vector3f(x*.5f, y*.5f, z*.5f), 0));
					
				}
				if(r==103 && g==69 && b==61) {
					models.add(new ModelRender(Model.table.getIndex(), new Vector3f(x*.5f, y*.5f, z*.5f), 0));
				}
				if(r==204 && g==163 && b==255) {
					models.add(new ModelRender(Model.stool.getIndex(), new Vector3f(x*.5f, y*.5f, z*.5f), 0));
				}
				if((r==128 && g==0 && b==0)) {
					models.add(new ModelRender(Model.bed.getIndex(), new Vector3f(x*.5f, y*.5f, z*.5f), 0));
				}
				
				if((r==226 && g==226 && b==226)) {
					models.add(new ModelRender(Model.fund.getIndex(), new Vector3f(x*.5f, y*.5f, z*.5f), 0));
				}
				
				if((r==0 && g==204 && b==255)) {
					models.add(new ModelRender(Model.sink.getIndex(), new Vector3f(x*.5f, y*.5f, z*.5f), 0));
				}
			}
		}
	}
	
	public void render() {
		glCallList(index);
		for(int i = 0; i<models.size(); i++) {
			models.get(i).render();
		}
	}
	
	public void renderGlass() {
		glCallList(indexGlass);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int sizeX() {
		return 128;
	}
	
	public int sizeZ() {
		return 128;
	}

	public void dispose() {
		glDeleteLists(index, 1);
	}

	public Vector3f isCollided(Vector3f pos) {
		int x = (int) (pos.x*2);
		int y = (int) (pos.y*2);
		int z = (int) (pos.z*2);
		
		int i = z*sizeZ() + y*sizeX()*sizeZ()+x;
		

		if(i<0) return new Vector3f();
		if(i>=pixels.length) return new Vector3f();
		
		int r = (pixels[i] & 0xff0000) >> 16;
		int g = (pixels[i] & 0xff00) >> 8;
		int b = (pixels[i] & 0xff);

		
		if(pixels[i] != 0xffffffff && !(r==0 && g==255 && b==0) && !(r==128 && g==0 && b==128)) {
			float xx = 0, yy = 0, zz = 0;
			
			if(pos.x>= x*.5f && pos.x<x*.5f+.25f) {
				xx = pos.x - (x*.5f);
			} else if(pos.x>= x*.5f+.25f && pos.x<x*.5f+.5f) { 
				xx = pos.x - (x*.5f+.5f);
			}
			
			if(pos.y>= y*.5f && pos.y<y*.5f+.25f) {
				yy = pos.y - (y*.5f);
			} else if(pos.y>= y*.5f+.25f && pos.y<y*.5f+.5f) { 
				yy = pos.y - (y*.5f+.5f);
			}
			
			if(pos.z>= z*.5f && pos.z<z*.5f+.25f) {
				zz = pos.z - (z*.5f);
			} else if(pos.z>= z*.5f+.25f && pos.z<z*.5f+.5f) { 
				zz = pos.z - (z*.5f+.5f);
			}
			//System.out.println("Level: >"+xx+","+yy+","+zz);
			return new Vector3f(xx, yy, zz);
		}
		return new Vector3f();
	}
	
	public boolean isBlocked(Vector3f pos) {
		int x = (int) (pos.x*2);
		int y = (int) (pos.y*2);
		int z = (int) (pos.z*2);
		
		int i = z*sizeZ() + y*sizeX()*sizeZ()+x;
		

		if(i<0) return true;
		if(i>=pixels.length) return true;
		
		int r = (pixels[i] & 0xff0000) >> 16;
		int g = (pixels[i] & 0xff00) >> 8;
		int b = (pixels[i] & 0xff);

		
		if(pixels[i] != 0xffffffff && !(r==0 && g==255 && b==0) && !(r==128 && g==0 && b==128)) {
			return true;
		}
		return false;
	}
	
	public boolean isSpawnable(int x, int z) {
		int i = z*sizeZ() +x;
		

		if(i<0) return false;
		if(i>=pixels.length) return false;
		
		int r = (pixels[i] & 0xff0000) >> 16;
		int g = (pixels[i] & 0xff00) >> 8;
		int b = (pixels[i] & 0xff);
		
		return (r == 0 && g == 255 && b == 0);
	}

	public Vector3f getSpawn() {
		return spawn;
	}
}
