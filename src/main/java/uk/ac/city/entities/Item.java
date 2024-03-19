package uk.ac.city.entities;

import java.util.HashSet;


public class Item {
	private int ID;
	private final String name;
	private int currentQuantity;
	private int maxQuantity;
	private HashSet<DishRequiredStock> dishesUsing;
	private HashSet<StockIntake> intakes = new HashSet<>();
	private HashSet<StockUsage> usages = new HashSet<>();

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

	public void setIntakes(HashSet<StockIntake> intakes) {
		this.intakes = intakes;
	}

	public HashSet<StockUsage> getUsages() {
		return usages;
	}

	public void setUsages(HashSet<StockUsage> usages) {
		this.usages = usages;
	}
}
