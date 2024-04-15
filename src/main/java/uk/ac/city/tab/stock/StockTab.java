package uk.ac.city.tab.stock;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uk.ac.city.Popup;


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
		addCategoryButton.setOnAction(event -> handleAddCategoryButton(categoryList));

		Button removeCategoryButton = new Button("Remove Category");
		removeCategoryButton.setOnAction(event -> handleRemoveCategoryButton(categoryList));

		VBox box = new VBox(5, new Label("Select Category"), categoryList, addCategoryButton, removeCategoryButton);
		box.setPadding(new Insets(10));

		return box;
	}

	private void handleAddCategoryButton(ListView<String> categoryList) {
		String categoryName = Popup.showTextInputPopup("Enter the name of the new category");
		if (categoryName == null || categoryName.isEmpty()) {
			Popup.showWarningPopup("Invalid Input", "Category name cannot be empty!");
		} else if (categoryList.getItems().contains(categoryName)) {
			Popup.showWarningPopup("Invalid Input", "Category name already exists!");
		} else {
			categoryList.getItems().add(categoryName);
		}
	}

	private void handleRemoveCategoryButton(ListView<String> categoryList) {
		String selectedCategory = categoryList.getSelectionModel().getSelectedItem();
		if (selectedCategory == null) {
			Popup.showWarningPopup("Invalid Input", "Please select a category to remove!");
		} else if (Popup.showYesNoPopup("Are you sure you want to remove this category?")) {
			categoryList.getItems().remove(categoryList.getSelectionModel().getSelectedItem());
		}
	}
}
