package uk.ac.city.database.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import static uk.ac.city.database.Database.getConnection;


public class Ingredient {
	private static final HashSet<Ingredient> ingredients = new HashSet<>();

	private int ID;
	private final String name;
	private final Category category;
	private int currentQuantity;
	private int maxQuantity;
	private final HashSet<DishRequiredIngredients> dishesUsing = new HashSet<>();
	private final HashSet<IngredientTransaction> changes = new HashSet<>();

	public Ingredient(int ID, String name, Category category, int currentQuantity, int maxQuantity) {
		this.ID = ID;
		this.name = name;
		this.category = category;
		this.currentQuantity = currentQuantity;
		this.maxQuantity = maxQuantity;
		this.currentQuantity = 0;
	}

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

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public Category getCategory() {
		return category;
	}

	public int getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(int currentQuantity) {
		if (this.currentQuantity + currentQuantity < 0) {
			throw new IllegalArgumentException(String.format("Cannot decrease %s quantity below 0!", name));
		}

		this.currentQuantity = currentQuantity;
	}

	public void increaseQuantity(int quantity) {
		if (this.currentQuantity + quantity < 0) {
			throw new IllegalArgumentException(String.format("Cannot decrease %s quantity below 0!", name));
		}

		this.currentQuantity += quantity;
	}

	public void decreaseQuantity(int quantity) {
		if (this.currentQuantity - quantity < 0) {
			throw new IllegalArgumentException(String.format("Cannot decrease %s quantity below 0!", name));
		}

		this.currentQuantity -= quantity;
	}

	public int getMaxQuantity() {
		return maxQuantity;
	}

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

	@Override
	public String toString() {
		return String.format("%s (%d/%d)", name, currentQuantity, maxQuantity);
	}
}
