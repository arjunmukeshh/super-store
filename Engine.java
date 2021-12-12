import java.util.Scanner;
import Inventory.Inventory;
import Inventory.Product.Product;

public class Engine {
    public static void main(String[] args) {
        Inventory inventory = new Inventory("products.csv", "restock.csv");
        Finance finance = new Finance("balance.txt");

        Scanner parser = new Scanner(System.in);
        boolean end = false;
        while(!end) {
            String line = parser.nextLine();
            if(line.strip().equalsIgnoreCase("products")) {
                inventory.displayInventory();
            } else if(line.strip().equalsIgnoreCase("purchase")) {
                System.out.println("Enter item: ");
                String item = parser.nextLine();
                int purchase = purchaseUI(inventory, finance, parser, item);
                if(purchase > 0) {
                    System.out.println("Successfully purchased "+purchase+" items.");
                }
            } else if(line.strip().equalsIgnoreCase("add to cart") || line.strip().equalsIgnoreCase("cart")) { 
                String productInCart = ""; // 
                System.out.println("Enter items: ");
                boolean done = false;
                while(!done) {
                    productInCart = parser.nextLine().strip();
                    if(productInCart.strip().equalsIgnoreCase("buy")) {
                        done = true;
                    } else {
                        purchaseUI(inventory, finance, parser, productInCart);
                    }
                }
            } else if(line.strip().equalsIgnoreCase("end")) {
                end = true;
            }

        }
        /*Product product = inventory.getProduct(205001003);
        System.out.println(inventory.removeStock(205001003, 1));
        
        if(product != null) { // upgrade to exception
            System.out.println(product);
        } else {
            System.out.println("product not found");
        }
        */
        inventory.saveArray("products.csv");
    }

  
    public static int purchaseUI(Inventory inventory, Finance finance, Scanner parser, String item) { // remove
        if(item.matches(".* [0-9]*")) {
            String[] tokens = item.strip().toLowerCase().split(" ");
            Product product = inventory.getProduct(tokens[0]);
            if(product!=null) {
                return purchaseProduct(inventory, finance, product, Integer.parseInt(tokens[1].strip()));
            }
        }
        return -1;
    }

    public static int purchaseProduct(Inventory inventory, Finance finance, Product product, int number) { // remove
        int available = inventory.removeStock(product.getName(), number);
        finance.purchaseItem(product, available);
        return available;
    }
}
