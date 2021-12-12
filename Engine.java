import Inventories.Inventory;
import Inventories.Products.Product;

public class Engine {
    public static void main(String[] args) {
        Inventory inventory = new Inventory("products.csv");
        Product product = inventory.getProduct(205001006);
        System.out.println(inventory.removeStock(205001006, 1));
        
        if(product != null) { // upgrade to exception
            System.out.println(product);
        } else {
            System.out.println("product not found");
        }

        inventory.saveArray("products.csv");
    }
}
