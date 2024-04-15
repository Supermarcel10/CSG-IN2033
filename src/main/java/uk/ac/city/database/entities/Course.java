package uk.ac.city.database.entities;


public class Course {
	private int ID;
	private final String name;

	public Course(String name) {
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}
}
