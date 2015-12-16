package fr.mimus.game.inventory;

public class Inv {
	int[] items = new int[6];
	int[] pizza = new int[4];

	public Inv() {
		for(int i = 0; i<items.length; i++) {
			items[i] = 0;
		}
		
		for(int i = 0; i<pizza.length; i++) {
			pizza[i] = 0;
		}
	}
	
	public void init() {
		items[Items.PATE] = 5;
		items[Items.TOMATO] = 5;
		items[Items.STEAK] = 5;
		pizza[Items.PIZZA_BACON] = 25;
		pizza[Items.PIZZA_CHEESE] = 1;
	}
	
	
	public int getNumberItem(int index) {
		return items[index];
	}
	
	public int getNumberPizza(int index) {
		return pizza[index];
	}

	public void setPizza(int index, int value) {
		pizza[index] = value;
	}
	public void setItem(int index, int value) {
		items[index] = value;
	}
	
	public void addPizza(int index, int value) {
		pizza[index] += value;
	}
	public void addItem(int index, int value) {
		items[index] += value;
	}
	
	public void subPizza(int index, int value) {
		pizza[index] -= value;
	}
	public void subItem(int index, int value) {
		items[index] -= value;
	}
}
