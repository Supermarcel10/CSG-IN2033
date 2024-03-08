package com.example.myjava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;

public class MenuController {
    @FXML
    private ListView<MenuItem> firstCourseListView;

    private int number1;
    private int number2;
    public void initialize() {
        // Example items
        ObservableList<MenuItem> items = FXCollections.observableArrayList(
                new MenuItem("Warm Onion Tart", true),
                new MenuItem("Venison Pâté en Croûte", false),
                new MenuItem("Lasagna of Rabbit Shoulder", true),
                new MenuItem("Grilled Beef Tongue", true)
                // Add other items here...
        );

        firstCourseListView.setItems(items);

        firstCourseListView.setCellFactory(lv -> new ListCell<MenuItem>() {
            @Override
            protected void updateItem(MenuItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + (item.isAvailable() ? " (Available)" : " (Not Available)"));
                    setStyle("-fx-text-fill: " + (item.isAvailable() ? "green;" : "red;"));
                }
            }
        });
    }


}
