package uk.ac.city.database.entities;

import java.util.HashSet;


public class Ingredient {
	private int ID;
	private final String name;
	private int currentQuantity;
	private int maxQuantity;
	private final HashSet<DishRequiredIngredients> dishesUsing = new HashSet<>();
	private final HashSet<IngredientTransaction> changes = new HashSet<>();

	public Ingredient(String name, int maxQuantity) {
		this.name = name;
		this.maxQuantity = maxQuantity;
		this.currentQuantity = 0;
	}

	public Ingredient(String name) {
		this.name = name;
		this.maxQuantity = 0;
		this.currentQuantity = 0;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public int getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(int currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

	public int getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	/**
	 * Get the dishes that use this item.
	 * @return A set of Dish objects
	 */
	public HashSet<Dish> getDishesUsing() {
		HashSet<Dish> dishes = new HashSet<>();
		for (DishRequiredIngredients drs : this.dishesUsing) {
			dishes.add(drs.getDish());
		}

		return dishes;
	}

	void addDishUsing(DishRequiredIngredients drs) {
		this.dishesUsing.add(drs);
	}

	void removeDishUsing(DishRequiredIngredients drs) {
		this.dishesUsing.remove(drs);
	}

	public void addUsage(IngredientTransaction change) {
		this.changes.add(change);
	}

	public HashSet<IngredientTransaction> getChanges() {
		return changes;
	}

	@Override
	public String toString() {
		return String.format("%s (%d/%d)", name, currentQuantity, maxQuantity);
	}
}
