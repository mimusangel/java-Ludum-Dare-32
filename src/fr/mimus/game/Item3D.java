package fr.mimus.game;

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

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import fr.mimus.render.Renderer;
public class Item3D {
	public static Item3D pizza = new Item3D("res/pizza.png");
	public static Item3D firstPizza = new Item3D("res/pizza.png", .05f);
	
	public static Item3D luigi = new Item3D("res/luigi.png");
	public static Item3D luigiArmor = new Item3D("res/luigiarmor.png");
	public static Item3D luigiDead = new Item3D("res/luigidead.png");
	public static Item3D galliano = new Item3D("res/galliano.png");
	public static Item3D gallianoArmor = new Item3D("res/gallianoarmor.png");
	public static Item3D gallianoDead = new Item3D("res/gallianodead.png");
	public static Item3D worlda = new Item3D("res/worlda.png");
	public static Item3D worldaArmor = new Item3D("res/worldaarmor.png");
	public static Item3D worldaDead = new Item3D("res/worldadead.png");
	
	
	
	public static Item3D pate = new Item3D("res/pate.png", .05f);
	public static Item3D egg = new Item3D("res/egg.png", .05f);
	public static Item3D cheese = new Item3D("res/cheese.png", .05f);
	public static Item3D steak = new Item3D("res/steak.png", .05f);
	public static Item3D pepper = new Item3D("res/pepper.png", .05f);
	public static Item3D tomato = new Item3D("res/tomato.png", .05f);
	
	public static Item3D fence = new Item3D("res/fence.png", .15f);
	

	
	int[] pixels = null;
	int width;
	int height;
	int index;
	float size = .025f;
	public Item3D(String path) {
		this(path, .025f);
	}
	public Item3D(String path, float s) {
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
			int x = i % width;
			int z = i / width;
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			if(a<0 || a>0) {
				Renderer.cube(new Vector3f(x*size,0,z*size), new Vector3f(size, size, size), new Vector4f(r/255f, g/255f, b/255f, 1), 0);
			}
				
			
		}
		glEnd();
		glEndList();
	}
	
	public void render() {
		glCallList(index);
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
	
	public static void disposeAll() {
		pizza.dispose();
		firstPizza.dispose();
		luigi.dispose();
		luigiDead.dispose();
		galliano.dispose();
		gallianoDead.dispose();
		worlda.dispose();
		worldaDead.dispose();
		pate.dispose();
		egg.dispose();
		cheese.dispose();
		steak.dispose();
		pepper.dispose();
		tomato.dispose();
		fence.dispose();
	}
}
