package fr.mimus.render;

import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL11.glReadPixels;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Renderer {
	
	public static void plane(Vector3f pos, Vector3f size, Vector4f color, int id) {
		float tx = 32f/(float)Texture.texture.getWidth();
		float ty = 32f/(float)Texture.texture.getHeight();
		
		int xt = id % 8;
		int yt = id / 8;
		glColor4f(color.x, color.y, color.z, color.w);
		glTexCoord2f(xt*tx,yt*ty); 			glVertex3f(pos.x, pos.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty); 		glVertex3f(pos.x+size.x, pos.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty+ty); 	glVertex3f(pos.x+size.x, pos.y, pos.z+size.z);
		glTexCoord2f(xt*tx,yt*ty+ty); 		glVertex3f(pos.x, pos.y, pos.z+size.z);
	}
	
	public static void quad(Vector3f pos, Vector3f pos2, Vector4f color, int id) {
		float tx = 32f/(float)Texture.texture.getWidth();
		float ty = 32f/(float)Texture.texture.getHeight();
		
		int xt = id % 8;
		int yt = id / 8;
		glColor4f(color.x, color.y, color.z, color.w);
		glTexCoord2f(xt*tx,yt*ty); 			glVertex3f(pos.x, pos.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty); 		glVertex3f(pos2.x, pos.y, pos2.z);
		glTexCoord2f(xt*tx+tx,yt*ty+ty); 	glVertex3f(pos2.x, pos2.y, pos2.z);
		glTexCoord2f(xt*tx,yt*ty+ty); 		glVertex3f(pos.x, pos2.y, pos.z);
	}
	
	public static void cube(Vector3f pos, Vector3f size, Vector4f color, int id) {
		if(size.y == 0) {
			plane(pos, size, color, id);
			return;
		}
		float tx = 32f/(float)Texture.texture.getWidth();
		float ty = 32f/(float)Texture.texture.getHeight();
		
		int xt = id % 8;
		int yt = id / 8;
		
		glColor4f(color.x*.95f, color.y*.95f, color.z*.95f, color.w);
		glTexCoord2f(xt*tx,yt*ty); 			glVertex3f(pos.x, pos.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty); 		glVertex3f(pos.x+size.x, pos.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty+ty); 	glVertex3f(pos.x+size.x, pos.y+size.y, pos.z);
		glTexCoord2f(xt*tx,yt*ty+ty); 		glVertex3f(pos.x, pos.y+size.y, pos.z);
		
		glColor4f(color.x*.85f, color.y*.85f, color.z*.85f, color.w);
		glTexCoord2f(xt*tx,yt*ty); 			glVertex3f(pos.x, pos.y, pos.z+size.z);
		glTexCoord2f(xt*tx+tx,yt*ty); 		glVertex3f(pos.x+size.x, pos.y, pos.z+size.z);
		glTexCoord2f(xt*tx+tx,yt*ty+ty); 	glVertex3f(pos.x+size.x, pos.y+size.y, pos.z+size.z);
		glTexCoord2f(xt*tx,yt*ty+ty); 		glVertex3f(pos.x, pos.y+size.y, pos.z+size.z);

		glColor4f(color.x*.90f, color.y*.90f, color.z*.90f, color.w);
		glTexCoord2f(xt*tx,yt*ty); 			glVertex3f(pos.x, pos.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty); 		glVertex3f(pos.x, pos.y, pos.z+size.z);
		glTexCoord2f(xt*tx+tx,yt*ty+ty); 	glVertex3f(pos.x, pos.y+size.y, pos.z+size.z);
		glTexCoord2f(xt*tx,yt*ty+ty); 		glVertex3f(pos.x, pos.y+size.y, pos.z);

		glTexCoord2f(xt*tx,yt*ty); 			glVertex3f(pos.x+size.x, pos.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty); 		glVertex3f(pos.x+size.x, pos.y, pos.z+size.z);
		glTexCoord2f(xt*tx+tx,yt*ty+ty); 	glVertex3f(pos.x+size.x, pos.y+size.y, pos.z+size.z);
		glTexCoord2f(xt*tx,yt*ty+ty); 		glVertex3f(pos.x+size.x, pos.y+size.y, pos.z);

		glColor4f(color.x, color.y, color.z, color.w);
		glTexCoord2f(xt*tx,yt*ty); 			glVertex3f(pos.x, pos.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty); 		glVertex3f(pos.x+size.x, pos.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty+ty); 	glVertex3f(pos.x+size.x, pos.y, pos.z+size.z);
		glTexCoord2f(xt*tx,yt*ty+ty); 		glVertex3f(pos.x, pos.y, pos.z+size.z);

		glTexCoord2f(xt*tx,yt*ty); 			glVertex3f(pos.x, pos.y+size.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty); 		glVertex3f(pos.x+size.x, pos.y+size.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty+ty); 	glVertex3f(pos.x+size.x, pos.y+size.y, pos.z+size.z);
		glTexCoord2f(xt*tx,yt*ty+ty); 		glVertex3f(pos.x, pos.y+size.y, pos.z+size.z);
	}
	
	public static void cubeNoY(Vector3f pos, Vector3f size, Vector4f color, int id) {
		if(size.y == 0) {
			plane(pos, size, color, id);
			return;
		}
		float tx = 32f/(float)Texture.texture.getWidth();
		float ty = 32f/(float)Texture.texture.getHeight();
		
		int xt = id % 8;
		int yt = id / 8;
		
		glColor4f(color.x*.95f, color.y*.95f, color.z*.95f, color.w);
		glTexCoord2f(xt*tx,yt*ty); 			glVertex3f(pos.x, pos.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty); 		glVertex3f(pos.x+size.x, pos.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty+ty); 	glVertex3f(pos.x+size.x, pos.y+size.y, pos.z);
		glTexCoord2f(xt*tx,yt*ty+ty); 		glVertex3f(pos.x, pos.y+size.y, pos.z);
		
		glColor4f(color.x*.85f, color.y*.85f, color.z*.85f, color.w);
		glTexCoord2f(xt*tx,yt*ty); 			glVertex3f(pos.x, pos.y, pos.z+size.z);
		glTexCoord2f(xt*tx+tx,yt*ty); 		glVertex3f(pos.x+size.x, pos.y, pos.z+size.z);
		glTexCoord2f(xt*tx+tx,yt*ty+ty); 	glVertex3f(pos.x+size.x, pos.y+size.y, pos.z+size.z);
		glTexCoord2f(xt*tx,yt*ty+ty); 		glVertex3f(pos.x, pos.y+size.y, pos.z+size.z);

		glColor4f(color.x*.90f, color.y*.90f, color.z*.90f, color.w);
		glTexCoord2f(xt*tx,yt*ty); 			glVertex3f(pos.x, pos.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty); 		glVertex3f(pos.x, pos.y, pos.z+size.z);
		glTexCoord2f(xt*tx+tx,yt*ty+ty); 	glVertex3f(pos.x, pos.y+size.y, pos.z+size.z);
		glTexCoord2f(xt*tx,yt*ty+ty); 		glVertex3f(pos.x, pos.y+size.y, pos.z);

		glTexCoord2f(xt*tx,yt*ty); 			glVertex3f(pos.x+size.x, pos.y, pos.z);
		glTexCoord2f(xt*tx+tx,yt*ty); 		glVertex3f(pos.x+size.x, pos.y, pos.z+size.z);
		glTexCoord2f(xt*tx+tx,yt*ty+ty); 	glVertex3f(pos.x+size.x, pos.y+size.y, pos.z+size.z);
		glTexCoord2f(xt*tx,yt*ty+ty); 		glVertex3f(pos.x+size.x, pos.y+size.y, pos.z);
	}
	
	public static void screenshot() {
		glReadBuffer(GL_FRONT);
		int width = Display.getWidth();
		int height = Display.getHeight();
		ByteBuffer buff = BufferUtils.createByteBuffer(width * height * 4);
		glReadPixels(0,0,width,height, GL_RGBA, GL_UNSIGNED_BYTE, buff);
		
		BufferedImage screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x<width; x++) {
			for(int y = 0; y<height; y++) {
				int i = (x + (y*width)) * 4;
				int r = buff.get(i) & 0xff;
				int g = buff.get(i + 1) & 0xff;
				int b = buff.get(i + 2) & 0xff;
				screen.setRGB(x, height - (y+1), (0xff << 24) | (r << 16) | (g << 8) | b);
			}
		}
		int id = (new Random()).nextInt(Integer.MAX_VALUE);
		File file = new File("screen_"+id+".png");
		try {
			ImageIO.write(screen, "png", file);
		} catch (IOException e) {}
	}
}
