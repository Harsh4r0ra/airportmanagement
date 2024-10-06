import java.util.Scanner;

public class AirportManagement {
    public static void main(String[] args) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.createTables();  // Create tables on startup

        Airport airport = new Airport(dbHandler);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Airport Management System");
            System.out.println("1. Add Flight");
            System.out.println("2. Add Passenger");
            System.out.println("3. Display Flights");
            System.out.println("4. Display Passengers");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter flight number: ");
                    String flightNumber = scanner.nextLine();
                    System.out.print("Enter destination: ");
                    String destination = scanner.nextLine();
                    System.out.print("Enter departure time (YYYY-MM-DD HH:MM:SS): ");
                    String departureTime = scanner.nextLine();
                    airport.addFlight(flightNumber, destination, departureTime);
                    break;
                case 2:
                    System.out.print("Enter passenger name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter flight ID: ");
                    int flightId = scanner.nextInt();
                    airport.addPassenger(name, flightId);
                    break;
                case 3:
                    airport.showFlights();
                    break;
                case 4:
                    airport.showPassengers();
                    break;
                case 5:
                    System.out.println("Exiting system...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}
