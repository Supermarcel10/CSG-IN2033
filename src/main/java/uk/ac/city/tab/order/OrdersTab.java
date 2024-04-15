package uk.ac.city.tab.order;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import uk.ac.city.resource.ResourceLoader;


public class OrdersTab extends BorderPane {
	protected static OrderHandler orderHandler;
	protected static HBox pane;

	public OrdersTab() {
		// Left side
		ScrollPane left = handleLeft();
		setLeft(left);

		// Right side
		VBox right = handleRight();
		setRight(right);

		boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
			left.setPrefWidth((getWidth() * 3) / 4);
			right.setPrefWidth((getWidth() * 1) / 4);
		});

		// Start handling orders
		orderHandler = new OrderHandler();
		orderHandler.run();
	}

	private ScrollPane handleLeft() {
		pane = new HBox();
		pane.setSpacing(10);

		ScrollPane left = new ScrollPane(pane);
		left.setPrefWidth((getWidth() * 3) / 4);
		left.setPrefHeight(getHeight());
		left.setStyle("-fx-background-color: #C5C5C5;");
		left.getStylesheets().add(ResourceLoader.getCSSFile("/styles/scrollpane.css"));
		left.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		left.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		return left;
	}

	private VBox handleRight() {
		VBox right = new VBox();
		right.setStyle("-fx-background-color: #C5C5C5; -fx-border-color: #B8B8B8; -fx-border-width: .1em; -fx-border-style: hidden hidden hidden solid;");

		return right;
	}
}
