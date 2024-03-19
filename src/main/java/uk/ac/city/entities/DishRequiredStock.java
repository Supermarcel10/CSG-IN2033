package uk.ac.city.entities;


class DishRequiredStock {
	private Integer ID;
	private Dish dish;
	private Item item;
	private Integer quantity;

	public DishRequiredStock(Dish dish, Item item, int quantity) {
		this.dish = dish;
		this.item = item;
		this.quantity = quantity;
	}

	public Integer getID() {
		return ID;
	}

	public Dish getDish() {
		return dish;
	}

	public Item getItem() {
		return item;
	}

	public Integer getQuantity() {
		return quantity;
	}
}
