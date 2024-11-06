package main;

import java.sql.Connection;
import java.util.Scanner;

import connectToDb.DatabaseConnection;
import manager.ProductManager;

public class Application {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        DatabaseConnection dbConn = new DatabaseConnection();

        System.out.print("user do banco de dados: ");
        String user = scanner.nextLine();

        System.out.print("senha do banco de dados: ");
        String password = scanner.nextLine();

        Connection conn = dbConn.createDatabaseAndTable(user, password);

        if(conn == null){
            System.out.println("nao foi possivel estabelecer conexao");
            scanner.close();
            return;
        }

        System.out.println("conexao estabelecida com sucesso!");

        ProductManager manager = new ProductManager(user, password);

        while (true) {
            int typeDecision;
            String productType;

            System.out.printf("\nescolha uma opção:\n" +
                    "1. adicionar produto\n" +
                    "2. listar produtos\n" +
                    "3. atualizar produto\n" +
                    "4. excluir produto\n" +
                    "5. ordenar por preço (ascendente)\n"+
                    "6. ordenar por preço (descendente)\n" +
                    "7. filtrar\n" +
                    "8. sair\n");
            System.out.print("opção: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option > 8 || option < 1) {
                System.out.println("valor inválido");
                continue;
            }

            switch (option) {
                case 1:
                    System.out.print("nome: ");
                    String productName = scanner.nextLine();
                    productName = productName.toLowerCase();

                    System.out.println("tipo:\n" +
                            "1. alimentos e bebidas\n" +
                            "2. produtos de higiene\n" +
                            "3. produtos de limpeza\n" +
                            "4. cosmeticos e cuidados pessoais\n" +
                            "5. eletro\n" +
                            "6. papelaria e utensilios\n" +
                            "7. roupas e acessorios\n" +
                            "8. pet shop ");
                    typeDecision = scanner.nextInt();
                    scanner.nextLine();

                    productType = null;

                    if (typeDecision >= 1 && typeDecision <= 8) {
                        switch (typeDecision) {
                            case 1: productType = "alimentos e bebidas"; break;
                            case 2: productType = "produtos de higiene"; break;
                            case 3: productType = "produtos de limpeza"; break;
                            case 4: productType = "cosmeticos e cuidados pessoais"; break;
                            case 5: productType = "eletro"; break;
                            case 6: productType = "papelaria e utensilios"; break;
                            case 7: productType = "roupas e acessorios"; break;
                            case 8: productType = "pet shop"; break;
                        }
                    } else {
                        System.out.println("tipo invalido");
                        break;
                    }

                    System.out.print("preço: ");
                    double productPrice = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("marca: ");
                    String productBrand = scanner.nextLine();
                    productBrand = productBrand.toLowerCase();

                    manager.addProduct(conn, productName, productType, productPrice, productBrand);
                    break;

                case 2:
                    String sql = "SELECT * FROM products";
                    manager.listProducts(sql);
                    break;

                case 3:
                    System.out.print("ID do produto para atualizar: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("novo nome: ");
                    String newName = scanner.nextLine();

                    System.out.println("tipo:\n" +
                            "1. alimentos e bebidas\n" +
                            "2. produtos de higiene\n" +
                            "3. produtos de limpeza\n" +
                            "4. cosmeticos e cuidados pessoais\n" +
                            "5. eletro\n" +
                            "6. papelaria e utensilios\n" +
                            "7. roupas e acessorios\n" +
                            "8. pet shop ");
                    typeDecision = scanner.nextInt();
                    scanner.nextLine();

                    productType = null;

                    if (typeDecision >= 1 && typeDecision <= 8) {
                        switch (typeDecision) {
                            case 1: productType = "alimentos e bebidas"; break;
                            case 2: productType = "produtos de higiene"; break;
                            case 3: productType = "produtos de limpeza"; break;
                            case 4: productType = "cosmeticos e cuidados pessoais"; break;
                            case 5: productType = "eletro"; break;
                            case 6: productType = "papelaria e utensilios"; break;
                            case 7: productType = "roupas e acessorios"; break;
                            case 8: productType = "pet shop"; break;
                        }
                    } else {
                        System.out.println("tipo invalido");
                        break;
                    }

                    System.out.print("novo preço: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine();

                    manager.updateProduct(updateId, newName, newPrice, productType);
                    break;

                case 4:
                    System.out.print("ID do produto para excluir: ");
                    int deleteId = scanner.nextInt();
                    manager.deleteProduct(deleteId);
                    break;

                case 5: manager.orderAsc(); break;

                case 6: manager.orderDesc(); break;

                case 7:
                    System.out.println("tipo:\n" +
                            "1. alimentos e bebidas\n" +
                            "2. produtos de higiene\n" +
                            "3. produtos de limpeza\n" +
                            "4. cosmeticos e cuidados pessoais\n" +
                            "5. eletro\n" +
                            "6. papelaria e utensilios\n" +
                            "7. roupas e acessorios\n" +
                            "8. pet shop ");
                    typeDecision = scanner.nextInt();
                    scanner.nextLine();

                    productType = null;

                    if (typeDecision >= 1 && typeDecision <= 8) {
                        switch (typeDecision) {
                            case 1: productType = "SELECT * FROM products WHERE type= 'alimentos e bebidas'"; break;
                            case 2: productType = "SELECT * FROM products WHERE type= 'produtos de higiene'"; break;
                            case 3: productType = "SELECT * FROM products WHERE type= 'produtos de limpeza'"; break;
                            case 4: productType = "SELECT * FROM products WHERE type= 'cosmeticos e cuidados pessoais'"; break;
                            case 5: productType = "SELECT * FROM products WHERE type= 'eletro'"; break;
                            case 6: productType = "SELECT * FROM products WHERE type= 'papelaria e utensilios'"; break;
                            case 7: productType = "SELECT * FROM products WHERE type= 'roupas e acessorio's'"; break;
                            case 8: productType = "SELECT * FROM products WHERE type= 'pet shop'"; break;
                        }
                    } else {
                        System.out.println("tipo invalido");
                        break;
                    }

                    manager.filterProducts(productType);
                    break;

                case 8:
                    System.out.println("finalizado.");
                    scanner.close();
                    DatabaseConnection.closeConnection();
                    return;

                default:
                    System.out.println("opção inválida.");
            }
        }
    }
}
