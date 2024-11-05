package manager;

import java.sql.*;

import static connectToDb.DatabaseConnection.*;

public class ProductManager {

    public static void createDatabaseAndTable() {
        Connection conn = null;
        Statement stmt = null;
        String DB_NAME = "product_db";
        String TABLE_NAME = "products";

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", USER, PASSWORD);
            stmt = conn.createStatement();

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);

            conn.close();

            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "type VARCHAR(100) NOT NULL, " +
                    "price DECIMAL(10, 2) NOT NULL" +
                    ")";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addProduct(String name, String type, double price) {
        String sql = "INSERT INTO products (name, type, price) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setDouble(3, price);

            pstmt.executeUpdate();

            System.out.println("\nproduto adicionado.\n");

        } catch (SQLException e) {
            System.out.println("\nerro ao adicionar produto: " + e.getMessage());
        }
    }

    public void listProducts(String sql) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) {
                System.out.println("\nnenhum produto encontrado.");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", nome: " +
                            rs.getString("name") + ", preço: R$" + rs.getDouble("price"));
                }
            }

        } catch (SQLException e) {
            System.out.println("\nerro ao listar produtos: " + e.getMessage());
        }
    }

    public void updateProduct(int id, String name, double price, String type) {
        String sql = "UPDATE products SET name = ?, price = ?, type = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setString(3, type);
            pstmt.setInt(4, id);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nproduto atualizado.");
            } else {
                System.out.println("\nproduto não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("\nerro ao atualizar produto: " + e.getMessage());
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nproduto removido.");
            } else {
                System.out.println("\nproduto não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("\nerro ao remover produto: " + e.getMessage());
        }
    }

    public void orderAsc() {
        String sql = "SELECT * FROM products ORDER BY price ASC";
        listProducts(sql);
    }

    public void orderDesc() {
        String sql = "SELECT * FROM products ORDER BY price DESC";
        listProducts(sql);
    }

    public void filterProducts(String sql) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) {
                System.out.println("\nnenhum produto encontrado.");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", nome: " +
                            rs.getString("name") + ", preço: R$" + rs.getDouble("price"));
                }
            }

        } catch (SQLException e) {
            System.out.println("\nerro ao listar produtos: " + e.getMessage());
        }
    }
}
