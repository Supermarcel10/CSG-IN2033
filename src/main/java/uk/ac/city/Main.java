package uk.ac.city;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
		Image logo = ResourceLoader.getImageResource(PredefinedResources.LOGO);
		primaryStage.getIcons().add(logo);

	    BorderPane root = new BorderPane();

		ImageView logoView = new ImageView(logo);
		logoView.setFitHeight(50); // TODO: Make this use relative sizing
		logoView.setFitWidth(50); // TODO: Make this use relative sizing

		VBox imageBox = new VBox();
		imageBox.setAlignment(Pos.TOP_CENTER);
		imageBox.getChildren().add(logoView);
		root.setTop(imageBox);

	    TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

	    tabPane.getTabs().addAll(
		    tabs.keySet().stream()
			    .map(key -> new Tab(key, tabs.get(key)))
			    .toList()
	    );

		root.setCenter(tabPane);

	    Scene scene = new Scene(root, 600, 400); // TODO: Make this use relative sizing
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Main Menu");
	    primaryStage.show();
	}

	public static void main(String[] args) {
		Database.initiateDB();
		launch();
	}
}
