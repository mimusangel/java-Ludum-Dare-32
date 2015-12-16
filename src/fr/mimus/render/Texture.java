package fr.mimus.render;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;
public class Texture {
	
	public static Texture backmenu = new Texture("res/backMenu.png");
	public static Texture font = new Texture("res/font.png");
	public static Texture gui = new Texture("res/gui.png");
	public static Texture texture = new Texture("res/texture.png");
	
	public static Texture pizza0 = new Texture("res/pizza.png");
	public static Texture pizza1 = new Texture("res/pizza1.png");
	public static Texture pizza2 = new Texture("res/pizza2.png");
	public static Texture pizza3 = new Texture("res/pizza3.png");
	public static Texture pizzaGold = new Texture("res/pizzaGold.png");
	public static Texture pizzaBox = new Texture("res/pizzaBox.png");
	
	public static Texture pate = new Texture("res/pate.png");
	public static Texture egg = new Texture("res/egg.png");
	public static Texture cheese = new Texture("res/cheese.png");
	public static Texture steak = new Texture("res/steak.png");
	public static Texture pepper = new Texture("res/pepper.png");
	public static Texture tomato = new Texture("res/tomato.png");

	public static Texture background = new Texture("res/background.png");
	public static Texture sky = new Texture("res/sky.png");
	public static Texture backhome1 = new Texture("res/backhome1.png");
	
	
	

	private int index;
	private int width, height;
	private int[] pixels = null;
	public Texture(String path) {
		
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			width=image.getWidth();
			height=image.getHeight();
			pixels = new int[width*height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int[] data = new int[width*height];
		for(int i = 0; i<data.length; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		int id  = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		IntBuffer buffer = (IntBuffer) BufferUtils.createIntBuffer(data.length).put(data).flip();
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		this.index=id;
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, index);
	}
	
	public static void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getAlpha(int x, int y) {
		return (pixels[x + y*getWidth()] & 0xff000000) >> 24;
	}
	
	public Vector3f getRGB(int x, int y) {
		Vector3f c = new Vector3f();
		int i = x + y*getWidth();
		c.x = (pixels[i] & 0xff0000) >> 16;
		c.y = (pixels[i] & 0xff00) >> 8;
		c.z = (pixels[i] & 0xff);
		return c;
	}
	
}
