package uk.ac.city.database.entities;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.HashSet;


/**
 * Represents a dish in the database.
 */
public class Dish {
	private int ID;
	private String name;
	private final HashSet<OrderDish> orders = new HashSet<>();
	private final HashSet<DishRequiredIngredients> requiredItems = new HashSet<>();
	private String recipe;
	private Image image;

	/**
	 * Create a new dish.
	 * @param name The name of the dish.
	 */
	public Dish(String name) {
		this.name = name;
	}

	/**
	 * Gets the dish's ID.
	 * @return The dish ID.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Gets the dish's name.
	 * @return The name of the dish.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the dish's name.
	 * @param name The name of the dish.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the orders where this dish exists.
	 * @return A set of orders.
	 */
	public HashSet<Order> getOrders() {
		HashSet<Order> orders = new HashSet<>();
		for (OrderDish od : this.orders) {
			orders.add(od.getOrder());
		}

		return orders;
	}

	/**
	 * Add an order where this dish exists.
	 * @param orderDish The order to attach to.
	 */
	void addOrder(OrderDish orderDish) {
		orders.add(orderDish);
	}

	/**
	 * Remove an order where this dish no longer exists.
	 * @param orderDish The order to dislogde from.
	 */
	void removeOrder(OrderDish orderDish) {
		orders.remove(orderDish);
	}

	/**
	 * Get the required items for this dish.
	 * @return A map of items and their quantities.
	 */
	public HashMap<Ingredient, Integer> getRequiredItems() {
		HashMap<Ingredient, Integer> items = new HashMap<>();
		for (DishRequiredIngredients drs : requiredItems) {
			items.put(drs.getItem(), drs.getQuantity());
		}

		return items;
	}

	/**
	 * Add a required item to the dish.
	 * @param ingredient The item to add.
	 * @param quantity The quantity of the item.
	 */
	public void addItem(Ingredient ingredient, int quantity) {
		DishRequiredIngredients dishItem = new DishRequiredIngredients(this, ingredient, quantity);

		requiredItems.add(dishItem);
		ingredient.addDishUsing(dishItem);
	}

	/**
	 * Remove a required item from the dish.
	 * @param ingredient The item to remove.
	 */
	void removeItem(Ingredient ingredient) {
		DishRequiredIngredients dishItem = requiredItems.stream().filter(drs -> drs.getItem().equals(ingredient)).findFirst().orElse(null);
		if (dishItem == null) {
			throw new IllegalArgumentException("Item does not exist in dish");
		}

		requiredItems.remove(dishItem);
		dishItem.getItem().removeDishUsing(dishItem);
	}

	/**
	 * Get the recipe for the dish.
	 * @return The recipe.
	 */
	public String getRecipe() {
		return recipe;
	}

	/**
	 * Set the recipe for the dish.
	 * @param recipe The recipe.
	 */
	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}

	/**
	 * Get the image for the dish.
	 * @return The image.
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Override the default toString method to return the dish's name for easier debugging and display in logs.
	 * @return The dish's name.
	 */
	@Override
	public String toString() {
		return name;
	}
}
