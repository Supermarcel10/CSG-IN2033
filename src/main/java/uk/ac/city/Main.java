package uk.ac.city;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
	    BorderPane root = new BorderPane();

	    TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

	    tabPane.getTabs().addAll(
		    tabs.keySet().stream()
			    .map(key -> new Tab(key, tabs.get(key)))
			    .toList()
	    );

		root.setCenter(tabPane);

	    Scene scene = new Scene(root, 600, 400);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Main Menu");
	    primaryStage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
