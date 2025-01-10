package connectToDb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection {
    private static final String dbName = "product_db";
    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String tableName = "products";
    private static String user;
    private static String password;
    private static HikariDataSource dataSource;

    public DatabaseConnection() {
    }

    public void setUser(String user) {
        this.user = user;
        resetDataSource();
    }

    public void setPassword(String password) {
        this.password = password;
        resetDataSource();
    }

    private static void resetDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url + dbName);
        config.setUsername(user);
        config.setPassword(password);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource não foi inicializado. Configure o usuário e a senha primeiro.");
        }
        return dataSource.getConnection();
    }

    public void createDatabaseAndTable() {
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + dbName;
        String useDatabaseSQL = "USE " + dbName;
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(250) NOT NULL, " +
                "type VARCHAR(250) NOT NULL, " +
                "price DECIMAL(10, 2) NOT NULL," +
                "brand VARCHAR(250) NOT NULL" +
                ")";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(createDatabaseSQL);
            stmt.executeUpdate(useDatabaseSQL);
            stmt.executeUpdate(createTableSQL);

            System.out.println("Banco de dados e tabela criados com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao criar banco de dados e tabela: " + e.getMessage());
        }
    }

    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
