package uk.ac.city.tab.order;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import uk.ac.city.resource.ResourceLoader;


public class OrdersTab extends BorderPane {
	protected static OrderHandler orderHandler;
	protected static HBox pane;

	public OrdersTab() {
		pane = new HBox();
		pane.setSpacing(10);

		ScrollPane scrollPane = new ScrollPane(pane);
		scrollPane.setPadding(new Insets(10));
		scrollPane.setStyle("-fx-background-color: #C5C5C5;");
		scrollPane.getStylesheets().add(ResourceLoader.getCSSFile("/styles/scrollpane.css"));
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		setLeft(scrollPane);

		boundsInLocalProperty().addListener((observable, oldValue, newValue) -> scrollPane.setPrefSize(getWidth(), getHeight()));

		// Start handling orders
		orderHandler = new OrderHandler();
		orderHandler.run();
	}
}
