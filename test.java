import java.util.HashMap;


class Employee{
    private double hour;
    private double hourlyWage;
    int id;
    String name;
    String aadhaar;
    int age;
    public Employee(double hour, double hourlyWage, int id, String aNo, String name, int age) {
        this.hour = hour;
        this.hourlyWage = hourlyWage;
        this.id = id;
        this.age = age;
        this.name = name;
        this.aadhaar = aNo;
    }
    public void display(){
        System.out.println("\t\t EMPLOYEE DETAILS \n");
        System.out.println("Name : "+this.name);
        System.out.println("ID : "+this.id);
        System.out.println("Age : "+ this.age);
        System.out.println("Aadhaar : "+this.aadhaar);
        System.out.println("Hours worked : "+this.hour);
        System.out.println("\n");
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public double getHour() {
        return hour;
    }
}

class Cashier extends Employee{
    public Cashier(double hour, double hourlyWage, int id, String aNo, String name, int age) {
        super(hour, hourlyWage, id, aNo, name, age);
    }
}
class Janitor extends Employee{
    public Janitor(double hour, double hourlyWage, int id, String aNo, String name, int age) {
        super(hour, hourlyWage, id, aNo, name, age);
    }
}
class Manager extends Employee{
    public Manager(double hour, double hourlyWage, int id, String aNo, String name, int age) {
        super(hour, hourlyWage, id, aNo, name, age);
    }
}
class AisleWorkers extends Employee{
    public AisleWorkers(double hour, double hourlyWage, int id, String aNo, String name, int age) {
        super(hour, hourlyWage, id, aNo, name, age);
    }
}


public class test {
    public createNew(){
        sop("\t\t New Employee Details \n");

    }
    public static void main(String[] args){
        //do while here.
      //  Employee e1 = new Employee(2,3,1,"1111","Anvesh",9);
     //   Employee e2 = new Employee(2, 4, 2, "2222","Arjun", 12);
        e1.display();
        e2.display();
    }
}
