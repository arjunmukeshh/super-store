package emp;

import java.util.ArrayList;

public class EmployeeManagement {
    ArrayList<Employee> employees;

    public EmployeeManagement(){
        employees = Employee.readObjects();
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

}
