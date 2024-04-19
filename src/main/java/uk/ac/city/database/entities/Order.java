package uk.ac.city.database.entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;


/**
 * Represents an order entitiy in the database from the FoH.
 */
public class Order {
	private int ID;
	private final LocalDateTime orderDateTime;
	private final int tableNumber;
	private Chef assignedChef;
	private final HashSet<OrderDish> dishes = new HashSet<>();
	private final HashMap<Ingredient, Integer> requiredItems = new HashMap<>();

	/**
	 * Creates a new order with the given parameters.
	 * @param orderDateTime The date and time the order was placed.
	 * @param tableNumber The table number the order was placed from.
	 * @param dishes The dishes and their quantities in the order.
	 */
	public Order(LocalDateTime orderDateTime, int tableNumber, HashMap<Dish, Integer> dishes) {
		this.orderDateTime = orderDateTime;
		this.tableNumber = tableNumber;

		for (Dish dish : dishes.keySet()) {
			OrderDish orderDish = new OrderDish(this, dish, dishes.get(dish));

			this.dishes.add(orderDish);
			dish.addOrder(orderDish);
			recalculateRequiredItems(dish, dishes.get(dish), false);
		}

		// Remove ingredients from stock
		requiredItems.forEach(Ingredient::decreaseQuantity);
	}

	/**
	 * Recalculates the required items for the order.
	 * @param dish The dish to recalculate the required items for.
	 * @param removal Whether the dish is being added or removed from the order.
	 */
	private void recalculateRequiredItems(Dish dish, boolean removal) {
		recalculateRequiredItems(dish, 1, removal);
	}

	/**
	 * Recalculates the required items for the order.
	 * @param dish The dish to recalculate the required items for.
	 * @param quantityOfDish The quantity of the dish to recalculate the required items for.
	 * @param removal Whether the dish is being added or removed from the order.
	 */
	private void recalculateRequiredItems(Dish dish, int quantityOfDish, boolean removal) {
		dish.getRequiredItems().forEach((ingredient, quantity) -> {
			int requiredQuantity = quantity * quantityOfDish;
			if (removal) requiredQuantity *= -1;

			// If the item is already in the requiredItems map, add the new quantity to the existing quantity, otherwise add a new entry
			requiredItems.put(ingredient, requiredItems.getOrDefault(ingredient, 0) + requiredQuantity);
		});
	}

	/**
	 * gets the ID of the order.
	 * @return The ID of the order.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Gets the date and time the order was placed.
	 * @return The date and time the order was placed.
	 */
	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	/**
	 * Gets the table number the order was placed for.
	 * @return The table number the order was placed for.
	 */
	public int getTableNumber() {
		return tableNumber;
	}

	/**
	 * Gets the assigned chef for the order in large scale kitchens.
	 * @return The assigned chef for the order.
	 */
	public Chef getAssignedChef() {
		return assignedChef;
	}

	/**
	 * Sets the assigned chef for the order in large scale kitchens.
	 * @param assignedChef The chef to assign to the order.
	 */
	public void setAssignedChef(Chef assignedChef) {
		this.assignedChef = assignedChef;
	}

	/**
	 * Gets the dishes in the order.
	 * @return a map of dishes and their quantities in the order.
	 */
	public HashMap<Dish, Integer> getDishesWithQuantities() {
		HashMap<Dish, Integer> dishMap = new HashMap<>();
		for (OrderDish orderDish : dishes) {
			dishMap.put(orderDish.getDish(), orderDish.getQuantity());
		}

		return dishMap;
	}

	/**
	 * Adds a dish to the order.
	 * @param dish The dish to add.
	 * @param quantity The quantity of the dish to add.
	 */
	public void addDish(Dish dish, int quantity) {
		dishes.add(new OrderDish(this, dish, quantity));
		recalculateRequiredItems(dish, false);
	}

	/**
	 * Removes a dish from the order.
	 * @param dish The dish to remove.
	 */
	public void removeDish(Dish dish) {
		for (OrderDish orderDish : dishes) {
			orderDish.Destruct();
			dishes.remove(orderDish);
		}

		recalculateRequiredItems(dish, true);
	}

	/**
	 * Gets the required items for the order.
	 * @return A map of ingredients and their quantities required for the order.
	 */
	public HashMap<Ingredient, Integer> getRequiredItems() {
		return requiredItems;
	}

	/**
	 * Dismisses the order and increases the quantity of the required items in stock prior to the order being placed.
	 */
	public void dismissOrder() {
		requiredItems.forEach(Ingredient::increaseQuantity);
	}

	/**
	 * Overrides the default toString method to provide a string representation of the order for debugging and logging.
	 * @return A string representation of the order.
	 */
	@Override
	public String toString() {
		return String.format("Order %d: %s\nContains: %s\nRequires: %s", ID, orderDateTime, dishes, requiredItems);
	}
}
