import java.util.*;

public class ElectionNormal {

   public static void main(String[] args) {
      Scanner scnr = new Scanner(System.in);

// Project Title
      System.out.println("Fall 2024 - CS1083 - Section 006 - Project 1 - Election - writtten by John Thompson\n");

// Section 1: Declare all variables
      double intialPercent;
      double dailyPercent;
      double weekendPercent;
      char lastMonthDay;

      int monthDays;
      int totalWeeks;
      int dayCount = 0;
      double totalMonthlyPercent = 0.0;

// Section 2: Gather variable inputs, while keeping multiple choice integrity.
      System.out.print("Please, input the intial percent increase: ");
      intialPercent = scnr.nextDouble();

      System.out.print("Please, input the daily percent: ");
      dailyPercent = scnr.nextDouble();

      System.out.print("Please, input the weekend percent increase: ");
      weekendPercent = scnr.nextDouble();

      System.out.print("Last day of the month (A-28, B-30, C-31): ");
      lastMonthDay = scnr.next().charAt(0);

      System.out.print("\n");

// Section 3: Calculate numbers
      switch (lastMonthDay){
            case 'a':
            case 'A':
                  monthDays = 28;
                  break;
            case 'b':
            case 'B':
                  monthDays = 30;
                  break;
            case 'c':
            case 'C':
                  monthDays = 31;
                  break;
            default:
                  System.out.println("How did you even get this result? Read the instructions! I'm just going to assume you meant there are 28 days in the month!");
                  monthDays = 0;
                  break;
      }
      totalWeeks = monthDays % 7;
      if (totalWeeks == 0)
            totalWeeks = 4;
      else 
            totalWeeks = 5;


// Section 4: Print the Table!
// Section 4.a: Print the table Header
      System.out.printf("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s\n",
              "Week", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Total/Week");

// Section 4.b: Print the weekly percentages
      //start going through the weeks:
      for (int i = 1; i<= totalWeeks; ++i){
            System.out.printf("%-10s", i);
            double addedWeeklyPercent = 0.0;
            for (int weekDay = 1; weekDay <= 8; ++weekDay){
                  if (weekDay <= 7) {
                        dayCount++;
                  }
                  if (dayCount > monthDays && (weekDay != 8)) {
                        System.out.printf("%-10s", "0-0.000");
                        addedWeeklyPercent += 0.0;
                  }
                  else if (dayCount == 1){
                        System.out.printf("%-10s", dayCount + "-" + (intialPercent+dailyPercent));
                        addedWeeklyPercent += intialPercent + dailyPercent;
                  }
                  else if (weekDay <= 5) {
                        System.out.printf("%-10s", dayCount + "-" + dailyPercent);
                        addedWeeklyPercent += dailyPercent;
                  }
                  else if (weekDay <= 7) {
                        System.out.printf("%-10s", dayCount + "-" + weekendPercent);
                        addedWeeklyPercent += weekendPercent;
                  }
                  if (weekDay == 8) {
                        System.out.printf("W%d-%.3f\n", i, addedWeeklyPercent);
                        totalMonthlyPercent += addedWeeklyPercent;
                  }
            }
      }
// Section 4.c: Print the end of month calculations
      System.out.printf("End of Month Percent: %.3f\n", totalMonthlyPercent);
// Close out the scanner
      scnr.close();
   }
}