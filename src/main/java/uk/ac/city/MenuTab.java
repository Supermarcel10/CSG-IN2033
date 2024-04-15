package uk.ac.city;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;
import uk.ac.city.database.Database;

public class MenuTab extends HBox {

	private Map<String, Map<String, List<String>>> menus;
	private VBox menuListSection, menuDescriptionSection, dishDescriptionSection;
	private ListView<String> menuListView;
	private TextArea dishDescriptionTextArea;
	private DatePicker createdDatePicker;

	public MenuTab() {
		this.setSpacing(10);
		this.setPadding(new Insets(10));
		this.setStyle("-fx-background-color: #FFFFFF;");

		menus = new HashMap<>();

		// Initialize UI components
		menuListSection = createMenuListSection();
		menuDescriptionSection = createMenuDescriptionSection();
		dishDescriptionSection = createDishDescriptionSection();

		// Add sections to the main layout
		HBox.setHgrow(menuListSection, Priority.ALWAYS);
		HBox.setHgrow(menuDescriptionSection, Priority.ALWAYS);
		HBox.setHgrow(dishDescriptionSection, Priority.ALWAYS);
		this.getChildren().addAll(menuListSection, menuDescriptionSection, dishDescriptionSection);

		// Load initial data and set up the UI
		loadInitialData();
		selectMostRecentMenu();
	}

	// Assume this method is called to load initial data into the application
	private void loadInitialData() {
		// Dummy data for illustration purposes
		List<String> winterDishes = Arrays.asList("Warm Onion Tart", "Venison Pâté en Croûte");
		List<String> springDishes = Arrays.asList("Spring Veggie Broth", "Lamb Stew");

		// Menu structure: Menu -> Course -> Dishes
		menus.put("Winter 2023/24", new LinkedHashMap<>());
		menus.get("Winter 2023/24").put("First Course", new ArrayList<>(winterDishes));

		menus.put("Spring 2024", new LinkedHashMap<>());
		menus.get("Spring 2024").put("First Course", new ArrayList<>(springDishes));

		// Update the menu list view
		updateMenuListView();
	}

	// Refreshes the ListView with menu names
	private void updateMenuListView() {
		ObservableList<String> menuNames = FXCollections.observableArrayList(menus.keySet());
		menuListView.setItems(menuNames);
	}

	// Selects the most recent menu in the ListView
	private void selectMostRecentMenu() {
		if (!menus.isEmpty()) {
			String mostRecentMenu = menus.keySet().iterator().next(); // Assuming a sorted map
			menuListView.getSelectionModel().select(mostRecentMenu);
			updateMenuDescription(mostRecentMenu);
		}
	}

