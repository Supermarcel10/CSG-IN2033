package uk.ac.city.database.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import static uk.ac.city.database.Database.getConnection;


public class Category {
	protected static HashSet<Category> categories = new HashSet<>();

	private int ID;
	private final String name;

	public Category(int ID, String name) {
		this.ID = ID;
		this.name = name;

		categories.add(this);
	}

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

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

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

	public static Category getCategory(String name) {
		for (Category category : categories) {
			if (category.getName().equalsIgnoreCase(name)) {
				return category;
			}
		}

		return null;
	}

	public static Category getCategoryByID(int ID) {
		for (Category category : categories) {
			if (category.getID() == ID) {
				return category;
			}
		}

		return null;
	}

	public static HashSet<Category> getAllCategories() {
		return categories;
	}

	public static void deleteCategory(String name) {
		for (Category category : categories) {
			if (category.getName().equals(name)) {
				deleteCategory(category);
				return;
			}
		}
	}

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
