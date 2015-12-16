package fr.mimus.game;

import fr.mimus.render.Renderer;
import fr.mimus.utils.LibUtils;
import fr.mimus.utils.Window;

public class Main {
	Game game;
	public static boolean needScreen = false;
	public Main(String title, int width, int height, boolean resizable) {
		Window.create(title, width, height, resizable);
		Window.enableFog(.035f, new float[] {0,0,0,1});
		init();
	}
	
	public void init() {
		game = new Game();
		loop();
	}
	
	public void loop() {
		long lastTickTime = System.nanoTime();
		double tickTime = 1000000000f/60f;
		
		long lastTime = System.currentTimeMillis();
		
		int lastFrames = 60;
		int lastTicks = 60;
		int frames = 0;
		int ticks = 0;
		while(Window.isRunning()) {
			if(System.nanoTime()-lastTickTime>=tickTime) {
				lastTickTime+=tickTime;
				game.update();
				ticks++;
			} else {
				Window.clearBuffer();
				Window.reziseViewPort();
				Window.perspective();
				game.render3D();
				Window.ortho();
				if(!needScreen) game.render2D(lastFrames, lastTicks);
				Window.update();
				if(needScreen) {
					Renderer.screenshot();
					needScreen=false;
				}
				frames++;
			}
			
			if(System.currentTimeMillis()-lastTime>=1000) {
				lastTime+=1000;
				lastFrames=frames;
				lastTicks=ticks;
				frames=0;
				ticks=0;
			}
		}
		dispose();
	}
	
	public void dispose() {
		game.dispose();
		Window.dispose();
		System.exit(0);
	}
	
	
	public static void main(String[] args) {
		LibUtils.addLWJGLNative("natives/");
		new Main("Pizza Predator", 720, 720*9/16, true);

	}

}
