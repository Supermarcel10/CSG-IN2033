module IN2033 {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.zaxxer.hikari;
	requires java.sql;
	requires org.dom4j;

	opens uk.ac.city to javafx.fxml;
	exports uk.ac.city;
}
