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
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import fr.mimus.render.Renderer;

public class Model {
	public static Model bar = new Model("res/Models/bar.png", .25f);
	public static Model table = new Model("res/Models/table.png", .25f);
	public static Model stool = new Model("res/Models/stool.png", .125f);
	public static Model bed = new Model("res/Models/bed.png", .2f);
	public static Model fund = new Model("res/Models/fund.png", .025f);
	public static Model sink = new Model("res/Models/sink.png", .04f);
	
	int[] pixels = null;
	int width;
	int height;
	int index;
	float size = .025f;
	public Model(String path) {
		this(path, .025f);
	}
	public Model(String path, float s) {
		size=s;
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			width=image.getWidth();
			height=image.getHeight();
			pixels = new int[width*height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			build(size);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void build(float size) {
		index = glGenLists(1);
		glNewList(index, GL_COMPILE);
		glBegin(GL_QUADS);
		for(int i = 0; i<pixels.length; i++) {
			int x = i % 32;
			int y = i / (32 * 32);
			int z = i / 32 - y * 32;
			int yy = 19-y;
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			if((a<0 || a>0) && !(r == 255 && g == 0 && b == 255)) {
				Renderer.cube(new Vector3f(x*size - 16f*size,yy*size,z*size - 16f*size),
						new Vector3f(size, size, size), 
						new Vector4f(r/255f, g/255f, b/255f, 1), 0);
			}
				
			
		}
		glEnd();
		glEndList();
	}
	
	public void render(Vector3f pos, int rotateY) {
		glPushMatrix();
			glTranslatef(pos.x,pos.y,pos.z);
			glRotatef(rotateY, 0, 1, 0);
			glCallList(index);
		glPopMatrix();
		
	}
	
	public void dispose() {
		glDeleteLists(index, 1);
	}
	public float getWidth() {
		return width * size;
	}
	public float getHeight() {
		return height * size;
	}
	public float getSize() {
		return size;
	}
	public int getIndex() {
		return index;
	}
	
	public static void disposeAll() {
		bar.dispose();
		table.dispose();
		stool.dispose();
		bed.dispose();
		fund.dispose();
		sink.dispose();
	}
}
