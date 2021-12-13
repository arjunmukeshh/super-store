import java.util.ArrayList;
import java.util.Scanner;

import Inventory.Inventory;
import Inventory.Product.Product;
import emp.Employee;
import emp.EmployeeManagement;

public class Engine {
    public static void main(String[] args) {
        Inventory inventory = new Inventory("products.csv", "restock.csv");
        ArrayList<Product> restock = inventory.restock();
        inventory.makeArray("products.csv");
        Finance finance = new Finance("balance.txt");
        finance.purchaseStock(restock);

        EmployeeManagement employeeManagement = new EmployeeManagement();
        //System.out.println(finance.bankBalance);
        //System.out.println(finance.bankBalance);
        //Employee.employeemanage();
        //System.out.println(e);
        //System.out.println("Size: "+ e.size());
        //System.out.println(employeeManagement.getEmployees());
        ArrayList<Employee> e = Employee.readObjects();
        

        Scanner parser = new Scanner(System.in);
        boolean end = false;
        while(!end) {
            String line = parser.nextLine();
            if(line.strip().equalsIgnoreCase("products")) {
                inventory.displayInventory();
            
            } else if(line.strip().equalsIgnoreCase("purchase")) {
                System.out.println("Enter item: ");
                String item = parser.nextLine();
                if(item.matches(".* [0-9]*")) {
                    String[] tokens = item.strip().toLowerCase().split(" ");
                    String[] itemName = tokens.clone();
                    itemName[itemName.length - 1] = ""; 
                    Product product = inventory.getProduct(itemName[0]); // join array
                    int purchase = purchaseProduct(inventory, finance, product, Integer.parseInt(tokens[tokens.length-1].strip()));
                    if(purchase > 0) {
                        System.out.println("Successfully purchased "+purchase+" "+product.getName());
                    }
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
        /*Product product = inventory.getProduct(205001003);
        System.out.println(inventory.removeStock(205001003, 1));
        
        if(product != null) { // upgrade to exception
            System.out.println(product);
        } else {
            System.out.println("product not found");
        }
        */
        finance.saveBalance();
        inventory.saveArray("products.csv");
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
        int available = inventory.removeStock(product.getName(), number);
        finance.purchaseItem(product, available);
        return available;
    }
}
