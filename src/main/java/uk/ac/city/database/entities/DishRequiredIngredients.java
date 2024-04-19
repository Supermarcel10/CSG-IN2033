package uk.ac.city.database.entities;


/**
 * Represents the required ingredients for a dish.
 */
class DishRequiredIngredients {
	private Integer ID;
	private final Dish dish;
	private final Ingredient ingredient;
	private final Integer quantity;

	/**
	 * Creates a new instance of DishRequiredIngredients.
	 * @param dish The dish that requires the ingredient.
	 * @param ingredient The ingredient required by the dish.
	 * @param quantity The quantity of the ingredient required by the dish.
	 */
	public DishRequiredIngredients(Dish dish, Ingredient ingredient, int quantity) {
		this.dish = dish;
		this.ingredient = ingredient;
		this.quantity = quantity;
	}

	/**
	 * Destructs the object in the database once it's no longer used in memory.
	 */
	void Destruct() {
		// TODO: Implement this method to destruct the object in database prior to memory deallocation by GC
	}

	/**
	 * Gets the ID of the DishRequiredIngredients.
	 * @return The ID of the DishRequiredIngredients.
	 */
	public Integer getID() {
		return ID;
	}

	/**
	 * Gets the dish that requires the ingredient.
	 * @return The dish that requires the ingredient.
	 */
	public Dish getDish() {
		return dish;
	}

	/**
	 * Gets the ingredient required by the dish.
	 * @return The ingredient required by the dish.
	 */
	public Ingredient getItem() {
		return ingredient;
	}

	/**
	 * Gets the quantity of the ingredient required by the dish.
	 * @return The quantity of the ingredient required by the dish.
	 */
	public Integer getQuantity() {
		return quantity;
	}
}
