package uk.ac.city.entities;


class DishRequiredIngredients {
	private Integer ID;
	private final Dish dish;
	private final Ingredient ingredient;
	private final Integer quantity;

	public DishRequiredIngredients(Dish dish, Ingredient ingredient, int quantity) {
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
