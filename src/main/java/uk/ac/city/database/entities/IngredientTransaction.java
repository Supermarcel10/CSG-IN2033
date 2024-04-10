package uk.ac.city.database.entities;

import java.time.LocalDateTime;


public class IngredientTransaction {
	private int ID;
	private final Ingredient ingredient;
	private final LocalDateTime dateTime;
	private final int quantity;
	private final TransactionType type;

	public enum TransactionType {
		USED,
		INTOOK,
		WASTED,
		EXPIRED
	}

	public IngredientTransaction(Ingredient ingredient, LocalDateTime dateTime, int quantity, TransactionType type) {
		this.ingredient = ingredient;
		this.dateTime = dateTime;
		this.quantity = quantity;
		this.type = type;
	}

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

	public TransactionType getType() {
		return type;
	}
}
