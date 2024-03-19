package uk.ac.city.entities;

import java.time.LocalDateTime;


public class StockIntake extends StockEntity {
	public StockIntake(Item item, LocalDateTime dateTime, int quantity) {
		this.item = item;
		this.dateTime = dateTime;
		this.quantity = quantity;
	}
}
