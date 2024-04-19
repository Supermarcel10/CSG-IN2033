package uk.ac.city.tab.stock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uk.ac.city.Popup;
import uk.ac.city.Utils;
import uk.ac.city.database.entities.Category;
import uk.ac.city.database.entities.Ingredient;

import java.util.stream.Collectors;


/**
 * The StockTab class is a custom JavaFX component that represents the Stock tab in the main application window.
 */
public class StockTab extends HBox {
	ListView<String> categoryList;

	/**
	 * Creates a new StockTab object.
	 */
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

	/**
	 * Creates a VBox containing the waste section of the Stock tab.
	 * @return A VBox containing the waste section of the Stock tab.
	 */
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

	/**
	 * Creates a VBox containing the status section of the Stock tab.
	 * @return A VBox containing the status section of the Stock tab.
	 */
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

	/**
	 * Creates a VBox containing the ingredient list section of the Stock tab.
	 * @return A VBox containing the ingredient list section of the Stock tab.
	 */
	private VBox createIngredientListSection() {
		VBox box = new VBox(5);
		box.setPadding(new Insets(10));

		// Create a list of ingredients
		ListView<String> ingredientList = new ListView<>();

		ObservableList<String> ingredientNames = FXCollections.observableArrayList(
			Ingredient.getAllIngredients().stream()
				.map(Ingredient::getName)
				.map(Utils::toSentenceCase)
				.collect(Collectors.toList())
		);

		ingredientList.setItems(ingredientNames);
		ingredientList.setPrefHeight(150);
		ingredientList.setPrefWidth(200);

		Button addNewIngredientButton = new Button("New ingredient");
		addNewIngredientButton.setOnAction(event -> handleAddIngredientButton(ingredientList));

		box.getChildren().addAll(new Label("Ingredient List"), ingredientList, addNewIngredientButton);

		return box;
	}

	/**
	 * Creates a VBox containing the select category section of the Stock tab.
	 * @return A VBox containing the select category section of the Stock tab.
	 */
	private VBox createSelectCategorySection() {
		// Create a list of categories
		categoryList = new ListView<>();

		ObservableList<String> categoryNames = FXCollections.observableArrayList(
			Category.getAllCategories().stream()
				.map(Category::getName)
				.map(Utils::toSentenceCase)
				.collect(Collectors.toList())
		);

		categoryList.setItems(categoryNames);
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

	/**
	 * Handles the action when the "Add Ingredient" button is clicked.
	 * @param ingredientList The list of ingredients.
	 */
	private void handleAddIngredientButton(ListView<String> ingredientList) {
		String ingredientName = Popup.showTextInputPopup("Enter the name of the new ingredient");
		if (ingredientName == null || ingredientName.isEmpty()) {
			Popup.showWarningPopup("Invalid Input", "Ingredient name cannot be empty!");
		} else if (ingredientList.getItems().contains(ingredientName.toLowerCase())) {
			Popup.showWarningPopup("Invalid Input", "Ingredient name already exists!");
		} else {
			ingredientList.getItems().add(ingredientName);
			System.out.println(categoryList.getSelectionModel().getSelectedItem().toLowerCase());
			System.out.println(Category.getCategory(categoryList.getSelectionModel().getSelectedItem().toLowerCase()));
			new Ingredient(ingredientName.toLowerCase(), Category.getCategory(categoryList.getSelectionModel().getSelectedItem().toLowerCase()));
		}
	}

	/**
	 * Handles the action when the "Add Category" button is clicked.
	 * @param categoryList The list of categories.
	 */
	private void handleAddCategoryButton(ListView<String> categoryList) {
		String categoryName = Popup.showTextInputPopup("Enter the name of the new category");
		if (categoryName == null || categoryName.isEmpty()) {
			Popup.showWarningPopup("Invalid Input", "Category name cannot be empty!");
		} else if (categoryList.getItems().contains(categoryName.toLowerCase())) {
			Popup.showWarningPopup("Invalid Input", "Category name already exists!");
		} else {
			categoryList.getItems().add(categoryName);
			new Category(categoryName.toLowerCase());
		}
	}

	/**
	 * Handles the action when the "Remove Category" button is clicked.
	 * @param categoryList The list of categories.
	 */
	private void handleRemoveCategoryButton(ListView<String> categoryList) {
		String selectedCategory = categoryList.getSelectionModel().getSelectedItem();
		if (selectedCategory == null) {
			Popup.showWarningPopup("Invalid Input", "Please select a category to remove!");
		} else if (Popup.showYesNoPopup("Are you sure you want to remove this category?")) {
			categoryList.getItems().remove(categoryList.getSelectionModel().getSelectedItem());
			Category.deleteCategory(selectedCategory.toLowerCase());
		}
	}
}
