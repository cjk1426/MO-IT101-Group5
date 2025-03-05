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
import java.util.Scanner;

public class WeeklyWorkHours {

    public static void main(String[] args) {


        String currentPath = System.getProperty("user.dir");
        String csvFile = currentPath + File.separator + "resources\\EmployeeDataAttendanceRecord.csv"; // Build the file path

        String line;
        String csvSplitBy = "\\|";
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        Map<String, Double> employeeHours = new HashMap<>();

        // Get the desired week from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the week: ");
        int weekNumber = scanner.nextInt();
        scanner.close();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header row
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Split line into columns
                String[] data = line.split(csvSplitBy);

                // Extract employee information
                String employeeID = data[0];
                String employeeName = data[1] + " " + data[2];
                String dateStr = data[3];
                String logIn = data[4];
                String logOut = data[5];


                //System.out.println(dateStr);
                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
               // System.out.println(date);
                // Check if the date matches the selected week
        
               // Locale locale;
                int weekOfYear = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
              //  System.out.println(weekOfYear);
                          // int weekOfYear = date.get(WeekFields.of(locale).weekOfYear());


                          if (weekNumber == weekOfYear){

                                    // Parse times
                                    LocalTime inTime = LocalTime.parse(logIn, timeFormatter);
                                    LocalTime outTime = LocalTime.parse(logOut, timeFormatter);

                                    // Calculate hours worked
                                    double hoursWorked = (double) java.time.Duration.between(inTime, outTime).toMinutes() / 60;

                                    // Update total hours for the employee
                                    String fullName = employeeID + " - " + employeeName;
                                    employeeHours.put(fullName, employeeHours.getOrDefault(fullName, 0.0) + hoursWorked);

                          };

            }



            // Print total hours worked for each employee
            System.out.println("Weekly Hours Worked for Week " + weekNumber + ":");
            for (Map.Entry<String, Double> entry : employeeHours.entrySet()) {
                System.out.printf("%s: %.2f hours%n", entry.getKey(), entry.getValue());
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error processing the data: " + e.getMessage());
        }
    }
}
