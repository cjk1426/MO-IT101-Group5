package com.mycompany.cp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;



public class EmployeeInformation {

    public static void main(String[] args) {
        String currentPath = System.getProperty("user.dir");
        String csvFile = currentPath + File.separator + "resources\\EmployeeDetails.csv"; // Build the file path
        String line;
        String delimiter = "\\|"; // Comma delimiter used in the CSV file

       // C:\Users\cj\Documents\NetBeansProjects\mavenproject1\CP1\CP1\resources\EmployeeDetails.csv
          System.out.println(currentPath);
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Employee Number: ");
            String employeeNumber = scanner.nextLine();

            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(delimiter);

                // Check if the Employee # matches
                if (details[0].equals(employeeNumber)) {
                    found = true;

                    // Print Employee Information
                    System.out.println("Employee Number: " + details[0]);
                    System.out.println("Employee Name: " + details[2] + " " + details[1]); // First Name + Last Name
                    System.out.println("Birthday: " + details[3]);
                    System.out.println("Rate: " + details[18]);
                    break;
                }
            }

            if (!found) {
                System.out.println("Employee not found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        
        
    }
}