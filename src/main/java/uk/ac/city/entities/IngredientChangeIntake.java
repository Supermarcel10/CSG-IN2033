package uk.ac.city.entities;

import java.time.LocalDateTime;


public class IngredientChangeIntake extends IngredientChangeEntity {
	public IngredientChangeIntake(Ingredient ingredient, LocalDateTime dateTime, int quantity) {
		this.ingredient = ingredient;
		this.dateTime = dateTime;
		this.quantity = quantity;
	}
}
