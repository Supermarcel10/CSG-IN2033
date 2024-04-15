package uk.ac.city.database;// The rest of your imports
import uk.ac.city.database.entities.Dish;
import uk.ac.city.database.entities.Ingredient;

import java.sql.*;
import java.util.HashSet;

// ...

public class DatabaseInitializer {



	public static void initializeDatabase() {
		try (Connection conn = Database.getConnection()) {
			conn.setAutoCommit(false);  // Start transaction

			try {
				// Assume createTables is another method setting up your DB schema
				createTables(conn);

				// Insert ingredients
				try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO ingredients (name) VALUES (?)")) {
					for (Ingredient ingredient : getAllIngredients()) {
						insertIngredient(ingredient, stmt);
					}
				}

				// Insert dishes and their relationships to ingredients
				try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO dishes (name, recipe) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
					for (Dish dish : getAllDishes()) {
						insertDish(dish, stmt, conn);
					}
				}

				conn.commit();  // Commit transaction
			} catch (SQLException e) {
				((Connection) conn).rollback();  // Rollback on error
				throw e;  // Rethrow to handle or log appropriately
			}
		} catch (SQLException e) {
			// Handle or log error
			e.printStackTrace();
		}
	}


	private static void createTables(Connection conn) {
		// Implement table creation logic
	}

	private static HashSet<Ingredient> getAllIngredients() {
		HashSet<Ingredient> uniqueIngredients = new HashSet<>();
		// Populate this set from your data source or statically
		return uniqueIngredients;
	}

	private static HashSet<Dish> getAllDishes() {
		HashSet<Dish> allDishes = new HashSet<>();
		// Populate this set from your data source or statically
		return allDishes;
	}

	private static void insertIngredient(Ingredient ingredient, PreparedStatement stmt) throws SQLException {
		stmt.setString(1, ingredient.getName());
		stmt.executeUpdate();
	}

	private static void insertDish(Dish dish, PreparedStatement stmt, Connection conn) throws SQLException {
		stmt.setString(1, dish.getName());
		stmt.setString(2, dish.getRecipe());
		stmt.executeUpdate();
	}

	public static void main(String[] args) {
		Database.initiateDB();
		initializeDatabase();
	}
}