	// Updates the menu description section based on the selected menu
	// Updates the menu description section based on the selected menu
	private void updateMenuDescription(String menuName) {
		menuDescriptionSection.getChildren().clear(); // Clear previous details

		Label menuHeader = new Label(menuName);
		menuHeader.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		menuDescriptionSection.getChildren().add(menuHeader);

		Map<String, List<String>> courses = menus.getOrDefault(menuName, Collections.emptyMap());
		courses.forEach((courseName, dishes) -> {
			HBox courseHeaderBox = new HBox(10);
			Label toggleLabel = new Label("-");
			toggleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;");

			Label courseLabel = new Label(courseName);
			courseLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

			Button removeCourseButton = new Button("X");
			removeCourseButton.setOnAction(event -> {
				boolean confirmation = showConfirmationAlert(
					"Remove Course",
					"Are you sure you want to remove the course '" + courseName + "' from the menu?"
				);
				if (confirmation) {
					removeCourseFromMenu(menuName, courseName);
				}
			});

			courseHeaderBox.getChildren().addAll(toggleLabel, courseLabel, removeCourseButton);
			menuDescriptionSection.getChildren().add(courseHeaderBox);

			VBox dishesBox = new VBox(5);
			dishes.forEach(dish -> {
				HBox dishBox = new HBox(10);
				Label dishLabel = new Label(dish);
				dishLabel.setStyle("-fx-font-size: 14px;");
				dishLabel.setOnMouseClicked(event -> updateDishDescriptionSection(dish)); // Set click action
				Button removeDishButton = new Button("X");
				removeDishButton.setOnAction(event -> removeDishFromCourse(menuName, courseName, dish));
				dishBox.getChildren().addAll(dishLabel, removeDishButton);
				dishesBox.getChildren().add(dishBox);
			});

			Button addDishButton = new Button("Add Dish");
			addDishButton.setOnAction(event -> addDishToCourse(menuName, courseName));
			dishesBox.getChildren().add(addDishButton);

			// Initially set dishesBox visible
			dishesBox.setVisible(true);
			addDishButton.setVisible(true);

			toggleLabel.setOnMouseClicked(event -> {
				boolean isExpanded = !dishesBox.isVisible();
				dishesBox.setVisible(isExpanded);
				addDishButton.setVisible(isExpanded);
				toggleLabel.setText(isExpanded ? "-" : "+");
			});

			menuDescriptionSection.getChildren().add(dishesBox);
		});

		Button addCourseButton = new Button("Add Course");
		addCourseButton.setOnAction(event -> addCourseToMenu(menuName));
		menuDescriptionSection.getChildren().add(addCourseButton);

		// Date Picker at the bottom
		createdDatePicker = new DatePicker();
		createdDatePicker.setValue(LocalDate.now()); // This should be the creation date of the menu
		createdDatePicker.setEditable(false);
		HBox dateBox = new HBox(new Label("Created:"), createdDatePicker);
		dateBox.setAlignment(Pos.CENTER_LEFT);
		menuDescriptionSection.getChildren().add(dateBox);

		// in the constructor, after initializing components
		menuListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				// newValue is the selected menu name
				updateMenuDescription(newValue);
				// Set the initial dish description for the selected menu
				String firstCourse = menus.get(newValue).keySet().iterator().next();
				String firstDish = menus.get(newValue).get(firstCourse).get(0);
				updateDishDescriptionSection(firstDish);
			}
		});

	}


	private boolean showConfirmationAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);

		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && result.get() == ButtonType.OK;
	}

	// Method to remove a course from a menu with a confirmation dialog
	private void removeCourseFromMenu(String menuName, String courseName) {
		menus.get(menuName).remove(courseName);
		updateMenuDescription(menuName);
		if (menus.get(menuName).isEmpty()) {
			menus.remove(menuName);
			updateMenuListView();
			if (menus.isEmpty()) {
				menuDescriptionSection.getChildren().clear();
			} else {
				// Select the first menu available after removal
				selectMostRecentMenu();
			}
		}
	}



	// This function will be called to add a dish to a course
	// This function will be called to add a dish to a course
	private void addDishToCourse(String menuName, String courseName) {
		Dialog<Map<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Add New Dish");
		dialog.setHeaderText("Add a new dish to " + courseName);
		ButtonType addDishButtonType = new ButtonType("Add Dish", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addDishButtonType, ButtonType.CANCEL);

		GridPane grid = setupDishDialogGrid();
		dialog.getDialogPane().setContent(grid);

		Platform.runLater(() -> ((TextField) grid.getChildren().get(1)).requestFocus());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == addDishButtonType) {
				return extractDishDetailsFromDialog(grid);
			}
			return null;
		});

		Optional<Map<String, String>> result = dialog.showAndWait();
		result.ifPresent(dishDetails -> {
			try {
				insertDishIntoDatabase(dishDetails);
				updateMenuDescription(menuName); // Refresh UI after insertion
			} catch (SQLException e) {
				showAlert("Database Error", "Failed to add new dish to the database.");
			}
		});
	}

	private GridPane setupDishDialogGrid() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		grid.add(new Label("Dish Name:"), 0, 0);
		grid.add(new TextField(), 1, 0);
		grid.add(new Label("Allergens:"), 0, 1);
		grid.add(new TextArea(), 1, 1);
		grid.add(new Label("Ingredients:"), 0, 2);
		grid.add(new TextArea(), 1, 2);
		grid.add(new Label("Recipe:"), 0, 3);
		grid.add(new TextArea(), 1, 3);
		return grid;
	}

	private Map<String, String> extractDishDetailsFromDialog(GridPane grid) {
		Map<String, String> details = new HashMap<>();
		details.put("name", ((TextField) grid.getChildren().get(1)).getText());
		details.put("allergens", ((TextArea) grid.getChildren().get(3)).getText());
		details.put("ingredients", ((TextArea) grid.getChildren().get(5)).getText());
		details.put("recipe", ((TextArea) grid.getChildren().get(7)).getText());
		return details;
	}

	private void insertDishIntoDatabase(Map<String, String> dishDetails) throws SQLException {
		String sql = "INSERT INTO dishes (name, allergens, ingredients, recipe) VALUES (?, ?, ?, ?)";
		try (Connection conn = Database.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, dishDetails.get("name"));
			stmt.setString(2, dishDetails.get("allergens"));
			stmt.setString(3, dishDetails.get("ingredients"));
			stmt.setString(4, dishDetails.get("recipe"));
			stmt.executeUpdate();
		}
	}

	// This method needs to be implemented to handle adding the dish details to the menu structure.
	private void addDishDetailsToCourse(String menuName, String courseName, Map<String, String> dishDetails) {
		// Here you will add the dish to the menu data structure
		// This is just an example, you'll need to adjust based on your actual data structure
		String dishName = dishDetails.get("name");
		// The details should be saved in a way that they can be retrieved later, not just the name
		menus.get(menuName).computeIfAbsent(courseName, k -> new ArrayList<>()).add(dishName);
		// Possibly save the dish details to a dish object or map
		// saveDishDetails(dishName, dishDetails);
	}



	// Updates the dish description section based on the selected dish


	private VBox createMenuListSection() {
		VBox section = new VBox(5);
		section.setPadding(new Insets(10));
		Label header = new Label("Select Menu");
		header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

		menuListView = new ListView<>();
		menuListView.setPrefHeight(150);
		menuListView.setPrefWidth(200);

		Button addMenuButton = new Button("Add Menu");
		addMenuButton.setOnAction(event -> addNewMenu());

		section.getChildren().addAll(header, menuListView, addMenuButton);
		return section;
	}


	private VBox createDishDescriptionSection() {
		VBox section = new VBox(5);
		section.setPadding(new Insets(10));

		Label header = new Label("Dish Description");
		header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

		dishDescriptionTextArea = new TextArea();
		dishDescriptionTextArea.setEditable(false);
		dishDescriptionTextArea.setPrefHeight(150);
		dishDescriptionTextArea.setPrefWidth(300);

		Button openInNewWindowButton = new Button("Open Menu in new window");
		openInNewWindowButton.setOnAction(event -> openMenuInNewWindow());


		section.getChildren().addAll(header, dishDescriptionTextArea, openInNewWindowButton);
		return section;
	}



