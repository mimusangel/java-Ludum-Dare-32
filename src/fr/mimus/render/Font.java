package fr.mimus.render;

import static org.lwjgl.opengl.GL11.*;

public class Font {
	private static String chars = "0123456789"
			+ "abcdefghij"
			+ "klmnopqrst"
			+ "uvwxyz    "
			+ "ABCDEFGHIJ"
			+ "KLMNOPQRST"
			+ "UVWXYZ    "
			+ ".:/\\?!,;[]"
			+ "=+-_><(){}"
			+ "'\"@       ";
	
	public static void drawString(String txt, int x, int y, int size) {
		float tx = 7f/(float)Texture.font.getWidth();
		float ty = 7f/(float)Texture.font.getHeight();
		
		Texture.font.bind();
		glBegin(GL_QUADS); 
			for(int i=0; i<txt.length(); i++) {
				int index = chars.indexOf(txt.charAt(i));
				int xt = index % 10;
				int yt = index / 10;

				glTexCoord2f(xt*tx,yt*ty); 			glVertex2f(x+i*size,y);
				glTexCoord2f(xt*tx+tx,yt*ty); 		glVertex2f(x+i*size+size,y);
				glTexCoord2f(xt*tx+tx,yt*ty+ty); 	glVertex2f(x+i*size+size,y+size);
				glTexCoord2f(xt*tx,yt*ty+ty); 		glVertex2f(x+i*size,y+size);
			}
		glEnd();
		Texture.unbind();
	}
}
