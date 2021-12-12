import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.*;
import Inventory.Product.Product;

public class Finance{
    double bankBalance;
    File Balance;
    Scanner parser;
    double tempBalance;
    //tempBalance = bankBalance;
    public Finance(String balanceFilename){
        try{
            parser = new Scanner(new File(balanceFilename));
            bankBalance = parser.nextDouble();
            System.out.println(bankBalance);
        }catch(FileNotFoundException e){
            System.out.println("File not Found");
        }
    }

    void payEmployees(Employee[] employees){
        for (int i=0;i<employees.length;i++){
            bankBalance-=employees[i].getWage();
        }
    }

    //Add balance from item purchases
    void purchaseItems(ArrayList<Product> shoppingList){
        for (int i=0;i<shoppingList.size();i++){
            bankBalance+=shoppingList.get(i).getPrice()*shoppingList.get(i).getNumber();
        }
    }
    
    void purchaseItem(Product product, int number) {
        bankBalance += product.getPrice() * number;
    }

    //Compare before and after for Profit or Loss
    void profitLoss(){
        double difference = bankBalance - tempBalance;
        if (difference<0)       System.out.printf("Loss of %d"+difference);
        else if (difference>0)  System.out.printf("Profit of %d"+difference);
        else                    System.out.printf("No Profit");
    }

    //Restock items and remove amount from balance
    void purchaseStock(ArrayList<Product> newStock){
        for (int i=0;i<newStock.size();i++){
            bankBalance-=newStock.get(i).getPrice()*newStock.get(i).getNumber();
        }
    }
}
