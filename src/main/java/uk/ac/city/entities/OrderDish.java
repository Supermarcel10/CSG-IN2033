package uk.ac.city.entities;


class OrderDish {
	private int ID;
	private final Order order;
	private final Dish dish;
	private final int quantity;

	OrderDish(Order order, Dish dish, int quantity) {
		this.order = order;
		this.dish = dish;
		this.quantity = quantity;
	}

	void Destruct() {
		// TODO: Implement this method to destruct the object in database prior to memory deallocation by GC
	}

	public int getID() {
		return ID;
	}

	public Order getOrder() {
		return order;
	}

	public Dish getDish() {
		return dish;
	}

	public int getQuantity() {
		return quantity;
	}
}
