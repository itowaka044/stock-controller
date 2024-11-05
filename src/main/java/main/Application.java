package main;

import manager.ProductManager;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        ProductManager manager = new ProductManager();
        Scanner scanner = new Scanner(System.in);

        manager.createDatabaseAndTable();

        while (true) {
            System.out.printf("\nescolha uma opção:\n" +
                    "1. adicionar produto\n" +
                    "2. listar produtos\n" +
                    "3. atualizar produto\n" +
                    "4. excluir produto\n" +
                    "5. ordenar por preço (ascendente)\n"+
                    "6. ordenar por preço (ascendente)\n" +
                    "7. filtrar\n" +
                    "8. sair\n");
            System.out.println("opção: ");
            int option = scanner.nextInt();

            scanner.nextLine();

            if (option > 8) {
                System.out.println("valor inválido");

                break;
            }

            switch (option) {
                case 1:
                    System.out.print("nome do produto: ");
                    String name = scanner.nextLine();

                    System.out.println("tipo de produto:\n1. bebida\n2. alimento");
                    int typeDecision = scanner.nextInt();

                    String type = "vazio";

                    if (typeDecision == 1 || typeDecision == 2) {
                        switch (typeDecision) {
                            case 1:
                                type = "bebida";

                                break;

                            case 2:
                                type = "alimento";

                                break;

                            default:

                                break;
                        }
                    } else {
                        System.out.println("tipo invalido");

                        break;
                    }

                    System.out.print("preço do produto: ");
                    double price = scanner.nextDouble();

                    manager.addProduct(name, type, price);

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

                    System.out.println("tipo do produto: ");
                    System.out.println(" 1. bebida\n 2. alimento \n");
                    int typeInCase = scanner.nextInt();

                    String newType = null;

                    if(typeInCase == 1){
                        newType = "bebida";
                    } else if(typeInCase == 2){
                        newType = "alimento";
                    } else{
                        System.out.println("tipo inválido");
                        break;
                    }

                    System.out.print("novo preço: ");
                    double newPrice = scanner.nextDouble();

                    manager.updateProduct(updateId, newName, newPrice, newType);

                    break;

                case 4:
                    System.out.print("ID do produto para excluir: ");
                    int deleteId = scanner.nextInt();

                    manager.deleteProduct(deleteId);
                    break;

                case 5:
                    manager.orderAsc();

                    break;

                case 6:
                    manager.orderDesc();

                    break;

                case 7:

                    System.out.println(" 1. bebida\n 2. alimento \n");
                    int filterDecision = scanner.nextInt();

                    if (filterDecision == 1) {
                        sql = "SELECT * FROM products WHERE type= 'bebida'";
                    } else if (filterDecision == 2) {
                        sql = "SELECT * FROM products WHERE type= 'alimento'";
                    } else {
                        break;
                    }

                    manager.filterProducts(sql);

                    break;

                case 8:
                    System.out.println("finalizado.");

                    scanner.close();

                    return;

                default:
                    System.out.println("opção inválida.");
            }
        }
        scanner.close();
    }
}
