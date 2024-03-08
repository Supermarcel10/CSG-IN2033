package uk.ac.city;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class MenuTab extends VBox {

	private final ObservableList<MenuItem> menuItems;

	public MenuTab() {
		// Sample menu items, this could be loaded from a database or file in a real application
		menuItems = FXCollections.observableArrayList(
			new MenuItem("Warm Onion Tart", 12.0, true),
			new MenuItem("Venison Pâté en Croûte", 13.0, false),
			new MenuItem("Lasagna of Rabbit Shoulder", 12.0, true),
			new MenuItem("Grilled Beef Tongue", 14.0, true)
		);

		ListView<MenuItem> listView = new ListView<>(menuItems);
		listView.setCellFactory(CheckBoxListCell.forListView(
			item -> item.isAvailableProperty(),
			new StringConverter<MenuItem>() {
				@Override
				public String toString(MenuItem item) {
					// String representation for the ListView cell
					return String.format("%s £%.2f", item.getName(), item.getPrice());
				}

				@Override
				public MenuItem fromString(String string) {
					// Conversion from string to MenuItem is not required for this ListView
					return null;
				}
			}
		));

		this.getChildren().add(listView);
	}
}

class MenuItem {
	private final String name;
	private final double price;
	private final SimpleBooleanProperty isAvailable;

	public MenuItem(String name, double price, boolean isAvailable) {
		this.name = name;
		this.price = price;
		this.isAvailable = new SimpleBooleanProperty(isAvailable);
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public boolean isAvailable() {
		return isAvailable.get();
	}

	public SimpleBooleanProperty isAvailableProperty() {
		return isAvailable;
	}

	public void setAvailable(boolean available) {
		isAvailable.set(available);
	}
}
