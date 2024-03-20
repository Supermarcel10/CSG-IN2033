package uk.ac.city.entities;

import java.util.HashSet;


public class Chef {
	private int ID;
	private String name;
	private ChefType role;
	private HashSet<Order> orders;

	public enum ChefType {
		HEAD_CHEF,
		SOUS_CHEF,
		LINE_CHEF
	}

	public Chef(String name, ChefType role) {
		this.name = name;
		this.role = role;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ChefType getRole() {
		return role;
	}

	public boolean login(String password) {
		// TODO: Encrypt this and compare with database
		return false;
	}

	public HashSet<Order> getOrders() {
		return orders;
	}

	public void addOrder(Order order) {
		orders.add(order);
	}

	public void removeOrder(Order order) {
		orders.remove(order);
	}
}
