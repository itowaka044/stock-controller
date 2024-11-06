package connectToDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_NAME = "product_db";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;

    private static Connection connection;

    public static Connection getConnection(String user, String password) {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, user, password);
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
