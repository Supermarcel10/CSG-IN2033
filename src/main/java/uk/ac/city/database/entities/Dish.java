package uk.ac.city.database.entities;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.HashSet;


public class Dish {
	private int ID;
	private String name;
	private final HashSet<OrderDish> orders = new HashSet<>();
	private final HashSet<DishRequiredIngredients> requiredItems = new HashSet<>();
	private String recipe;
	private Image image;

	public Dish(String name) {
		this.name = name;
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

	void addOrder(OrderDish orderDish) {
		orders.add(orderDish);
	}

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

	public void addItem(Ingredient ingredient, int quantity) {
		DishRequiredIngredients dishItem = new DishRequiredIngredients(this, ingredient, quantity);

		requiredItems.add(dishItem);
		ingredient.addDishUsing(dishItem);
	}

	void removeItem(Ingredient ingredient) {
		DishRequiredIngredients dishItem = requiredItems.stream().filter(drs -> drs.getItem().equals(ingredient)).findFirst().orElse(null);
		if (dishItem == null) {
			throw new IllegalArgumentException("Item does not exist in dish");
		}

		requiredItems.remove(dishItem);
		dishItem.getItem().removeDishUsing(dishItem);
	}

	public String getRecipe() {
		return recipe;
	}

	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}

	public Image getImage() {
		return image;
	}

	@Override
	public String toString() {
		return name;
	}
}
