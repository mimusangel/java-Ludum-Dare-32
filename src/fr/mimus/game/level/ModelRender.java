package fr.mimus.game.level;

import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.util.vector.Vector3f;

public class ModelRender {
	int index;
	Vector3f pos;
	int rotateY=0;
	
	public ModelRender(int i, Vector3f p, int r) {
		this.index=i;
		this.pos=p;
		this.rotateY=r;
	}
	
	public void render() {
		glPushMatrix();
			glTranslatef(pos.x,pos.y,pos.z);
			glRotatef(rotateY, 0, 1, 0);
			glCallList(index);
		glPopMatrix();
	}
}
