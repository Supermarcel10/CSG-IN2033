package uk.ac.city.entities;


class OrderDish {
	private int ID;
	private Order order;
	private Dish dish;
	private int quantity;

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
