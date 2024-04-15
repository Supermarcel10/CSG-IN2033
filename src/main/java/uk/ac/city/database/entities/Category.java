package uk.ac.city.database.entities;


public class Category {
	private int ID;
	private final String name;

	public Category(String name) {
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}
}
