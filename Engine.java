import java.util.ArrayList;
import java.util.Scanner;

import Finance.Finance;
import Inventory.Inventory;
import Inventory.Product.Product;
import emp.Employee;


public class Engine {
    public static void main(String[] args) {
        Inventory inventory = new Inventory("products.csv", "restock.csv");
        Finance finance = new Finance("balance.txt");
        ArrayList<Product> restock = inventory.restock();
        inventory.makeArray("products.csv");
        finance.purchaseStock(restock);
        Scanner parser = new Scanner(System.in);
        boolean end = false;
        String line = "";
        System.out.println("\t\t\tStore Management System\n\n");
        while(!end) {
            line = parser.nextLine();
            if(line.strip().equalsIgnoreCase("products")) {
                inventory.displayInventory();
            
            }
            else if(line.strip().equalsIgnoreCase("manage employees")) {
                System.out.println("\t\t\tEmployee Management System\n\n");
                Employee.employeemanage();
                System.out.println("Employee management system terminated.\n");
            } else if(line.strip().equalsIgnoreCase("employee print")) {
                System.out.println(Employee.readObjects());
                System.out.println();
            }
            else if(line.strip().equalsIgnoreCase("purchase")) {
                System.out.println("Enter the item name and quantity: ");
                String item = parser.nextLine();
                if(item.matches(".* [0-9]*")) {
                    String[] tokens = item.strip().toLowerCase().split(" ");
                    String[] itemName = tokens.clone();
                    itemName[itemName.length - 1] = ""; 
                    Product product = inventory.getProduct(itemName[0]); // join array
                    int purchase = purchaseProduct(inventory, finance, product, Integer.parseInt(tokens[tokens.length-1].strip()));
                    if(purchase > 0) {
                        System.out.println("Successfully purchased "+purchase+" "+product.getName()+"\n\n");
                    }
                } else {
                    System.out.println();
                    continue;
                }
            } else if(line.strip().equalsIgnoreCase("add to cart") || line.strip().equalsIgnoreCase("cart")) { 
                System.out.println("Enter items: ");
                String item = "";
                boolean done = false;
                ArrayList<Product> cart = new ArrayList<Product>();
                while(!done) {
                    item = parser.nextLine();
                    if(item.strip().equalsIgnoreCase("buy")) {
                        done = true;
                    } else if(item.matches(".* [0-9]*")) {
                        String[] tokens = item.strip().toLowerCase().split(" ");
                        Product product = inventory.getProduct(tokens[0]);
                        if(product != null) {
                            Product temp = new Product(product.getName(), product.getId(), Integer.parseInt(tokens[1].strip()), product.getPrice(), 0, 0);
                            cart.add(temp);
                        }
                    }
                }
                System.out.printf("\n\n\nBill of %d items\n", cart.size());
                System.out.println("---------------------------------------------");
                System.out.printf("%-25s|%-5s|%-12s|\n", "Item name", "QTY", "Price");
                System.out.println("---------------------------------------------");
                float total = 0;
                for(int i = 0; i < cart.size(); i++) {
                    int purchase = purchaseProduct(inventory, finance, cart.get(i), cart.get(i).getNumber());
                    double price = purchase*cart.get(i).getPrice();
                    total += price;
                    System.out.printf("%-25s|%5d|%12.2f|\n", cart.get(i).getName(), purchase, price);
                }
                System.out.println("---------------------------------------------");
                System.out.printf("%31s| %11.2f|\n","Total", total);
                System.out.println("---------------------------------------------");
            } else if(line.strip().equalsIgnoreCase("end")) {
                end = true;
            } 
        }
        parser.close();
        finance.saveBalance();
        inventory.saveArray("products.csv");
        double profit = finance.calculateProfit();
        if(profit>0) {
            System.out.println("Today's profit was "+profit+"/-");
        } else {
            System.out.println("Today's loss was "+profit*-1+"/-");
        }
    }

    public String help() {
        String helpMessage = String.format("\t\tCommands\n");
        helpMessage+= String.format("\t\t%20s\n");
        return helpMessage;
    }

    public Product parseAndGetProduct(Inventory inventory, Finance finance, String item) {
        if(item.matches(".* [0-9]*")) {
            String[] tokens = item.strip().toLowerCase().split(" ");
            Product product = inventory.getProduct(tokens[0]);
            return product;
        }
        return null;
    }

    
    public static int purchaseProduct(Inventory inventory, Finance finance, Product product, int number) {
        if(product != null) {
            int available = inventory.removeStock(product.getName(), number);
            finance.sellItem(product, available);
            return available;
        }
        return 0;
    }
}
