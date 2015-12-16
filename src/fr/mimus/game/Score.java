package fr.mimus.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Score {
	private static int best = 0;
	private static int last = 0;
	
	public static boolean addScore(int score) {
		if(score>best) {
			last=best;
			best=score;
			save();
			return true;
		}else if(score>last) {
			last=score;
			save();
			return true;
		}
		return false;
	}
	
	public static void save() {
		File file = new File("score.opt");
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			try {
				writer.write("best="+best+"\n");
				writer.write("last="+last+"\n");
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void load() {
		if(!new File("score.opt").exists()) {
			save();
			return;
		}
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("score.opt"));
			try {
				String line = "";
				while((line = reader.readLine()) != null) {
					String values[] = line.split("=");
					if(values[0].equalsIgnoreCase("best")) {
						best = Integer.valueOf(values[1]);
					} else if(values[0].equalsIgnoreCase("last")) {
						last = Integer.valueOf(values[1]);
					}
				}
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getBest() {
		return best;
	}

	public static int getLast() {
		return last;
	}
}
