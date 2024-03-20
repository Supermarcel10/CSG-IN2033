package uk.ac.city.entities;

import java.time.LocalDateTime;


class IngredientChangeEntity {
	protected int ID;
	protected Ingredient ingredient;
	protected LocalDateTime dateTime;
	protected int quantity;

	public int getID() {
		return ID;
	}

	public Ingredient getItem() {
		return ingredient;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public int getQuantity() {
		return quantity;
	}
}
