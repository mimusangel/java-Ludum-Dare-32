package fr.mimus.game.controlers;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import fr.mimus.game.entities.EntityPlayerSP;
import fr.mimus.game.gui.GuiNextStage;
import fr.mimus.game.inventory.Items;
import fr.mimus.game.level.Level;
import fr.mimus.game.managers.ControlsManager;
import fr.mimus.game.managers.ParticuleManager;

public class StageControler extends Controler {

	int stage = 0;
	/*Level levels[] = new Level[]{
			new Level("res/levels/pizzeria.png"),
			new Level("res/levels/openspace.png"),
			new Level("res/levels/mimus.png")
	};*/
	ArrayList<Level> levels = new ArrayList<Level>();
	int levelIndex = 0;
	Random rand;
	public StageControler(ControlsManager cm) {
		super(cm);
		rand=new Random();
		File file = new File("res/levels/");
		String list[] = file.list();
		for(int i=0; i<list.length; i++) {
			if(list[i].endsWith(".png")) {
				levels.add(new Level("res/levels/"+list[i]));
			}
		}
	}

	@Override
	public void update() {
		if(!this.manager.getGame().getEntitiesManager().hasEntityIA()) {
			if(this.manager.getGame().getGui() == null) {
				this.manager.getGame().getEntitiesManager().getLocalPlayer().addScore(stage*5+5);
				this.manager.getGame().setGui(new GuiNextStage());
			}
		}

	}

	public Level getLevel() {
		return levels.get(levelIndex);
	}
	
	public void spawnStage() {
		for(int i=0; i<(10 + stage*2); i++) {
			int x = 0, z = 0;
			do {
				x = rand.nextInt(128);
				z = rand.nextInt(128);
			} while(!getLevel().isSpawnable(x, z));
			this.manager.getGame().getEntitiesManager().addEntityIA(x*.5f, 0f, z*.5f);
		}
		if(stage<5) {
			int d = stage+1;
			int x = 0, z = 0;
			for(int i=0; i<10/d; i++) {
				do {
					x = rand.nextInt(128);
					z = rand.nextInt(128);
				} while(!getLevel().isSpawnable(x, z));
				this.manager.getGame().getEntitiesManager().addLoot(new Vector3f(x*.5f, 0f, z*.5f));
			}
			x = rand.nextInt(128);
			z = rand.nextInt(128);
			if(getLevel().isSpawnable(x, z)) {
				this.manager.getGame().getEntitiesManager().addLoot(new Vector3f(x*.5f, 0f, z*.5f));
			}
		}
	}

	public void startStage() {
		levelIndex = rand.nextInt(levels.size());
		this.manager.getGame().getEntitiesManager().clear();
		spawnStage();
		this.manager.getGame().getEntitiesManager().getLocalPlayer().setPos(new Vector3f(getLevel().getSpawn()));
	}
	
	public void next() {
		levelIndex = rand.nextInt(levels.size());
		stage++;
		EntityPlayerSP player = this.manager.getGame().getEntitiesManager().getLocalPlayer();
		if(player.getArmor()<4) {
			player.setArmor(player.getArmor()+1);
		} else {
			player.addScore(25);
		}
		ParticuleManager.getInstance().clear();
		this.manager.getGame().getEntitiesManager().clearExceptPlayer();
		this.manager.getGame().getEntitiesManager().getLocalPlayer().getInventory().addPizza(Items.PIZZA_BACON, (12 + stage*3));
		spawnStage();
		this.manager.getGame().getEntitiesManager().getLocalPlayer().setPos(new Vector3f(getLevel().getSpawn().x, getLevel().getSpawn().y, getLevel().getSpawn().z));
		this.manager.getGame().setGui(null);
		Mouse.setGrabbed(true);
	}

	public int getStage() {
		return stage;
	}
}
