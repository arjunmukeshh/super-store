package Inventory;
import java.util.*;

import Inventory.Product.Product;

import java.io.*;

public class Inventory {
    /*
    public void makeArray():                                                        Makes the array
    public void displayInventory():                                                 Displays the current inventory
    public Product getProduct(int id):                                              Gets the product with the given id
    public int removeStock(int id, int number):                                     Removes the product with the given id
    public void addStock(String productName, int id, int number, double price):     Adds the stock the inventory
    public void saveArray(String productsFileName):                                 Saves the current inventory to the file
    public void writeToFile(String filename, Product product):                      Writes the product details to the file                       
    */ 

    String productsFileName;
    String restockFilename;
    ArrayList<Product> products;


    public Inventory(String productsFileName, String restockFilename) {
        this.productsFileName = productsFileName;
        this.restockFilename = restockFilename;
        products = new ArrayList<Product>();
        makeArray(productsFileName);
    }

    // makes the array
    public void makeArray(String filename) {
        try{
            File file = new File(filename);
            Scanner parser = new Scanner(file);
            while(parser.hasNextLine()) {
                String productLine = parser.nextLine();
                if(!productLine.matches(".*,[0-9]*,[0-9]*,[0-9]*.[0-9]*")) {
                    continue;
                }
                String[] tokens = productLine.split(",");
                String name = tokens[0].strip();
                int id = Integer.parseInt(tokens[1].strip());
                int number = Integer.parseInt(tokens[2].strip());
                double price = Double.parseDouble(tokens[3].strip());
                addStock(name, id, number, price);
            }
            parser.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    // Displays the inventory
    public void displayInventory() {
        for(int i = 0; i < products.size(); i++) {
            System.out.println(products.get(i));
        }
    }

    // Returns the product with the name
    public Product getProduct(int id) {
        for(int i = 0; i < products.size(); i++) {
            if(products.get(i).getId() == id) {
                return products.get(i);
            }
        }
        return null;
    }

    // removes product from the inventory and returns the number of items deleted
    public int removeStock(int id, int number) {
        Product product = getProduct(id);
        if(product != null) {
            int index = products.indexOf(product);
            int count = product.getNumber() - number;
            if(count > 0) {
                products.get(index).setNumber(count);
                return number;
            } else {
                int c = product.getNumber();
                writeToFile(restockFilename, product);
                products.remove(index);
                return c;
            }            
        }
        return 0;
    }

    // Adds items to the inventory
    public void addStock(String productName, int id, int number, double price) {
        Product product = getProduct(id);
        if(getProduct(id) == null) {
            product = new Product(productName, id, number, price);
            products.add(product);               
        } else {
            int index = products.indexOf(product);
            products.get(index).setNumber(products.get(index).getNumber()+number);;
        }
    }

    // Saves the current inventory to the file
    public void saveArray(String productsFileName) {
        try {
            FileWriter writer = new FileWriter(new File(productsFileName));
            writer.close();
            for(int i = 0; i < products.size(); i++) {
                writeToFile(productsFileName, products.get(i));
            }
            //writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("File permission denied");
        }
    }

    // writes to a file
    public void writeToFile(String filename, Product product) {
        try {
        FileWriter writer = new FileWriter(new File(filename), true); // append mode
        String toFile = String.format("%s,%d,%d,%f\n", product.getName(), product.getId(), product.getNumber(), product.getPrice());
        writer.write(toFile);
        writer.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Can't write into file.");
        }
    }
}
