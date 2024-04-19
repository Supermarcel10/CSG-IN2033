package uk.ac.city.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;


/**
 * Database initialiser class to handle the connection to the database
 */
public class Database {
	private static HikariDataSource ds;

	/**
	 * Initiates the database connection.
	 * @throws RuntimeException if the database connection fails.
	 */
	public static void initiateDB() throws RuntimeException {
		HikariConfig config = new HikariConfig();

		String dbUrl = Objects.requireNonNull(Database.class.getResource("/temporary/sqlite.db")).getPath();
		config.setJdbcUrl("jdbc:sqlite:" + dbUrl);
		config.setDriverClassName("org.sqlite.JDBC");

		// TODO: To be changed for when moving to the University Database
		// config.setJdbcUrl("");
		// config.setUsername("");
		// config.setPassword("");
		// config.setMaximumPoolSize(10);
		// config.setAutoCommit(false);
		// config.addDataSourceProperty("cachePrepStmts", "true");
		// config.addDataSourceProperty("prepStmtCacheSize", "250");
		// config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		ds = new HikariDataSource(config);
	}

	/**
	 * Returns a connection to the database.
	 * @return a connection to the database.
	 * @throws SQLException if the connection to the database fails.
	 */
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}
