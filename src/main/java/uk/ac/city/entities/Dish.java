package uk.ac.city.entities;

import java.util.HashMap;
import java.util.HashSet;


public class Dish {
	private int ID;
	private String name;
	private final HashSet<OrderDish> orders = new HashSet<>();
	private final HashSet<DishRequiredStock> requiredItems = new HashSet<>();

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

	void addToOrder(OrderDish orderDish) {
		orders.add(orderDish);
	}

	void removeFromOrder(OrderDish orderDish) {
		orders.remove(orderDish);
	}

	/**
	 * Get the required items for this dish.
	 * @return A map of items and their quantities.
	 */
	public HashMap<Item, Integer> getRequiredItems() {
		HashMap<Item, Integer> items = new HashMap<>();
		for (DishRequiredStock drs : requiredItems) {
			items.put(drs.getItem(), drs.getQuantity());
		}

		return items;
	}

	public void addItem(Item item, int quantity) {
		DishRequiredStock dishItem = new DishRequiredStock(this, item, quantity);
		requiredItems.add(dishItem);
	}

	public void addItems(HashMap<Item, Integer> items) {
		for (Item item : items.keySet()) {
			addItem(item, items.get(item));
		}
	}
}
