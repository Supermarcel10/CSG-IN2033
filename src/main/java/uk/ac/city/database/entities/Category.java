package uk.ac.city.database.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import static uk.ac.city.database.Database.getConnection;


/**
 * Represents an item category entity in the system.
 */
public class Category {
	/**
	 * A set of all categories locally cached in the system.
	 */
	protected static HashSet<Category> categories = new HashSet<>();

	private int ID;
	private final String name;

	/**
	 * Loads a category entity from the database.
	 * @param ID The category's ID.
	 * @param name The category's name.
	 */
	public Category(int ID, String name) {
		this.ID = ID;
		this.name = name;

		categories.add(this);
	}

	/**
	 * Creates a new category entity and caches it.
	 * @param name The category's name.
	 */
	public Category(String name) {
		this.name = name;

		boolean createCategory = true;
		for (Category category : categories) {
			if (category.getName().equals(name)) {
				createCategory = false;
				break;
			}
		}

		if (createCategory) {
			try (Connection conn = getConnection();
			     PreparedStatement stmt = conn.prepareStatement("INSERT INTO Category (Name) VALUES (?)")) {
				stmt.setString(1, name);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		categories.add(this);
	}

	/**
	 * Gets the category's ID.
	 * @return The category's ID.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Gets the category's name.
	 * @return The category's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Caches all categories from the database locally in memory.
	 */
	public static void cacheAll() {
		try (Connection conn = getConnection();
		     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Category");
		     ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				new Category(rs.getInt("ID"), rs.getString("Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets a category entity by its name.
	 * @param name The category's name.
	 * @return The category entity, or null if not found.
	 */
	public static Category getCategory(String name) {
		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase(name)) {
				return category;
			}
		}

		return null;
	}

	/**
	 * Gets a category entity by its ID.
	 * @param ID The category's ID.
	 * @return The category entity, or null if not found.
	 */
	public static Category getCategoryByID(int ID) {
		for (Category category : categories) {
			if (category.getID() == ID) {
				return category;
			}
		}

		return null;
	}

	/**
	 * Gets all categories cached in memory.
	 * @return A set of all categories.
	 */
	public static HashSet<Category> getAllCategories() {
		return categories;
	}

	/**
	 * Deletes a category entity by its name.
	 * @param name The category's name.
	 */
	public static void deleteCategory(String name) {
		for (Category category : categories) {
			if (category.getName().equals(name)) {
				deleteCategory(category);
				return;
			}
		}
	}

	/**
	 * Deletes a category entity by its object.
	 * @param category The category entity to delete.
	 */
	public static void deleteCategory(Category category) {
		try (Connection conn = getConnection();
		     PreparedStatement stmt = conn.prepareStatement("DELETE FROM Category WHERE ID = ?")) {
			stmt.setInt(1, category.getID());
			stmt.executeUpdate();
			categories.remove(category);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
