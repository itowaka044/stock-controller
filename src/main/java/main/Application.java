package main;

import connectToDb.DatabaseConnection;
import manager.ProductManager;
import java.util.Scanner;

public class Application {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        DatabaseConnection dbConn = new DatabaseConnection();

        System.out.print("Usuário: ");
        String user = scanner.nextLine();

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        dbConn.setUser(user);
        dbConn.setPassword(password);

        dbConn.createDatabaseAndTable();

        ProductManager manager = new ProductManager();

        while (true) {
            System.out.printf("\nEscolha uma opção:\n" +
                    "1. Adicionar produto\n" +
                    "2. Listar produtos\n" +
                    "3. Atualizar produto\n" +
                    "4. Excluir produto\n" +
                    "5. Filtrar produtos\n" +
                    "6. Sair\n");
            System.out.print("Opção: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Nome: ");
                    String productName = scanner.nextLine();

                    System.out.print("Tipo: ");
                    String productType = scanner.nextLine();

                    System.out.print("Preço: ");
                    double productPrice = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Marca: ");
                    String productBrand = scanner.nextLine();

                    manager.addProduct(productName, productType, productPrice, productBrand);
                    break;

                case 2:
                    manager.listProducts();
                    break;

                case 3:
                    System.out.print("ID do produto para atualizar: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Novo nome: ");
                    String newName = scanner.nextLine();

                    System.out.print("Novo tipo: ");
                    String newType = scanner.nextLine();

                    System.out.print("Novo preço: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine();

                    manager.updateProduct(updateId, newName, newPrice, newType);
                    break;

                case 4:
                    System.out.print("ID do produto para excluir: ");
                    int deleteId = scanner.nextInt();
                    manager.deleteProduct(deleteId);
                    break;

                case 5:
                    System.out.print("Digite o tipo de produto para filtrar (ex: 'alimentos e bebidas'): ");
                    String filterType = scanner.nextLine();
                    String filterQuery = "SELECT * FROM products WHERE type = '" + filterType + "'";
                    manager.listFilteredProducts(filterQuery);
                    break;

                case 6:
                    System.out.println("Finalizado.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
