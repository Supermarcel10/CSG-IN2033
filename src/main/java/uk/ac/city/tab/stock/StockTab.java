package uk.ac.city.tab.stock;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class StockTab extends HBox {
	public StockTab() {
		this.setSpacing(10);
		this.setPadding(new Insets(10));
		this.setStyle("-fx-background-color: #C5C5C5;");

		VBox selectCategorySection = createSelectCategorySection();
		VBox ingredientListSection = createIngredientListSection();
		VBox statusSection = createStatusSection();
		VBox wasteSection = createWasteSection();

		this.getChildren().addAll(selectCategorySection, ingredientListSection, statusSection, wasteSection);
	}

	private VBox createWasteSection() {
		VBox box = new VBox(5);
		box.setPadding(new Insets(10));

		Label todayLabel = new Label("Today");
		ListView<String> wasteList = new ListView<>();
		wasteList.getItems().addAll("0", "0", "1", "2", "1", "0");

		Button addWasteButton = new Button("Add Waste");
		box.getChildren().addAll(new Label("Waste"), todayLabel, wasteList, addWasteButton);

		return box;
	}

	private VBox createStatusSection() {
		VBox box = new VBox(5);
		box.setPadding(new Insets(10));

		Label categoryLabel = new Label("Fruits & Veg");
		ListView<String> stockList = new ListView<>();
		stockList.getItems().addAll("Low Stock", "Low Stock", "Good Stock", "Out of Stock", "Good Stock", "Good Stock");
		Label overallLabel = new Label("Overall: Good");
		box.getChildren().addAll(new Label("Status"), categoryLabel, stockList, overallLabel);

		return box;
	}

	private VBox createIngredientListSection() {
		VBox box = new VBox(5);
		box.setPadding(new Insets(10));

		ListView<String> ingredientList = new ListView<>();
		ingredientList.getItems().addAll("Shallots", "Mustard Fruit", "Thyme", "Quince", "Beetroot", "Spring Onion");
		ingredientList.setPrefHeight(150);
		ingredientList.setPrefWidth(200);

		Button addNewIngredientButton = new Button("New ingredient");
		box.getChildren().addAll(new Label("Ingredient List"), ingredientList, addNewIngredientButton);

		return box;
	}

	private VBox createSelectCategorySection() {
		ListView<String> categoryList = new ListView<>();
		categoryList.getItems().addAll("Fruit & Veg", "Dry Fruits", "Carbohydrates", "Meats", "Dairy", "Herbs & Spices", "Others");
		categoryList.setPrefHeight(150);
		categoryList.setPrefWidth(200);

		Button addCategoryButton = new Button("Add Category");
		VBox box = new VBox(5, new Label("Select Category"), categoryList, addCategoryButton);
		box.setPadding(new Insets(10));

		return box;
	}
}
