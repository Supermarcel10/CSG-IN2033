package uk.ac.city.database.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import static uk.ac.city.database.Database.getConnection;


/**
 * Represents an ingredient entity in the database.
 */
public class Ingredient {
	/**
	 * A set of all ingredients cached in memory.
	 */
	private static final HashSet<Ingredient> ingredients = new HashSet<>();

	private int ID;
	private final String name;
	private final Category category;
	private int currentQuantity;
	private int maxQuantity;
	private final HashSet<DishRequiredIngredients> dishesUsing = new HashSet<>();
	private final HashSet<IngredientTransaction> changes = new HashSet<>();

	/**
	 * Load an ingredient from the database and cache it in memory.
	 * @param ID The ID of the ingredient.
	 * @param name The name of the ingredient.
	 * @param category The category of the ingredient.
	 * @param currentQuantity The current quantity of the ingredient.
	 * @param maxQuantity The maximum quantity of the ingredient.
	 */
	public Ingredient(int ID, String name, Category category, int currentQuantity, int maxQuantity) {
		this.ID = ID;
		this.name = name;
		this.category = category;
		this.currentQuantity = currentQuantity;
		this.maxQuantity = maxQuantity;
	}

	/**
	 * Create a new ingredient object in the database and cache it in memory.
	 *
	 * @param name The name of the ingredient.
	 * @param category The category of the ingredient.
	 */
	public Ingredient(String name, Category category) {
		this.name = name;
		this.category = category;
		this.maxQuantity = 0;
		this.currentQuantity = 0;

		boolean createIngredient = true;
		for (Ingredient ingredient : ingredients) {
			if (ingredient.getName().equalsIgnoreCase(name)) {
				createIngredient = false;
				break;
			}
		}

		if (createIngredient) {
			try (Connection conn = getConnection();
			     PreparedStatement stmt = conn.prepareStatement("INSERT INTO Ingredient (Name, CategoryID, CurrentQuantity, MaxQuantity) VALUES (?, ?, ?, ?)")) {
				stmt.setString(1, name);
				stmt.setInt(2, category.getID());
				stmt.setInt(3, currentQuantity);
				stmt.setInt(4, maxQuantity);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets the ID of the ingredient.
	 * @return The ID of the ingredient.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Gets the name of the ingredient.
	 * @return The name of the ingredient.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the category of the ingredient.
	 * @return The category of the ingredient.
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Gets the current quantity available in stock.
	 * @return The current quantity available.
	 */
	public int getCurrentQuantity() {
		return currentQuantity;
	}

	/**
	 * Sets the current quantity available in stock.
	 * @param currentQuantity The new quantity.
	 */
	public void setCurrentQuantity(int currentQuantity) {
		if (this.currentQuantity + currentQuantity < 0) {
			throw new IllegalArgumentException(String.format("Cannot decrease %s quantity below 0!", name));
		}

		this.currentQuantity = currentQuantity;
	}

	/**
	 * Increases the current quantity available in stock.
	 * @param quantity The quantity to increase by.
	 */
	public void increaseQuantity(int quantity) {
		if (this.currentQuantity + quantity < 0) {
			throw new IllegalArgumentException(String.format("Cannot decrease %s quantity below 0!", name));
		}

		this.currentQuantity += quantity;
	}

	/**
	 * Decreases the current quantity available in stock.
	 * @param quantity The quantity to decrease by.
	 */
	public void decreaseQuantity(int quantity) {
		if (this.currentQuantity - quantity < 0) {
			throw new IllegalArgumentException(String.format("Cannot decrease %s quantity below 0!", name));
		}

		this.currentQuantity -= quantity;
	}

	/**
	 * Gets the maximum quantity available in stock.
	 * @return The maximum quantity available.
	 */
	public int getMaxQuantity() {
		return maxQuantity;
	}

	/**
	 * Sets the maximum quantity available in stock.
	 * @param maxQuantity The new maximum quantity.
	 */
	public void setMaxQuantity(int maxQuantity) {
		if (maxQuantity < 0) {
			throw new IllegalArgumentException("Cannot set max quantity below 0!");
		}

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

	/**
	 * Attaches a dish to this ingredient.
	 * @param drs The dish to attach.
	 */
	void addDishUsing(DishRequiredIngredients drs) {
		this.dishesUsing.add(drs);
	}

	/**
	 * Detaches a dish from this ingredient.
	 * @param drs The dish to detach.
	 */
	void removeDishUsing(DishRequiredIngredients drs) {
		this.dishesUsing.remove(drs);
	}

	/**
	 * Adds a usage of this ingredient.
	 * @param change The transaction to add.
	 */
	public void addUsage(IngredientTransaction change) {
		this.changes.add(change);
	}

	/**
	 * Retrieves a log of all changes to this ingredient.
	 * @return A set of IngredientTransaction objects.
	 */
	public HashSet<IngredientTransaction> getChanges() {
		return changes;
	}

	/**
	 * Caches all ingredients in the database.
	 */
	public static void cacheAll() {
		try (Connection conn = getConnection();
		     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Ingredient");
		     ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				new Ingredient(rs.getInt("ID"), rs.getString("Name"), Category.getCategoryByID(rs.getInt("CategoryID")), rs.getInt("CurrentQuantity"), rs.getInt("MaxQuantity"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static HashSet<Ingredient> getAllIngredients() {
		return ingredients;
	}

	/**
	 * Overrides the toString method to return a formatted debuggable and easilly loggable string.
	 */
	@Override
	public String toString() {
		return String.format("%s (%d/%d)", name, currentQuantity, maxQuantity);
	}
}
