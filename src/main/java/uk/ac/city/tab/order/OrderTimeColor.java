package uk.ac.city.tab.order;

import javafx.scene.paint.Paint;
import java.time.Duration;


/**
 * Enum to determine the color of the order time based on how long it has been since the order was placed.
 */
enum OrderTimeColor {
	EARLY("#C8F9D8", "#47EB7E", "#063716", "#04240E"),
	MID("#C9DBF8", "#4986E9", "#092148", "#051124"),
	LATE("#F8CCC9", "#E8584A", "#360B07", "#240705");

	private final String backgroundColor;
	private final String brightAccentColor;
	private final String darkAccentColor;
	private final String textColor;

	/**
	 * Constructor for OrderTimeColor
	 * @param backgroundColor The default background color of the order
	 * @param brightAccentColor The bright accent color of the order
	 * @param darkAccentColor The dark accent color of the order
	 * @param textColor The default text color of the order
	 */
	OrderTimeColor(String backgroundColor, String brightAccentColor, String darkAccentColor, String textColor) {
		this.backgroundColor = backgroundColor;
		this.brightAccentColor = brightAccentColor;
		this.darkAccentColor = darkAccentColor;
		this.textColor = textColor;
	}

	/**
	 * Determines the color of the order time based on how long it has been since the order was placed.
	 * @param timeSinceStart The time since the order was placed
	 * @return The color of the order time
	 */
	static OrderTimeColor determineTimeColor(Duration timeSinceStart) {
		return timeSinceStart.toMinutes() < 15 ? EARLY : timeSinceStart.toMinutes() < 25 ? MID : LATE;
	}

	/**
	 * Gets the background color of the order
	 * @return The background color of the order
	 */
	Paint getBackgroundColor() {
		return Paint.valueOf(backgroundColor);
	}

	/**
	 * Gets the background color of the order as a string
	 * @return The background color of the order as a string

	 */
	String getBackgroundColorString() {
		return backgroundColor;
	}

	/**
	 * Gets the bright accent color of the order
	 * @return The bright accent color of the order
	 */
	Paint getBrightAccentColor() {
		return Paint.valueOf(brightAccentColor);
	}

	/**
	 * Gets the bright accent color of the order as a string
	 * @return The bright accent color of the order as a string
	 */
	String getBrightAccentColorString() {
		return brightAccentColor;
	}

	/**
	 * Gets the dark accent color of the order
	 * @return The dark accent color of the order
	 */
	Paint getDarkAccentColor() {
		return Paint.valueOf(darkAccentColor);
	}

	/**
	 * Gets the dark accent color of the order as a string
	 * @return The dark accent color of the order as a string
	 */
	String getDarkAccentColorString() {
		return darkAccentColor;
	}

	/**
	 * Gets the text color of the order
	 * @return The text color of the order
	 */
	Paint getTextColor() {
		return Paint.valueOf(textColor);
	}

	/**
	 * Gets the text color of the order as a string
	 * @return The text color of the order as a string
	 */
	String getTextColorString() {
		return textColor;
	}
}
