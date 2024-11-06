package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static connectToDb.DatabaseConnection.getConnection;

public class ProductManager {

    private final String user;
    private final String password;

    public ProductManager(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void addProduct(Connection conn, String name, String type, double price, String brand) {
        String sql = "INSERT INTO products (name, type, price, brand) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setDouble(3, price);
            pstmt.setString(4, brand);

            pstmt.executeUpdate();

            System.out.println("\nproduto adicionado.");

        } catch (SQLException e) {
            System.out.println("\nerro ao adicionar produto: " + e.getMessage());
        }
    }

    public void listProducts(String sql) {
        try (Connection conn = getConnection(user, password);
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

        try (Connection conn = getConnection(user, password);
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

        try (Connection conn = getConnection(user, password);
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
        try (Connection conn = getConnection(user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) {
                System.out.println("\nnenhum produto encontrado.");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", nome: " +
                            rs.getString("name") + ", preço: R$" + rs.getDouble("price") + ", marca: " + rs.getString("brand"));
                }
            }

        } catch (SQLException e) {
            System.out.println("\nerro ao listar produtos: " + e.getMessage());
        }
    }
}
