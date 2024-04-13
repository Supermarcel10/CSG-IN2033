package uk.ac.city.resource;


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
