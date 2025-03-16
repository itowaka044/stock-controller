package manager;

import repositories.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductManager {

    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String user = "root";
    private static final String password = "1234";

    public void addProduct(String name, String type, double price, String brand) {
        String sql = "INSERT INTO products (name, type, price, brand) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setDouble(3, price);
            pstmt.setString(4, brand);

            pstmt.executeUpdate();
            System.out.println("\nProduto adicionado com sucesso.");

        } catch (SQLException e) {
            System.out.println("\nErro ao adicionar produto: " + e.getMessage());
        }
    }

    public void listProducts() {
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) {
                System.out.println("\nNenhum produto encontrado.");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Nome: " +
                            rs.getString("name") + ", Preço: R$" + rs.getDouble("price"));
                }
            }

        } catch (SQLException e) {
            System.out.println("\nErro ao listar produtos: " + e.getMessage());
        }
    }

    public void updateProduct(int id, String name, double price, String type) {
        String sql = "UPDATE products SET name = ?, price = ?, type = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setString(3, type);
            pstmt.setInt(4, id);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nProduto atualizado com sucesso.");
            } else {
                System.out.println("\nProduto não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("\nErro ao atualizar produto: " + e.getMessage());
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nProduto removido com sucesso.");
            } else {
                System.out.println("\nProduto não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("\nErro ao remover produto: " + e.getMessage());
        }
    }

    public void listFilteredProducts(String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) {
                System.out.println("\nNenhum produto encontrado.");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Nome: " +
                            rs.getString("name") + ", Preço: R$" + rs.getDouble("price") + ", Marca: " + rs.getString("brand"));
                }
            }

        } catch (SQLException e) {
            System.out.println("\nErro ao listar produtos filtrados: " + e.getMessage());
        }
    }
}
