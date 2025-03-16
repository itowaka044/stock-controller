package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String dbName = "product_db";
    private static final String tableName = "products";
    private static final String url = "jdbc:mysql://localhost:3306/" + dbName;
    private static final String user = "root";
    private static final String password = "1234";

    private static Connection connection = null;

    public Connection createDatabaseAndTable() {
        Connection connection1 = null;

        try {
            connection1 = DriverManager.getConnection(url, user, password);
            Statement stmt = connection1.createStatement();

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            System.out.println("banco de dados '" + dbName + "' verificado/criado.");
            stmt.close();

            connection1 = DriverManager.getConnection(url+dbName, user, password);
            stmt = connection1.createStatement();

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

        return connection1;
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Conexao com o banco de dados estabelecida.");
            } catch (SQLException e) {
                System.err.println("Falha ao conectar ao banco de dados: " + e.getMessage());
            }
        }
        return connection;
    }
}
