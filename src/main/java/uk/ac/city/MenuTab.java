package uk.ac.city;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.input.MouseEvent;

public class MenuTab extends HBox {

	private ListView<String> dishesList;
	private Button addDishButton;

	public MenuTab() {
		this.setSpacing(10);
		this.setPadding(new Insets(10));
		this.setStyle("-fx-background-color: #FFFFFF;"); // Set background to white

		// Select Menu Section
		VBox selectMenuSection = createSelectMenuSection();

		// Menu Description Section
		VBox menuDescriptionSection = createMenuDescriptionSection();

		// Dish Description Section
		VBox dishDescriptionSection = createDishDescriptionSection();

		// Adding all sections to the HBox
		HBox.setHgrow(selectMenuSection, Priority.ALWAYS);
		HBox.setHgrow(menuDescriptionSection, Priority.ALWAYS);
		HBox.setHgrow(dishDescriptionSection, Priority.ALWAYS);
		this.getChildren().addAll(selectMenuSection, menuDescriptionSection, dishDescriptionSection);
	}

	private VBox createSelectMenuSection() {
		// 'Select Menu' header
		Label selectMenuHeader = new Label("Select Menu");
		selectMenuHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

		// Search box
		TextField searchField = new TextField();
		searchField.setPromptText("Search...");
		searchField.setMaxWidth(Double.MAX_VALUE);

		// Menu list
		ListView<String> menuList = new ListView<>();
		menuList.getItems().addAll("Winter 2023/24", "Autumn 2023", "Summer 2023", "Spring 2023");
		menuList.setPrefHeight(150);
		menuList.setPrefWidth(200);

		// Scroll pane for the menu list
		ScrollPane scrollPane = new ScrollPane(menuList);
		scrollPane.setFitToWidth(true);

		// 'Add Menu' button
		Button addMenuButton = new Button("Add Menu");

		VBox box = new VBox(5, selectMenuHeader, searchField, scrollPane, addMenuButton);
		box.setPadding(new Insets(10));
		VBox.setVgrow(scrollPane, Priority.ALWAYS);

		return box;
	}

	private VBox createMenuDescriptionSection() {
		VBox box = new VBox(5);
		box.setPadding(new Insets(10));

		Label menuDescriptionHeader = new Label("Menu Description");
		menuDescriptionHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

		// First Course label that toggles the list and button
		Label firstCourseLabel = new Label("First Course -");
		firstCourseLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 5px; -fx-cursor: hand;");
		firstCourseLabel.setOnMouseClicked(event -> toggleCourse(firstCourseLabel));

		// List for dishes, initially visible
		dishesList = new ListView<>();
		dishesList.getItems().addAll("Warm Onion Tart", "Venison Pâté en Croûte", "Lasagna of Rabbit Shoulder", "Grilled Beef Tongue");
		dishesList.setPrefHeight(150);
		dishesList.setPrefWidth(200);

		// Add Dish button, initially visible
		addDishButton = new Button("Add Dish");

		// Add Course button
		Button addCourseButton = new Button("Add Course");

		// Created label and date field
		Label createdLabel = new Label("Created:");
		DatePicker datePicker = new DatePicker();
		HBox createdBox = new HBox(5, createdLabel, datePicker);
		createdBox.setAlignment(Pos.CENTER_LEFT);

		// Adding components to the VBox for the Menu Description section
		box.getChildren().addAll(menuDescriptionHeader, firstCourseLabel, dishesList, addDishButton, addCourseButton, createdBox);

		return box;
	}


	private void toggleCourse(Label label) {
		// Check the current text and toggle between "-" and "+"
		boolean isExpanded = label.getText().endsWith("-");
		label.setText("First Course " + (isExpanded ? "+" : "-"));

		// Toggle visibility of dishesList and addDishButton
		dishesList.setVisible(isExpanded);
		addDishButton.setVisible(isExpanded);
	}


	private VBox createDishDescriptionSection() {
		// 'Dish Description' header with bold style
		Label dishDescriptionHeader = new Label("Dish Description");
		dishDescriptionHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

		// Dish description text area
		TextArea dishDescription = new TextArea();
		dishDescription.setText("Name: Warm Onion Tart\nAllergens: Casein, Gluten, Alliums\nIngredients: Quickes Goats Cheese, Worcestershire, Shallots\nRecipe:\n1. [Step 1]\n2. [Step 2]\n3. [Step 3]\n4. [Step 4]\n5. [Step 5]");
		dishDescription.setEditable(false);
		dishDescription.setPrefHeight(150);
		dishDescription.setPrefWidth(300);

		// 'Open Menu in new window' button
		Button openMenuButton = new Button("Open Menu in new window");

		VBox box = new VBox(5, dishDescriptionHeader, dishDescription, openMenuButton);
		box.setPadding(new Insets(10));

		return box;
	}

}
