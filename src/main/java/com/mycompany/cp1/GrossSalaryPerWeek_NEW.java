package com.mycompany.cp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;

public class GrossSalaryPerWeek_NEW {

//    public static void main(String[] args) throws IOException {
//        int weekNumber = 10;  // Example week number
//        String employeeNumber = "E001";  // Example employee number
//        calculateGrossSalary(weekNumber, employeeNumber);
//    }

    public static void calculateGrossSalary(int weekNumber, String employeeNumber) throws IOException {
        String currentPath = System.getProperty("user.dir");
        String csvFile = currentPath + File.separator + "resources" + File.separator + "EmployeeDataAttendanceRecord.csv";
        String csvFileEmpD = currentPath + File.separator + "resources" + File.separator + "EmployeeDetails.csv";
        //for update add the SSS matrix resources csv file
         String csvSSSMatrix = currentPath + File.separator + "resources" + File.separator + "SSS Contribution_Mod.csv";
        
        
        String csvSplitBy = "\\|";
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        Map<String, Double> employeeHours = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip header row
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(csvSplitBy);
                String employeeID = details[0];
                String employeeName = details[1] + " " + details[2];
                LocalDate date = LocalDate.parse(details[3], dateFormatter);
                String logIn = details[4];
                String logOut = details[5];
                int weekOfYear = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);

                if (weekNumber == weekOfYear && employeeID.equals(employeeNumber)) {
                    LocalTime inTime = LocalTime.parse(logIn, timeFormatter);
                    LocalTime outTime = LocalTime.parse(logOut, timeFormatter);
                    double hoursWorked = (double) java.time.Duration.between(inTime, outTime).toMinutes() / 60;
                    String fullName = employeeID + " - " + employeeName;
                    employeeHours.put(fullName, employeeHours.getOrDefault(fullName, 0.0) + hoursWorked);
                }
            }
        }

        double totalHoursWorked = employeeHours.values().stream().mapToDouble(Double::doubleValue).sum();
        System.out.printf("Weekly Hours Worked for Week %d by Employee %s: %.2f hours%n", weekNumber, employeeNumber, totalHoursWorked);

        String hourlyRate = null;
        String BasicSalary = null;
        
        try (BufferedReader brEmp = new BufferedReader(new FileReader(csvFileEmpD))) {
            String line;
            while ((line = brEmp.readLine()) != null) {
                String[] details = line.split(csvSplitBy);
                if (details[0].equals(employeeNumber)) {
                    hourlyRate = details[18];
                    BasicSalary = details[14]; // basic salary
                    break;
                }

             
            }
        }
        //check the SSS salary deduction range 
        //if basic salary within the range true then deduction on details[3]
        
        try (BufferedReader SssLookUp = new BufferedReader(new FileReader(csvSSSMatrix))) {
            String line;
//            double Bs = Double.parseDouble(BasicSalary);
            
            
            while ((line = SssLookUp.readLine()) != null) {
                String[] details = line.split(csvSplitBy);
                        
                
               // double min = details[0].equals("Min") ? 0 : Double.parseDouble(details[0].replace(",", ""));
                
         

        try {
            int number = Integer.parseInt(details[0]);
            System.out.println("Integer value: " + number);
        } catch (NumberFormatException e) {
            System.out.println("Invalid integer string: " + details[0]);
        }
                        
                          //System.out.println( parse.int(details[0]));  
                        
                    

                    
                 
            }
        }
        
        
        //for update
        
        if (hourlyRate != null) {
            double hrate = Double.parseDouble(hourlyRate);
            double grossWeeklySalary = hrate * totalHoursWorked;
            System.out.printf("Gross Weekly Salary: %.2f%n", grossWeeklySalary);
        } else {
            System.out.println("Hourly rate is missing.");
        }
    }
}
