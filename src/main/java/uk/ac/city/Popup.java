package uk.ac.city;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import uk.ac.city.resource.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * A utility class to display popups with different types of messages and buttons.
 */
public class Popup {
	private static final int popupTextSize = 22;
	private static final int buttonFontSize = 14;

	/**
	 * Display a blocking popup with a message and YES/NO buttons.
	 * @param prompt The message to display in the popup.
	 * @return True if the YES button was clicked, false if the NO button was clicked.
	 */
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
		yesButton.setFont(new Font(buttonFontSize));
		yesButton.setOnAction(e -> {
			result.set(true);
			popupStage.close();
		});

		Button noButton = new Button("No");
		noButton.getStylesheets().add(ResourceLoader.getCSSFile("/styles/buttons/danger.css"));
		noButton.setFont(new Font(buttonFontSize));
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

	/**
	 * Display a blocking popup with a message and an OK button.
	 * @param prompt The message to display in the popup.
	 */
	public static void showOkPopup(String prompt) {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.initStyle(StageStyle.TRANSPARENT);
		popupStage.setResizable(false);

		Label label = new Label(prompt);
		label.setFont(new Font(popupTextSize));
		label.setTextFill(Color.WHITE);

		Button okButton = new Button("OK");
		okButton.getStylesheets().add(ResourceLoader.getCSSFile("/styles/buttons/white.css"));
		okButton.setFont(new Font(buttonFontSize));
		okButton.setOnAction(e -> popupStage.close());
		okButton.setMinWidth(80);

		label.setOnKeyPressed(event -> {
			if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
				okButton.fire();
			}
		});

		VBox layout = new VBox(20, label, okButton);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20));
		layout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");

		Scene scene = new Scene(layout);
		scene.setFill(Color.TRANSPARENT);
		popupStage.setScene(scene);
		popupStage.showAndWait();
	}

	/**
	 * Display a blocking popup with a message and an input text field.
	 * @param prompt The message to display in the popup.
	 * @return The text entered by the user in the text field.
	 */
	public static String showTextInputPopup(String prompt) {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.initStyle(StageStyle.TRANSPARENT);
		popupStage.setResizable(false);

		Label label = new Label(prompt);
		label.setFont(new Font(popupTextSize));
		label.setTextFill(Color.WHITE);

		TextField textField = new TextField();
		textField.setPromptText("Enter your input");
		textField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8); -fx-background-radius: 5;");

		Button okButton = new Button("OK");
		okButton.getStylesheets().add(ResourceLoader.getCSSFile("/styles/buttons/white.css"));
		okButton.setFont(new Font(buttonFontSize));
		okButton.setOnAction(e -> popupStage.close());
		okButton.setMinWidth(80);

		textField.setOnKeyPressed(event -> {
			if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
				okButton.fire();
			}
		});

		VBox layout = new VBox(20, label, textField, okButton);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20));
		layout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");

		Scene scene = new Scene(layout);
		scene.setFill(Color.TRANSPARENT);
		popupStage.setScene(scene);
		popupStage.showAndWait();

		return textField.getText();
	}

	/**
	 * Display a blocking popup with a message and a warning icon.
	 * @param title The title of the popup.
	 * @param message The message to display in the popup.
	 */
	public static void showWarningPopup(String title, String message) {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.initStyle(StageStyle.TRANSPARENT);
		popupStage.setResizable(false);

		Label titleLabel = new Label(title);
		titleLabel.setFont(new Font(popupTextSize * 1.2));
		titleLabel.setTextFill(Color.WHITE);

		Label messageLabel = new Label(message);
		messageLabel.setFont(new Font(popupTextSize));
		messageLabel.setTextFill(Color.WHITE);

		SVGPath warningIcon = new SVGPath();
		warningIcon.setContent("M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5m.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2");
		warningIcon.setFill(Color.rgb(255, 165, 0)); // Orangy-yellow color
		warningIcon.setScaleX(1.6);
		warningIcon.setScaleY(1.6);

		HBox iconAndTitleBox = new HBox(10, warningIcon, titleLabel);
		iconAndTitleBox.setAlignment(Pos.CENTER_LEFT);

		Button okButton = new Button("OK");
		okButton.getStylesheets().add(ResourceLoader.getCSSFile("/styles/buttons/white.css"));
		okButton.setFont(new Font(buttonFontSize));
		okButton.setOnAction(e -> popupStage.close());
		okButton.setMinWidth(80);

		VBox layout = new VBox(20, iconAndTitleBox, messageLabel, okButton);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20));
		layout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");

		Scene scene = new Scene(layout);
		scene.setFill(Color.TRANSPARENT);
		popupStage.setScene(scene);
		popupStage.showAndWait();
	}
}
