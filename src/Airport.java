import java.sql.ResultSet;
import java.sql.SQLException;

public class Airport {
    private DatabaseHandler db;

    public Airport(DatabaseHandler dbHandler) {
        this.db = dbHandler;
    }

    public void addFlight(String flightNumber, String destination, String departureTime, String status) {
        db.insertFlight(flightNumber, destination, departureTime, status);
    }

    public void addPassenger(String name, int flightId) {
        db.insertPassenger(name, flightId);
    }

    public void displayFlights() {
        ResultSet flights = db.displayFlights();
        try {
            while (flights != null && flights.next()) {
                System.out.println("Flight ID: " + flights.getInt("id") +
                        ", Flight Number: " + flights.getString("flight_number") +
                        ", Destination: " + flights.getString("destination") +
                        ", Departure Time: " + flights.getString("departure_time") +
                        ", Status: " + flights.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayPassengers() {
        ResultSet passengers = db.displayPassengers();
        try {
            while (passengers != null && passengers.next()) {
                System.out.println("Passenger ID: " + passengers.getInt("id") +
                        ", Name: " + passengers.getString("name") +
                        ", Flight ID: " + passengers.getInt("flight_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
