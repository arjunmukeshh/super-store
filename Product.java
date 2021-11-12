import java.util.Date;

public class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Gets the name of the product
    public String getName() {
        return name;
    }

    // Gets the price of the product
    public double getPrice() {
        return price;
    }

    // Sets the name of the product
    public void setName(String name) {
        this.name = name;
    }

    // Sets the price of the product
    public void setPrice(double price) {
        this.price = price;
    }
}

class Produce extends Product{
    Date expiry;
    public Produce(String name, double price, Date expiry) {
        super(name, price);
        
    }
}
