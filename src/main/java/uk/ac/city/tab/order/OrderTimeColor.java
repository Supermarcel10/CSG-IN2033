package uk.ac.city.tab.order;

import javafx.scene.paint.Paint;
import java.time.Duration;


enum OrderTimeColor {
	EARLY("#C8F9D8", "#47EB7E", "#063716", "#04240E"),
	MID("#C9DBF8", "#4986E9", "#092148", "#051124"),
	LATE("#F8CCC9", "#E8584A", "#360B07", "#240705");

	private final String backgroundColor;
	private final String brightAccentColor;
	private final String darkAccentColor;
	private final String textColor;

	OrderTimeColor(String backgroundColor, String brightAccentColor, String darkAccentColor, String textColor) {
		this.backgroundColor = backgroundColor;
		this.brightAccentColor = brightAccentColor;
		this.darkAccentColor = darkAccentColor;
		this.textColor = textColor;
	}

	static OrderTimeColor determineTimeColor(Duration timeSinceStart) {
		return timeSinceStart.toMinutes() < 15 ? EARLY : timeSinceStart.toMinutes() < 25 ? MID : LATE;
	}

	Paint getBackgroundColor() {
		return Paint.valueOf(backgroundColor);
	}

	String getBackgroundColorString() {
		return backgroundColor;
	}

	Paint getBrightAccentColor() {
		return Paint.valueOf(brightAccentColor);
	}

	String getBrightAccentColorString() {
		return brightAccentColor;
	}

	Paint getDarkAccentColor() {
		return Paint.valueOf(darkAccentColor);
	}

	String getDarkAccentColorString() {
		return darkAccentColor;
	}

	Paint getTextColor() {
		return Paint.valueOf(textColor);
	}

	String getTextColorString() {
		return textColor;
	}
}
