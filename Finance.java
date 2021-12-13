import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import Inventory.Product.Product;

public class Finance{
    double bankBalance;
    double profit = 0;
    File Balance;
    Scanner parser;
    String balanceFilename;
    //tempBalance = bankBalance;
    public Finance(String balanceFilename){
        this.balanceFilename = balanceFilename;
        try{
            parser = new Scanner(new File(balanceFilename));
            bankBalance = parser.nextDouble();
            this.profit = bankBalance;
            //System.out.println(bankBalance);
        }catch(FileNotFoundException e){
            System.out.println("File not Found");
        }
    }

    public void saveBalance() {
        try {
            FileWriter writer = new FileWriter(new File(this.balanceFilename));
            writer.write(this.bankBalance+" ");
            writer.close();
        } catch (IOException e) {
            System.out.println("File cannot be written into!");
        }
    }

    /*void payEmployees(Employee[] employees){
        for (int i=0;i<employees.length;i++){
            bankBalance-=employees[i].getWage();
        }
    }*/

    //Add balance from item purchases
    void purchaseItems(ArrayList<Product> shoppingList){
        for (int i=0;i<shoppingList.size();i++){
            bankBalance+=shoppingList.get(i).getPrice()*shoppingList.get(i).getNumber();
        }
    }

    double purchaseItem(Product product, int number) {
        bankBalance += product.getPrice() * number;
        return product.getPrice() * number;
    }

    //Restock items and remove amount from balance
    void purchaseStock(ArrayList<Product> newStock){
        for (int i=0;i<newStock.size();i++){
            bankBalance-=newStock.get(i).getPrice()*newStock.get(i).getRestockAmt();
        }
    }

    public double calculateProfit() {
        return this.profit-bankBalance;
    }
}
