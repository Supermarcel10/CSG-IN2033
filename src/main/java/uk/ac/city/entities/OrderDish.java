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
