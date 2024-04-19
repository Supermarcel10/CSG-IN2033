package uk.ac.city;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import uk.ac.city.database.Database;
import uk.ac.city.resource.PredefinedResources;
import uk.ac.city.resource.ResourceLoader;
import uk.ac.city.tab.menu.MenuTab;
import uk.ac.city.tab.order.OrdersTab;
import uk.ac.city.tab.stock.StockTab;

import java.io.File;
import java.util.LinkedHashMap;


/**
 * The main class of the application. This class is responsible for creating the main window and setting up the tabs.
 */
public class Main extends Application {
	private static final String version = getProjectVersion();
	private static final Image logo = ResourceLoader.getImageResource(PredefinedResources.LOGO);
	private static final LinkedHashMap<String, Pane> tabs = new LinkedHashMap<>();

	protected static int sizeX = 800; // TODO: Make this dynamic
	protected static int sizeY = 600; // TODO: Make this dynamic

	/**
	 * The entry point of the application.
	 * @param primaryStage The primary stage of the application.
	 */
	@Override
	public void start(Stage primaryStage) {
		Pane topBar = createTopBar();
		TabPane tabPane = createTabPane();

		// Arrange top level components
		BorderPane root = new BorderPane();
		root.setTop(topBar);
		root.setCenter(tabPane);

		// Set the scene and show the stage
		Scene scene = new Scene(root, sizeX, sizeY);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Main Menu");
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(logo);
		primaryStage.show();
	}

	/**
	 * Creates the top bar of the application responsible for displaying the logo and the version of the application.
	 * @return The top bar of the application.
	 */
	private Pane createTopBar() {
		Pane topBar = new Pane();
		topBar.setStyle("-fx-background-color: #2b3336;");

		// Logo
		ImageView logoView = new ImageView(logo);
		int imageSize = Math.min(sizeX, sizeY) / 10;
		logoView.setFitHeight(imageSize);
		logoView.setFitWidth(imageSize);
		logoView.setLayoutX(sizeX / 2d - imageSize / 2d);
		topBar.getChildren().add(logoView);

		// Version label
		Label versionLabel = new Label(String.format("v%s", version));
		versionLabel.setTextFill(Color.WHITE);
		versionLabel.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> versionLabel.setLayoutX(sizeX - newValue.getWidth()));
		topBar.getChildren().add(versionLabel);

		return topBar;
	}

	/**
	 * Creates the tab pane of the application responsible for displaying the different tabs.
	 * @return The tab pane of the application.
	 */
	private TabPane createTabPane() {
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tabPane.getStyleClass().add("tab-pane-custom");

		// TODO: Center the tabs
		// TODO: Change the colors of the background tab ribbon

		tabs.put("Orders", new OrdersTab());
		tabs.put("Menu", new MenuTab());
		tabs.put("Stock", new StockTab());

		tabPane.getTabs().addAll(
			tabs.keySet().stream()
				.map(key -> new Tab(key, tabs.get(key)))
				.toList()
		);

		return tabPane;
	}

	/**
	 * Retrieves the version of the project from the pom.xml file.
	 * @return The version of the project.
	 */
	private static String getProjectVersion() {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File("pom.xml"));
			Element rootElement = document.getRootElement();
			Element versionElement = rootElement.element("version");
			return versionElement.getTextTrim();
		} catch (DocumentException e) {
			return "0.0";
		}
	}

	/**
	 * The entry point of the application.
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		Database.initiateDB();
		launch();
	}
}
