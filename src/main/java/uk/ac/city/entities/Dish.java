package uk.ac.city.entities;

import java.util.HashMap;
import java.util.HashSet;


public class Dish {
	private int ID;
	private String name;
	private int price = 0;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	public void addToOrder(Order order, int quantity) {
		OrderDish orderDish = new OrderDish(order, this, quantity);
		orders.add(orderDish);
	}

	public void removeFromOrder(Order order) {
		OrderDish orderDish = null;
		for (OrderDish od : orders) {
			if (od.getOrder().equals(order)) {
				orderDish = od;
				break;
			}
		}

		orders.remove(orderDish);
	}

	public HashSet<Item> getRequiredItems() {
		HashSet<Item> items = new HashSet<>();
		for (DishRequiredStock drs : requiredItems) {
			items.add(drs.getItem());
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