// ... continuation of the MenuTab class



	// Removes a dish from a course
	private void removeDishFromCourse(String menuName, String courseName, String dish) {
		menus.get(menuName).get(courseName).remove(dish);
		if (menus.get(menuName).get(courseName).isEmpty()) {
			// If no dishes are left in the course, remove the course
			menus.get(menuName).remove(courseName);
		}
		// If no courses are left in the menu, remove the menu
		if (menus.get(menuName).isEmpty()) {
			menus.remove(menuName);
			updateMenuListView();
			if (!menus.isEmpty()) {
				// Select another menu
				selectMostRecentMenu();
			} else {
				// Clear the menu description section if all menus are removed
				menuDescriptionSection.getChildren().clear();
			}
		} else {
			// Refresh the UI to show the updated courses and dishes
			updateMenuDescription(menuName);
		}
	}

	// Adds a new course to the specified menu
	private void addCourseToMenu(String menuName) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Add New Course");
		dialog.setHeaderText("Add a new course to " + menuName);
		dialog.setContentText("Please enter the name of the course:");

		Optional<String> result = dialog.showAndWait();
		result.ifPresent(courseName -> {
			if (!courseName.trim().isEmpty() && !menus.get(menuName).containsKey(courseName)) {
				menus.get(menuName).put(courseName, new ArrayList<>());
				updateMenuDescription(menuName);
			}
		});
	}


	// Adds a new menu
	private void addNewMenu() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Add New Menu");
		dialog.setHeaderText("Create a new menu");
		dialog.setContentText("Menu name:");

		Optional<String> result = dialog.showAndWait();
		result.ifPresent(menuName -> {
			if (!menuName.trim().isEmpty() && !menus.containsKey(menuName)) {
				// Create a new menu with no courses
				menus.put(menuName, new LinkedHashMap<>());
				updateMenuListView();
				menuListView.getSelectionModel().select(menuName);
				// We could also open a new dialog to immediately add courses to the new menu here
			} else {
				// Handle the case where the menu name is empty or already exists
				showAlert("Invalid Menu Name", "Please enter a unique name for the menu.");
			}
		});
	}

	private void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	// Update dish description when a dish is selected
	private void updateDishDescriptionSection(String dish) {
		// TODO: Fetch the actual details of the dish from your data source.
		String dishDetails = String.format(
			"Name: %s\nAllergens: %s\nIngredients: %s\nRecipe: %s",
			dish,
			"Allergens details", // Replace with actual data
			"Ingredients details", // Replace with actual data
			"Recipe steps" // Replace with actual data
		);
		dishDescriptionTextArea.setText(dishDetails);
	}




	// Create the Menu Description Section
	private VBox createMenuDescriptionSection() {
		VBox section = new VBox(5);
		section.setPadding(new Insets(10));
		// The menu description header is added dynamically in updateMenuDescription
		return section;
	}

	// Create the Dish Description Section


	// Open the current menu in a new window
	private void openMenuInNewWindow() {
		// Create a new stage (window)
		Stage stage = new Stage();
		stage.setTitle("Menu Details");

		// Create a new text area for displaying the details, copying the content from the existing one
		TextArea textArea = new TextArea(dishDescriptionTextArea.getText());
		textArea.setEditable(false);
		textArea.setWrapText(true);

		// Set the scene
		Scene scene = new Scene(new StackPane(textArea), 300, 400);
		stage.setScene(scene);

		// Show the new stage
		stage.show();
	}


// ... rest of the MenuTab class

}
