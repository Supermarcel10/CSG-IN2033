package uk.ac.city.database.entities;


/**
 * Represents a dish in an order.
 */
class OrderDish {
	private int ID;
	private final Order order;
	private final Dish dish;
	private final int quantity;

	/**
	 * Creates a new OrderDish object.
	 * @param order The order this dish belongs to.
	 * @param dish The dish.
	 * @param quantity The quantity of the dish.
	 */
	OrderDish(Order order, Dish dish, int quantity) {
		this.order = order;
		this.dish = dish;
		this.quantity = quantity;
	}

	/**
	 * Destructs the object in the database once it's no longer referenced in memory.
	 */
	void Destruct() {
		// TODO: Implement this method to destruct the object in database prior to memory deallocation by GC
	}

	/**
	 * Gets the ID of the OrderDish.
	 * @return The ID of the OrderDish.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Gets the order this dish belongs to.
	 * @return The order this dish belongs to.
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Gets the dish.
	 * @return The dish.
	 */
	public Dish getDish() {
		return dish;
	}

	/**
	 * Gets the quantity of the dish.
	 * @return The quantity of the dish.
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Overrides the toString method to return a string representation of the object for easier debugging and logging.
	 * @return A string representation of the object.
	 */
	@Override
	public String toString() {
		return String.format("%s x%d", dish.getName(), quantity);
	}
}
