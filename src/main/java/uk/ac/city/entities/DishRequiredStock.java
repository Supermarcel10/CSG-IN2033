package uk.ac.city.entities;


class DishRequiredStock {
	private Integer ID;
	private Dish dish;
	private Ingredient ingredient;
	private Integer quantity;

	public DishRequiredStock(Dish dish, Ingredient ingredient, int quantity) {
		this.dish = dish;
		this.ingredient = ingredient;
		this.quantity = quantity;
	}

	public Integer getID() {
		return ID;
	}

	public Dish getDish() {
		return dish;
	}

	public Ingredient getItem() {
		return ingredient;
	}

	public Integer getQuantity() {
		return quantity;
	}
}
