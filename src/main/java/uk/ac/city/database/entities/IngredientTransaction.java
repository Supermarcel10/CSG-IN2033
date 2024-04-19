package uk.ac.city.database.entities;

import java.time.LocalDateTime;


/**
 * Represents a transaction of an ingredient.
 */
public class IngredientTransaction {
	private int ID;
	private final Ingredient ingredient;
	private final LocalDateTime dateTime;
	private final int quantity;
	private final TransactionType type;

	/**
	 * The type of transaction.
	 */
	public enum TransactionType {
		USED,
		INTOOK,
		WASTED,
		EXPIRED
	}

	/**
	 * Loads an existing transaction from the database.
	 * @param ingredient The ingredient involved in the transaction.
	 * @param dateTime The date and time of the transaction.
	 * @param quantity The quantity of the ingredient involved in the transaction.
	 * @param type The type of transaction.
	 */
	public IngredientTransaction(Ingredient ingredient, LocalDateTime dateTime, int quantity, TransactionType type) {
		this.ingredient = ingredient;
		this.dateTime = dateTime;
		this.quantity = quantity;
		this.type = type;
	}

	/**
	 * Gets the ID of the transaction.
	 * @return The ID of the transaction.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Gets the ingredient involved in the transaction.
	 * @return The ingredient involved in the transaction.
	 */
	public Ingredient getItem() {
		return ingredient;
	}

	/**
	 * Gets the date and time of the transaction.
	 * @return The date and time of the transaction.
	 */
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	/**
	 * Gets the quantity of the ingredient involved in the transaction.
	 * @return The quantity of the ingredient involved in the transaction.
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Gets the type of transaction.
	 * @return The type of transaction.
	 */
	public TransactionType getType() {
		return type;
	}

	/**
	 * Overrides the toString method to return a string representation of the transaction for easier debugging and logging.
	 * @return A string representation of the transaction.
	 */
	@Override
	public String toString() {
		return String.format("[%s] %s %d %s", dateTime, type, quantity, ingredient.getName());
	}
}
