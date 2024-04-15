package uk.ac.city.tab.order;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uk.ac.city.database.entities.Order;
import uk.ac.city.resource.ResourceLoader;
import java.time.Duration;
import java.time.LocalDateTime;

import static uk.ac.city.tab.order.OrderHandler.removeOrder;


class OrderDisplay extends BorderPane {
	private final Order order;
	private final HBox top = new HBox();
	private Timeline timeline;
	private OrderTimeColor timeColor;
	private Text timeText;

	OrderDisplay(Order order) {
		OrdersTab.pane.getBoundsInLocal();

		setPrefSize(300, 400); // TODO: Update this to be dynamic

		this.order = order;
		this.timeColor = OrderTimeColor.EARLY;

		// Info section
		HBox top = renderTop();
		setTop(top);

		// Content section
		HBox content = renderContent();
		setCenter(content);

		// Button section
		HBox bottom = renderBottom();
		setBottom(bottom);

		updateColorStyle();
	}

	private HBox renderTop() {
		top.setAlignment(Pos.TOP_CENTER);
		top.setSpacing(20);

		// Display the order duration
		timeText = new Text(formatTime(order.getOrderDateTime()));
		timeText.setFill(Paint.valueOf("#FFFFFF"));
		timeText.setFont(Font.font(20));
		timeText.getStyleClass().add("order-time");

		// Display the table number
		Text tableNumberText = new Text(String.format("T%d", order.getTableNumber()));
		tableNumberText.setFont(Font.font(20));
		tableNumberText.setFill(Paint.valueOf("#FFFFFF"));

		startOrderTimeCounter();

		top.getChildren().addAll(tableNumberText, timeText);
		return top;
	}

	private HBox renderContent() {
		HBox content = new HBox();

		VBox orderItemsBox = new VBox();
		orderItemsBox.setAlignment(Pos.CENTER_LEFT);
		orderItemsBox.setPadding(new Insets(10));
		orderItemsBox.setSpacing(5);

		ScrollPane scrollPane = new ScrollPane(orderItemsBox);
		scrollPane.setPrefSize(200, 300); // TODO: Make this dynamic
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		VBox.setVgrow(scrollPane, Priority.ALWAYS);

		order.getDishesWithQuantities().forEach((dish, quantity) -> {
			for (int i = 0; i < quantity; i++) {
				Text orderItemText = new Text(dish.getName());
				orderItemText.setFont(Font.font(16));
				orderItemsBox.getChildren().add(orderItemText);

				orderItemText.onMouseClickedProperty().set(event -> {
					orderItemText.setStrikethrough(!orderItemText.isStrikethrough());
					orderItemText.setFill(orderItemText.isStrikethrough() ? Paint.valueOf("#999999") : Paint.valueOf("#000000"));
				});
			}
		});

		content.getChildren().add(scrollPane);
		return content;
	}

	private HBox renderBottom() {
		HBox bottom = new HBox();
		bottom.setAlignment(Pos.TOP_CENTER);
		bottom.setSpacing(10);
		bottom.setPadding(new Insets(10, 0, 10, 0));

		// Button to mark the order as complete
		Button completeButton = new Button("Complete");
		completeButton.getStylesheets().add(ResourceLoader.getCSSFile("/styles/buttons/success.css"));
		completeButton.setFont(Font.font(14));
		completeButton.setOnAction(event -> completeButtonAction());

		// Button to dismiss the order
		Button dismissButton = new Button("Cancel");
		dismissButton.getStylesheets().add(ResourceLoader.getCSSFile("/styles/buttons/danger.css"));
		dismissButton.setFont(Font.font(14));
		dismissButton.setOnAction(event -> dismissButtonAction());

		bottom.getChildren().addAll(completeButton, dismissButton);
		return bottom;
	}

	private void completeButtonAction() {
		timeline.stop();
		removeOrder(this);
	}

	private void dismissButtonAction() {
		order.dismissOrder();
		completeButtonAction();
	}

	private void startOrderTimeCounter() {
		timeline = new Timeline(
			new KeyFrame(javafx.util.Duration.seconds(1), event -> {
				LocalDateTime dt = order.getOrderDateTime();
				Duration duration = Duration.between(dt, LocalDateTime.now());

				// Update the time display
				timeText.setText(formatTime(dt));

				// Update the timeColor every minute
				if (duration.toSeconds() % 60 == 0) {
					timeColor = OrderTimeColor.determineTimeColor(duration);
					updateColorStyle();
				}
			})
		);

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	private void updateColorStyle() {
		setStyle(String.format("-fx-background-color: %s; -fx-border-color: %s; -fx-border-width: .1em;",
			timeColor.getBackgroundColorString(), timeColor.getTextColorString()));

		top.setStyle(String.format("-fx-background-color: %s;", timeColor.getDarkAccentColorString()));
	}

	private static String formatTime(LocalDateTime orderDateTime) {
		Duration duration = Duration.between(orderDateTime, LocalDateTime.now());
		return String.format("%02d:%02d", (int) duration.toMinutes(), duration.toSecondsPart());
	}
}
