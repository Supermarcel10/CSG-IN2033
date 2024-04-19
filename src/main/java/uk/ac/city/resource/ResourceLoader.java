package uk.ac.city.resource;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;


/**
 * Utility class for loading resources from the classpath.
 */
public class ResourceLoader {
	/**
	 * Loads a predefined resource.
	 * @param resource The predefined resource to load.
	 * @see PredefinedResources
	 * @return An input stream for the resource.
	 */
	public static InputStream getResource(PredefinedResources resource) {
		return getResource(resource.getPath());
	}

	/**
	 * Loads a resource from the classpath.
	 * @param path The path to the resource.
	 * @return An input stream for the resource.
	 */
	public static InputStream getResource(String path) {
		InputStream inputStream = ResourceLoader.class.getResourceAsStream(path);
		if (inputStream == null) {
			throw new IllegalArgumentException("Resource not found: " + path);
		}
		return inputStream;
	}

	/**
	 * Loads a predefined image resource.
	 * @param resource The predefined image resource to load.
	 * @return An image object for the resource.
	 */
	public static Image getImageResource(PredefinedResources resource) {
		return new Image(getResource(resource));
	}

	/**
	 * Loads an image resource from the classpath.
	 * @param path The path to the image resource.
	 * @return An image object for the resource.
	 */
	public static Image getImageResource(String path) {
		return new Image(getResource(path));
	}

	/**
	 * Loads a predefined resource as a URL.
	 * @param resource The predefined resource to load.
	 * @return A URL object for the resource.
	 */
	public static URL getResourceAsURL(PredefinedResources resource) {
		return getResourceAsURL(resource.getPath());
	}

	/**
	 * Loads a resource from the classpath as a URL.
	 * @param path The path to the resource.
	 * @return A URL object for the resource.
	 */
	public static URL getResourceAsURL(String path) {
		URL url = ResourceLoader.class.getResource(path);
		if (url == null) {
			throw new IllegalArgumentException("Resource not found: " + path);
		}
		return url;
	}

	/**
	 * Loads a predefined CSS file.
	 * @param resource The predefined CSS file to load.
	 * @return A URL object for the CSS file.
	 */
	public static String getCSSFile(PredefinedResources resource) {
		return getCSSFile(resource.getPath());
	}

	/**
	 * Loads a CSS file from the classpath.
	 * @param path The path to the CSS file.
	 * @return A URL object for the CSS file.
	 */
	public static String getCSSFile(String path) {
		return Objects.requireNonNull(ResourceLoader.class.getResource(path)).toExternalForm();
	}
}
