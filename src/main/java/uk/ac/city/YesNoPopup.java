package uk.ac.city;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import uk.ac.city.resource.ResourceLoader;

import java.util.concurrent.atomic.AtomicBoolean;


public class YesNoPopup {
	public static boolean showYesNoPopup(String prompt) {
		AtomicBoolean result = new AtomicBoolean(false);
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.initStyle(StageStyle.TRANSPARENT);
		popupStage.setResizable(false);

		Label label = new Label(prompt);
		label.setFont(new Font(22));
		label.setTextFill(Color.WHITE);

		Button yesButton = new Button("Yes");
		yesButton.getStylesheets().add(ResourceLoader.getCSSFile("/styles/buttons/success.css"));
		yesButton.setFont(new Font(14));
		yesButton.setOnAction(e -> {
			result.set(true);
			popupStage.close();
		});

		Button noButton = new Button("No");
		noButton.getStylesheets().add(ResourceLoader.getCSSFile("/styles/buttons/danger.css"));
		noButton.setFont(new Font(14));
		noButton.setOnAction(e -> {
			result.set(false);
			popupStage.close();
		});

		yesButton.setMinWidth(80);
		noButton.setMinWidth(80);

		HBox buttonBox = new HBox(10, yesButton, noButton);
		buttonBox.setAlignment(Pos.CENTER);

		VBox layout = new VBox(20, label, buttonBox);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20));
		layout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");

		Scene scene = new Scene(layout);
		scene.setFill(Color.TRANSPARENT);
		popupStage.setScene(scene);
		popupStage.showAndWait();

		return result.get();
	}
}
