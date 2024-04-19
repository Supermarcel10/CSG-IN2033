package uk.ac.city.database.entities;

import java.util.HashSet;


/**
 * Represents a chef entity using the system.
 */
public class Chef {
	private int ID;
	private String name;
	private final ChefType role;
	private final HashSet<Order> orders = new HashSet<>();

	/**
	 * Represents the chef's role.
	 */
	public enum ChefType {
		HEAD_CHEF,
		SOUS_CHEF,
		LINE_CHEF
	}

	/**
	 * Creates a new chef entity.
	 * @param name The chef's name.
	 * @param role The chef's role.
	 */
	public Chef(String name, ChefType role) {
		this.name = name;
		this.role = role;
	}

	/**
	 * Gets the chef's ID.
	 * @return The chef's ID.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Gets the chef's name.
	 * @return The chef's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the chef's name.
	 * @param name The chef's name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the chef's role.
	 * @return The chef's role.
	 */
	public ChefType getRole() {
		return role;
	}

	/**
	 * Checks if the chef's login password is correct.
	 * @param password The plaintext password to encrypt and check its hash against.
	 * @return True if the password is correct, false otherwise.
	 */
	public boolean login(String password) {
		// TODO: Encrypt this and compare with database
		return false;
	}

	/**
	 * Gets the chef's orders.
	 * @return The chef's orders.
	 */
	public HashSet<Order> getOrders() {
		return orders;
	}

	/**
	 * Adds an order to the chef's orders.
	 * @param order The order to add.
	 */
	public void addOrder(Order order) {
		orders.add(order);
	}

	/**
	 * Removes an order from the chef's orders.
	 * @param order The order to remove.
	 */
	public void removeOrder(Order order) {
		orders.remove(order);
	}

	/**
	 * Override of the toString method to return the chef's name for easier debuging and display in logs.
	 */
	@Override
	public String toString() {
		return name;
	}
}
