package uk.ac.city.entities;

import java.util.HashSet;


public class Item {
	private int ID;
	private final String name;
	private int currentQuantity;
	private int maxQuantity;
	private final HashSet<DishRequiredStock> dishesUsing = new HashSet<>();
	private final HashSet<StockIntake> intakes = new HashSet<>();
	private final HashSet<StockUsage> usages = new HashSet<>();

	public Item(String name, int maxQuantity) {
		this.name = name;
		this.maxQuantity = maxQuantity;
		this.currentQuantity = 0;
	}

	public Item(String name) {
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
		for (DishRequiredStock drs : this.dishesUsing) {
			dishes.add(drs.getDish());
		}

		return dishes;
	}

	void addDishUsing(DishRequiredStock drs) {
		this.dishesUsing.add(drs);
	}

	void removeDishUsing(DishRequiredStock drs) {
		this.dishesUsing.remove(drs);
	}

	public HashSet<StockIntake> getIntakes() {
		return intakes;
	}

	public void addUsage(StockUsage usage) {
		this.usages.add(usage);
	}

	public HashSet<StockUsage> getUsages() {
		return usages;
	}

	public void addIntake(StockIntake intake) {
		this.intakes.add(intake);
	}
}
