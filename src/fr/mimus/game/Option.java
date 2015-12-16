package fr.mimus.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Option {

	private static boolean key_qwerty = false;
	private static int volume_effect = 75;
	private static int volume_music = 60;
	private static boolean particles = true;
	
	public static int getVolume_effect() {
		return volume_effect;
	}
	public static void setVolume_effect(int volume_effect) {
		Option.volume_effect = volume_effect;
	}
	public static boolean isKey_qwerty() {
		return key_qwerty;
	}
	public static void setKey_qwerty(boolean qwerty) {
		Option.key_qwerty = qwerty;
	}
	public static int getVolume_music() {
		return volume_music;
	}
	public static void setVolume_music(int volume_music) {
		Option.volume_music = volume_music;
	}
	
	public static void save() {
		File file = new File("option.opt");
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			try {
				writer.write("qwerty="+key_qwerty+"\n");
				writer.write("effect="+volume_effect+"\n");
				writer.write("music="+volume_music+"\n");
				writer.write("particles="+particles+"\n");
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void load() {
		if(!new File("option.opt").exists()) {
			save();
			return;
		}
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("option.opt"));
			try {
				String line = "";
				while((line = reader.readLine()) != null) {
					String values[] = line.split("=");
					if(values[0].equalsIgnoreCase("qwerty")) {
						key_qwerty = Boolean.valueOf(values[1]);
					} else if(values[0].equalsIgnoreCase("effect")) {
						volume_effect = Integer.valueOf(values[1]);
					} else if(values[0].equalsIgnoreCase("music")) {
						volume_music = Integer.valueOf(values[1]);
					} else if(values[0].equalsIgnoreCase("particles")) {
						particles = Boolean.valueOf(values[1]);
					}
				}
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean isParticles() {
		return particles;
	}
	public static void setParticles(boolean particles) {
		Option.particles = particles;
	}
}
