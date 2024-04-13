package uk.ac.city.resource;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.net.URL;


public class ResourceLoader {
	public static InputStream getResource(PredefinedResources resource) {
		return getResource(resource.getPath());
	}

	public static InputStream getResource(String path) {
		InputStream inputStream = ResourceLoader.class.getResourceAsStream(path);
		if (inputStream == null) {
			throw new IllegalArgumentException("Resource not found: " + path);
		}
		return inputStream;
	}

	public static Image getImageResource(PredefinedResources resource) {
		return new Image(getResource(resource));
	}

	public static Image getImageResource(String path) {
		return new Image(getResource(path));
	}

	public static URL getResourceAsURL(PredefinedResources resource) {
		return getResourceAsURL(resource.getPath());
	}

	public static URL getResourceAsURL(String path) {
		URL url = ResourceLoader.class.getResource(path);
		if (url == null) {
			throw new IllegalArgumentException("Resource not found: " + path);
		}
		return url;
	}
}
