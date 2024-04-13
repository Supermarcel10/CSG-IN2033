package uk.ac.city;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.ac.city.database.Database;
import uk.ac.city.resource.PredefinedResources;
import uk.ac.city.resource.ResourceLoader;

import java.util.LinkedHashMap;


public class Main extends Application {
	private static final LinkedHashMap<String, VBox> tabs = new LinkedHashMap<>();
	static {
		tabs.put("Orders", new OrdersTab());
		tabs.put("Menu", new MenuTab());
		tabs.put("Stock", new StockTab());
	}

	@Override
	public void start(Stage primaryStage) {
		// Setup
		Image logo = ResourceLoader.getImageResource(PredefinedResources.LOGO);
		primaryStage.getIcons().add(logo);

	    BorderPane root = new BorderPane();

		Scene scene = new Scene(root, 1920, 1080); // TODO: Make this use relative sizing
		primaryStage.setScene(scene);
		primaryStage.setTitle("Main Menu");
		primaryStage.show();

		// Top Bar
		ImageView logoView = new ImageView(logo);
		int logoSize = (int) (Math.min(scene.getWidth(), scene.getHeight()) / 10);
		logoView.setFitHeight(logoSize);
		logoView.setFitWidth(logoSize);

		VBox imageBox = new VBox();
		imageBox.setAlignment(Pos.TOP_CENTER);
		imageBox.getChildren().add(logoView);
		imageBox.setBackground(new Background(new BackgroundFill(Color.web("#2b3336"), null, null)));
		root.setTop(imageBox);

		// Tab Panes
	    TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		root.setCenter(tabPane);

	    tabPane.getTabs().addAll(
		    tabs.keySet().stream()
			    .map(key -> new Tab(key, tabs.get(key)))
			    .toList()
	    );
	}

	public static void main(String[] args) {
		Database.initiateDB();
		launch();
	}
}
