package uk.ac.city.database.entities;


/**
 * Represents a course entity in the database.
 */
public class Course {
	private int ID;
	private final String name;

	/**
	 * Creates a new course with the given name.
	 *
	 * @param name The name of the course.
	 */
	public Course(String name) {
		this.name = name;
	}

	/**
	 * Gets the ID of the course.
	 * @return The ID of the course.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Gets the name of the course.
	 * @return The name of the course.
	 */
	public String getName() {
		return name;
	}
}
