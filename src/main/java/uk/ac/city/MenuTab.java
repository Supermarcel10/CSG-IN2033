package uk.ac.city;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.scene.control.cell.CheckBoxListCell;

public class MenuTab extends HBox {

	public MenuTab() {
		this.setSpacing(10);
		this.setPadding(new Insets(10));

		// Select Menu Section
		VBox selectMenuSection = createSelectMenuSection();

		// Menu Description Section
		VBox menuDescriptionSection = createMenuDescriptionSection();

		// Dish Description Section
		VBox dishDescriptionSection = createDishDescriptionSection();

		// Adding all sections to the HBox
		this.getChildren().addAll(selectMenuSection, menuDescriptionSection, dishDescriptionSection);
	}

	private VBox createSelectMenuSection() {
		ListView<String> menuList = new ListView<>();
		menuList.getItems().addAll("Winter 2023/24", "Autumn 2023", "Summer 2023", "Spring 2023");
		menuList.setPrefHeight(150);
		menuList.setPrefWidth(200); // Set a preferred width to match the design

		Button addMenuButton = new Button("Add Menu");
		VBox box = new VBox(5, new Label("Select Menu"), menuList, addMenuButton);
		box.setPadding(new Insets(10));

		return box;
	}

	private VBox createMenuDescriptionSection() {
		VBox box = new VBox(5);
		box.setPadding(new Insets(10));

		Label firstCourseLabel = new Label("First Course -");
		ListView<String> dishesList = new ListView<>();
		dishesList.getItems().addAll("Warm Onion Tart", "Venison Pâté en Croûte", "Lasagna of Rabbit Shoulder", "Grilled Beef Tongue");
		dishesList.setPrefHeight(150);
		dishesList.setPrefWidth(200); // Set a preferred width to match the design

		Button addDishButton = new Button("Add Dish");
		box.getChildren().addAll(new Label("Menu Description"), firstCourseLabel, dishesList, addDishButton);

		return box;
	}

	private VBox createDishDescriptionSection() {
		TextArea dishDescription = new TextArea();
		dishDescription.setText("Name: Warm Onion Tart\nAllergens: Casein, Gluten, Alliums\nIngredients: Quickes Goats Cheese, Worcestershire, Shallots\nRecipe:\n1. [Step 1]\n2. [Step 2]\n3. [Step 3]\n4. [Step 4]\n5. [Step 5]");
		dishDescription.setEditable(false);
		dishDescription.setPrefHeight(150);
		dishDescription.setPrefWidth(300); // Set a preferred width to match the design

		Button openMenuButton = new Button("Open Menu in new window");
		VBox box = new VBox(5, new Label("Dish Description"), dishDescription, openMenuButton);
		box.setPadding(new Insets(10));

		return box;
	}
}
