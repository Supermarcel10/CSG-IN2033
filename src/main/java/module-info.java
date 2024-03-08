module com.example.myjava {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.myjava to javafx.fxml;
    exports com.example.myjava;
	exports uk.ac.city;
	opens uk.ac.city to javafx.fxml;
}
