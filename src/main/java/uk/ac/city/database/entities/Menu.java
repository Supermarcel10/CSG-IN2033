package uk.ac.city.database.entities;

import java.time.LocalDateTime;
import java.util.HashSet;


public class Menu {
	private int ID;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private final HashSet<Dish> dishes;

	public Menu(LocalDateTime startDate, LocalDateTime endDate, HashSet<Dish> dishes) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.dishes = dishes;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public HashSet<Dish> getDishes() {
		return dishes;
	}

	public void addDish(Dish dish) {
		dishes.add(dish);
	}

	public void removeDish(Dish dish) {
		dishes.remove(dish);
	}

	@Override
	public String toString() {
		return String.format("Menu: %s - %s\nConsists of: %s", startDate, endDate, dishes);
	}
}
