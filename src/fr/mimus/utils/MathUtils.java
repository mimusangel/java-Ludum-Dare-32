package fr.mimus.utils;

import org.lwjgl.util.vector.Vector2f;

public class MathUtils {
	public static double getRotate(Vector2f a, Vector2f b) {
		return Math.toDegrees(Math.atan2(b.y-a.y, b.x-a.x));
	}
}
