package uk.ac.city.entities;

import java.time.LocalDateTime;


public class IngredientChangeUsage extends IngredientChangeEntity {
	private final UsageType usageType;

	public enum UsageType {
		USED,
		WASTED,
		EXPIRED
	}

	public IngredientChangeUsage(Ingredient ingredient, LocalDateTime dateTime, int quantity, UsageType usageType) {
		this.ingredient = ingredient;
		this.dateTime = dateTime;
		this.quantity = quantity;
		this.usageType = usageType;
	}

	public UsageType getUsageType() {
		return usageType;
	}
}
