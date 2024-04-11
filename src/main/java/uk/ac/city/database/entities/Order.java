package uk.ac.city.database.entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;


public class Order {
	private int ID;
	private final LocalDateTime orderDateTime;
	private final int tableNumber;
	private Chef assignedChef;
	private final HashSet<OrderDish> dishes = new HashSet<>();
	private final HashMap<Ingredient, Integer> requiredItems = new HashMap<>();

	public Order(LocalDateTime orderDateTime, int tableNumber, HashMap<Dish, Integer> dishes) {
		this.orderDateTime = orderDateTime;
		this.tableNumber = tableNumber;

		for (Dish dish : dishes.keySet()) {
			OrderDish orderDish = new OrderDish(this, dish, dishes.get(dish));

			this.dishes.add(orderDish);
			dish.addOrder(orderDish);
			recalculateRequiredItems(dish, false);
		}
	}

	private void recalculateRequiredItems(Dish dish, boolean removal) {
		dish.getRequiredItems().forEach((ingredient, quantity) -> {
			int requiredQuantity = quantity * dish.getRequiredItems().get(ingredient);
			if (removal) requiredQuantity *= -1;

			// If the item is already in the requiredItems map, add the new quantity to the existing quantity, otherwise add a new entry
			requiredItems.put(ingredient, requiredItems.getOrDefault(ingredient, 0) + requiredQuantity);
		});
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
		recalculateRequiredItems(dish, false);
	}

	public void removeDish(Dish dish) {
		for (OrderDish orderDish : dishes) {
			orderDish.Destruct();
			dishes.remove(orderDish);
		}

		recalculateRequiredItems(dish, true);
	}

	public HashMap<Ingredient, Integer> getRequiredItems() {
		return requiredItems;
	}
}