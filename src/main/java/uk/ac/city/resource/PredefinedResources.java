package uk.ac.city.resource;


/**
 * Enum containing paths to predefined resources for easy access and readability accross the application.
 */
public enum PredefinedResources {
	LOGO("/image/logo.jpeg");

	private final String path;

	PredefinedResources(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
