module bytebake {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.zaxxer.hikari;
	requires java.sql;
	requires org.dom4j;

	opens uk.ac.city to javafx.fxml;
	exports uk.ac.city;
	exports uk.ac.city.database;
	exports uk.ac.city.database.entities;
}
