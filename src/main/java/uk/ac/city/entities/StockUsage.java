package uk.ac.city.entities;

import java.time.LocalDateTime;


public class StockUsage extends StockEntity {
	private final UsageType usageType;

	public enum UsageType {
		USED,
		WASTED,
		EXPIRED
	}

	public StockUsage(Item item, LocalDateTime dateTime, int quantity, UsageType usageType) {
		this.item = item;
		this.dateTime = dateTime;
		this.quantity = quantity;
		this.usageType = usageType;
	}

	public UsageType getUsageType() {
		return usageType;
	}
}
