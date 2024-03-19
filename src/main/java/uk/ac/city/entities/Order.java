package uk.ac.city.entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;


public class Order {
	private int ID;
	private final LocalDateTime orderDateTime;
	private final int tableNumber;
	private Chef assignedChef;
	private final HashSet<OrderDish> dishes = new HashSet<>();

	public Order(LocalDateTime orderDateTime, int tableNumber, HashMap<Dish, Integer> dishes) {
		this.orderDateTime = orderDateTime;
		this.tableNumber = tableNumber;

		for (Dish dish : dishes.keySet()) {
			OrderDish orderDish = new OrderDish(this, dish, dishes.get(dish));

			this.dishes.add(orderDish);
			dish.addOrder(orderDish);
		}
	}

	public int getID() {
		return ID;
	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public Chef getAssignedChef() {
		return assignedChef;
	}

	public void setAssignedChef(Chef assignedChef) {
		this.assignedChef = assignedChef;
	}

	public HashMap<Dish, Integer> getDishesWithQuantities() {
		HashMap<Dish, Integer> dishMap = new HashMap<>();
		for (OrderDish orderDish : dishes) {
			dishMap.put(orderDish.getDish(), orderDish.getQuantity());
		}

		return dishMap;
	}

	public void addDish(Dish dish, int quantity) {
		dishes.add(new OrderDish(this, dish, quantity));
	}

	public void removeDish(Dish dish) {
		for (OrderDish orderDish : dishes) {
			if (orderDish.getDish().equals(dish)) {
				dishes.remove(orderDish);
				break;
			}
		}
	}
}
