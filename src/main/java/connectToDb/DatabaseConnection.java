package connectToDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String dbName = "product_db";
    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String tableName = "products";

    private static Connection connection = null;

    public DatabaseConnection() {
    }

    public Connection createDatabaseAndTable(String user, String password) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            System.out.println("banco de dados '" + dbName + "' verificado/criado.");
            stmt.close();

            conn = DriverManager.getConnection(url+dbName, user, password);
            stmt = conn.createStatement();

            String sql ="CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(250) NOT NULL, " +
                        "type VARCHAR(250) NOT NULL, " +
                        "price DECIMAL(10, 2) NOT NULL," +
                        "brand VARCHAR(250) NOT NULL" +
                        ")";
            stmt.executeUpdate(sql);
            System.out.println("tabela '" + tableName + "' verificada/criada.");
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Falha ao criar o banco de dados: " + e.getMessage());
            return null;
        }

        return conn;
    }

    public static Connection getConnection(String user, String password) {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Conexão com o banco de dados estabelecida.");
            } catch (SQLException e) {
                System.err.println("Falha ao conectar ao banco de dados: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexão com o banco de dados fechada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}
