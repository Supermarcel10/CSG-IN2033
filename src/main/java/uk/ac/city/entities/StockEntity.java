package uk.ac.city.entities;

import java.time.LocalDateTime;


class StockEntity {
	protected int ID;
	protected Item item;
	protected LocalDateTime dateTime;
	protected int quantity;

	public int getID() {
		return ID;
	}

	public Item getItem() {
		return item;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public int getQuantity() {
		return quantity;
	}
}
