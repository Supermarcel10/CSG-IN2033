package uk.ac.city.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;


public class Database {
	private static HikariDataSource ds;

	public static void initiateDB() throws RuntimeException {
		HikariConfig config = new HikariConfig();

		String dbUrl = Objects.requireNonNull(Database.class.getResource("/temporary/sqlite.db")).getPath();
		config.setJdbcUrl("jdbc:sqlite:" + dbUrl);
		config.setDriverClassName("org.sqlite.JDBC");

		// For when University Database gets fixed
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

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}
