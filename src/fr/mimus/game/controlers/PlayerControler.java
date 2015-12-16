package fr.mimus.game.controlers;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import fr.mimus.game.Audio;
import fr.mimus.game.Game;
import fr.mimus.game.Option;
import fr.mimus.game.entities.EntityPlayerSP;
import fr.mimus.game.gui.GuiCrafting;
import fr.mimus.game.gui.GuiPause;
import fr.mimus.game.inventory.Items;
import fr.mimus.game.managers.ControlsManager;

public class PlayerControler extends Controler {

	int forward = Keyboard.KEY_Z;
	int backward = Keyboard.KEY_S;
	int left = Keyboard.KEY_Q;
	int right = Keyboard.KEY_D;
	
	EntityPlayerSP player = null;
	public PlayerControler(ControlsManager cm) {
		super(cm);
	}

	@Override
	public void update() {
		if(player == null) {
			player = this.manager.getGame().getEntitiesManager().getLocalPlayer();
		}
		
		if(player != null) {
			if(!Mouse.isGrabbed()) {
				if(this.manager.isMouseDown(0)) Mouse.setGrabbed(true);
				else return;
			} else {
				if(this.manager.isKeyClicked(Keyboard.KEY_ESCAPE)) {
					Game.getInstance().setGui(new GuiPause());
					return;
				}
			}
			
			if(Option.isKey_qwerty()) {
				forward = Keyboard.KEY_W;
				backward = Keyboard.KEY_S;
				left = Keyboard.KEY_A;
				right = Keyboard.KEY_D;
			} else if(!Option.isKey_qwerty()) {
				forward = Keyboard.KEY_Z;
				backward = Keyboard.KEY_S;
				left = Keyboard.KEY_Q;
				right = Keyboard.KEY_D;
			}
			
			
			Vector2f rot = player.getRot();	
			rot.x -= Mouse.getDY()*.25f;
			rot.y += Mouse.getDX()*.25f;
			if(rot.x>90) rot.x=90;
			if(rot.x<-90) rot.x=-90;
			if(rot.y>360) rot.y-=360;
			if(rot.y<0) rot.y+=360;
			player.setRot(rot);
			float speed = .15f;
			if(this.manager.isKeyDown(Keyboard.KEY_LSHIFT)) {
				speed*=1.2f;
			}
			speed += player.getOffsetSpeed();
						
			if(this.manager.isKeyDown(forward) && this.manager.isKeyDown(left)) {
				player.move(player.getSide(-135), speed);
			} else if(this.manager.isKeyDown(forward) && this.manager.isKeyDown(right)) {
				player.move(player.getSide(-45), speed);
			} else if(this.manager.isKeyDown(backward) && this.manager.isKeyDown(left)) {
				player.move(player.getSide(135), speed);
			} else if(this.manager.isKeyDown(backward) && this.manager.isKeyDown(right)) {
				player.move(player.getSide(45), speed);
			} else if(this.manager.isKeyDown(forward)) {
				player.move(player.getForward(), speed);
			} else if(this.manager.isKeyDown(backward)) {
				player.move(player.getForward(), -speed);
			} else if(this.manager.isKeyDown(left)) {
				player.move(player.getSide(180), speed);
			} else if(this.manager.isKeyDown(right)) {
				player.move(player.getSide(0), speed);
			}
			
			if(this.manager.isMouseClicked(0)) {
				if(player.getInventory().getNumberPizza(Items.PIZZA_BACON)>0) {
					Game.getInstance().getEntitiesManager().addBullet(player);
					player.getInventory().subPizza(Items.PIZZA_BACON, 1);
					Audio.fire.play();
				}
			}
			if(this.manager.isMouseClicked(1)) {
				if(player.getInventory().getNumberPizza(Items.PIZZA_BACON)>2
					&& player.isTripleShot()) {
					Game.getInstance().getEntitiesManager().addTripleBullet(player);
					player.getInventory().subPizza(Items.PIZZA_BACON, 2);
					Audio.fire.play();
				}
			}
			
			if(this.manager.isKeyClicked(Keyboard.KEY_C)) {
				Game.getInstance().setGui(new GuiCrafting(player));
			}
			if(this.manager.isKeyClicked(Keyboard.KEY_1)) {
				if(player.getInventory().getNumberPizza(Items.PIZZA_CHEESE)>0 && player.getLife()<8) {
					player.setLife(player.getLife() + 1);
					player.subOffsetSpeed(.028f);
					player.getInventory().subPizza(Items.PIZZA_CHEESE, 1);
					Audio.eat.play();
				}
			}
			if(this.manager.isKeyClicked(Keyboard.KEY_2)) {
				if(player.getInventory().getNumberPizza(Items.PIZZA_CANNIBAL)>0 && player.getLife()<7) {
					player.setLife(player.getLife() + 2);
					player.getInventory().subPizza(Items.PIZZA_CANNIBAL, 1);
					Audio.eat.play();
				}
			}
			if(this.manager.isKeyClicked(Keyboard.KEY_3)) {
				if(player.getInventory().getNumberPizza(Items.PIZZA_CALZONE)>0 && player.getOffsetSpeed()<.1f) {
					player.addOffsetSpeed(.02f);
					player.getInventory().subPizza(Items.PIZZA_CALZONE, 1);
					Audio.eat.play();
				}
			}
		}
	}

}
