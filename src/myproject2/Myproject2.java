
package myproject2;

import java.util.Scanner;

public class Myproject2 {

  
    public static void main(String[] args) {
       
       
        Scanner scanner = new Scanner(System.in);

      
        int numStops = 0;
        while (numStops <= 0) {
            System.out.print("Enter the number of stops on the route : ");
            if (scanner.hasNextInt()) {
                numStops = scanner.nextInt();
                if (numStops <= 0) {
                    System.out.println("Please enter a positive integer!");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next(); 
            }
        }
        scanner.nextLine(); 

        
        String[] stopNames = new String[numStops];
        int[] boarding = new int[numStops];
        int[] alighting = new int[numStops];

        
        for (int i = 0; i < numStops; i++) {
            System.out.println("\n--- Stop " + (i + 1) + " ---");
            
            System.out.print("Enter stop name: ");
            stopNames[i] = scanner.nextLine();

            boarding[i] = getValidInteger(scanner, "Enter number of passengers boarding: ");
            alighting[i] = getValidInteger(scanner, "Enter number of passengers alighting (getting off): ");
        }

      
        int capacity = getValidInteger(scanner, "\nEnter the bus's seating capacity: ");

        
        int[] currentOccupancyAtStop = new int[numStops];
        int currentOccupancy = 0;
        int overCapacityCount = 0;

        System.out.println("\n==================================================");
        System.out.println("PROCESSING ROUTE STOPS...");
        System.out.println("==================================================");

  
        for (int i = 0; i < numStops; i++) {
        
            int tentativeOccupancy = currentOccupancy + boarding[i] - alighting[i];

            
            if (tentativeOccupancy < 0) {
                System.out.println("Data error at [" + stopNames[i] + "]: cannot have more passengers alighting than are currently on the bus. Occupancy set to 0.");
                currentOccupancy = 0;
            } else {
                currentOccupancy = tentativeOccupancy;
            }

            currentOccupancyAtStop[i] = currentOccupancy;

            
            System.out.println("[" + stopNames[i] + "] After stop current passenger count: " + currentOccupancy);

            
            if (currentOccupancy > capacity) {
                System.out.println("Warning: Bus is over capacity at [" + stopNames[i] + "]! (Passengers: " + currentOccupancy + ", Capacity: " + capacity + ")");
                overCapacityCount++;
            }
        }

  
        System.out.println("\n=================================================================");
        System.out.printf("%-25s %-10s %-10s %-15s%n", "Stop Name", "Boarding", "Alighting", "Current Occupancy");
        System.out.println("=================================================================");
        for (int i = 0; i < numStops; i++) {
            System.out.printf("%-25s %-10d %-10d %-15d%n", stopNames[i], boarding[i], alighting[i], currentOccupancyAtStop[i]);
        }
        System.out.println("=================================================================");

        
        System.out.println("\nSTATISTICS:");

        
        int maxBoarding = boarding[0];
        int busiestIndex = 0;
        int totalOccupancySum = 0;

        for (int i = 0; i < numStops; i++) {
            if (boarding[i] > maxBoarding) {
                maxBoarding = boarding[i];
                busiestIndex = i;
            }
            totalOccupancySum += currentOccupancyAtStop[i];
        }

        System.out.println("- Busiest stop (most boarding): " + stopNames[busiestIndex] + " (" + maxBoarding + " passengers)");

        
        double avgOccupancy = (double) totalOccupancySum / numStops;
        System.out.printf("- Average occupancy across stops: %.2f passengers%n", avgOccupancy);

        
        System.out.println("- Number of stops exceeding capacity: " + overCapacityCount);

        
        if (currentOccupancy != 0) {
            System.out.println("Warning: " + currentOccupancy + " passengers still on the bus after the final stop — please check your data.");
        }

        scanner.close();
    }

  
    private static int getValidInteger(Scanner scanner, String prompt) {
        int value = -1;
        while (value < 0) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value < 0) {
                    System.out.println("Value cannot be negative. Please enter a valid number.");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.next(); 
            }
        }
        scanner.nextLine(); 
        return value;
    }
}
