package com.mycompany.cp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * GrossSalaryPerWeek class reads a CSV file containing employee attendance records,
 * extracts the week of the year from the date, and calculates the total hours worked 
 * for each employee within a user-selected week.
 */
public class GrossSalaryPerWeek {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String currentPath = System.getProperty("user.dir");
        String csvFile = currentPath + File.separator + "resources" + File.separator + "EmployeeDataAttendanceRecord.csv"; // Build the file path
        String line;
        String csvSplitBy = "\\|";
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        
        // Initialize BufferedReader
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        
        // Map to store total hours worked for each employee
        Map<String, Double> employeeHours = new HashMap<>();
        
        // Skip the header row
        br.readLine();

        String weekselect = "";

        // Read and process each line of the CSV file to get available weeks
        while ((line = br.readLine()) != null) {
            String[] details = line.split(csvSplitBy);
            String dateStr = details[3];
            LocalDate date = LocalDate.parse(dateStr, dateFormatter);
            int weekOfYear = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            String weeknum = String.valueOf(weekOfYear);

            if (!weekselect.contains(weeknum)) {
                weekselect = weekselect.isEmpty() ? weeknum : weekselect + ", " + weeknum;
            }
        }

        // Display available weeks
        System.out.println("Select from week: " + weekselect);
        
        // Get the desired week from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the week: ");
        int weekNumber = scanner.nextInt();
         scanner.nextLine(); 
       // scanner.close();
        
        
        // Reset BufferedReader to the start of the file
        br.close();
        br = new BufferedReader(new FileReader(csvFile));
        br.readLine(); // Skip the header row again

        String employeeIDs = "";

        // Get the available employee IDs within the chosen week
        while ((line = br.readLine()) != null) {
            String[] details = line.split(csvSplitBy);
            String dateStr = details[3];
            LocalDate date = LocalDate.parse(dateStr, dateFormatter);
            int weekOfYear = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);

            // Check if the current line's week matches the chosen week
            if (weekNumber == weekOfYear) {
                String employeeID = details[0];

                // Add employee ID to the employeeIDs string in the desired format
                if (!employeeIDs.contains(employeeID)) {
                    employeeIDs = employeeIDs.isEmpty() ? employeeID : employeeIDs + ", " + employeeID;
                }
            }
        }
     

        // Print the selected employee IDs
        System.out.println("Select from the employee IDs: " + employeeIDs); 
        
        System.out.print("Enter EmployeeID: ");
        String employeeNumber = scanner.nextLine();
        scanner.close();

           br.close();
     
           
           // Get the hourly rate
       // String currentPath = System.getProperty("user.dir");
        String csvFileEmpD = System.getProperty("user.dir") + File.separator + "resources\\EmployeeDetails.csv"; // Build the file path
        String linedata;
      //  String delimiter = "\\|"; // Comma delimiter used in the CSV file     
      
      BufferedReader brEmp = new BufferedReader(new FileReader(csvFileEmpD));
            String hourlyRate = null;
            
            while ((line = brEmp.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                 
                if (data[0].equals(employeeNumber)) {
                    hourlyRate = data[18];
                    break;
                }
            }
            
             br.close();
  System.out.println(hourlyRate);

            
      
           
           
        // Reset BufferedReader to the start of the file
        br = new BufferedReader(new FileReader(csvFile));
        br.readLine(); // Skip the header row again

        
        // Read and process each line of the CSV file to calculate hours worked
        while ((line = br.readLine()) != null) {
            String[] details = line.split(csvSplitBy);
            String employeeID = details[0];
            String employeeName = details[1] + " " + details[2];
            String dateStr = details[3];
            String logIn = details[4];
            String logOut = details[5];

            LocalDate date = LocalDate.parse(dateStr, dateFormatter);
            int weekOfYear = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);

            if (weekNumber == weekOfYear && employeeID.equals(employeeNumber)) {
                // Parse times
                LocalTime inTime = LocalTime.parse(logIn, timeFormatter);
                LocalTime outTime = LocalTime.parse(logOut, timeFormatter);

                // Calculate hours worked
                double hoursWorked = (double) java.time.Duration.between(inTime, outTime).toMinutes() / 60;

                // Update total hours for the employee
                String fullName = employeeID + " - " + employeeName;
                employeeHours.put(fullName, employeeHours.getOrDefault(fullName, 0.0) + hoursWorked);
            }
        }
        br.close();
        
        //String Totalhwork = hoursWorked;
        // Print total hours worked for the selected employee
        // Accumulate total hours worked for the selected employee
            double totalHoursWorked = employeeHours.values().stream().mapToDouble(Double::doubleValue).sum();

        System.out.println("Weekly Hours Worked for Week " + weekNumber + " by Employee " + employeeNumber + ":");
        for (Map.Entry<String, Double> entry : employeeHours.entrySet()) {
            System.out.printf("%s: %.2f hours%n", entry.getKey(), entry.getValue());
        }
        
        
        
        
      //  double totalHoursWorked = 0.0;
//        for (Map.Entry<String, Double> entry : employeeHours.entrySet()) {
//             totalHoursWorked += entry.getValue();
//        }
        
            // Calculate and print gross weekly salary
          //  int hrate = Integer.parseInt(hourlyRate);
                    // System.out.println(totalHoursWorked  );
                   //   System.out.println( hourlyRate);
            
                if (hourlyRate != null) {
                   double hrate = Double.parseDouble(hourlyRate);
    
                   double grossWeeklySalary = hrate * totalHoursWorked;
                    System.out.println("Gross Weekly Salary: " + grossWeeklySalary);
               } else {
                   System.out.println("Hourly rate is missing.");
                }
    }
}
