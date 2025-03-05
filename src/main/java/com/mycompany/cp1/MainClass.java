


package com.mycompany.cp1;

import java.io.IOException;


public class MainClass {

    public static void main(String[] args) {

             int weekNumber = 29;  // Example week number
        String employeeNumber = "10003";  // Example employee number

        GrossSalaryPerWeek_NEW calculator = new GrossSalaryPerWeek_NEW();
        try {
            calculator.calculateGrossSalary(weekNumber, employeeNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

           
}
