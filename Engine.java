import Inventory.Inventory;
import Inventory.Product.Product;

public class Engine {
    public static void main(String[] args) {
        Inventory inventory = new Inventory("products.csv", "restock.csv");
        Product product = inventory.getProduct(205001003);
        System.out.println(inventory.removeStock(205001003, 1));
        
        if(product != null) { // upgrade to exception
            System.out.println(product);
        } else {
            System.out.println("product not found");
        }

        inventory.saveArray("products.csv");
    }
}
