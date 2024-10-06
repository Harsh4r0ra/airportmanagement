import java.sql.*;

public class DatabaseHandler {
    private Connection conn;

    public DatabaseHandler() {
        try {
            // Connect to SQLite database (or create it)
            conn = DriverManager.getConnection("jdbc:sqlite:airport.db");
            System.out.println("Connected to the SQLite database.");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    // Create tables for flights and passengers
    public void createTables() {
        String createFlightTable = "CREATE TABLE IF NOT EXISTS flights ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "flight_number TEXT NOT NULL,"
                + "destination TEXT NOT NULL,"
                + "departure_time TEXT NOT NULL"
                + ");";

        String createPassengerTable = "CREATE TABLE IF NOT EXISTS passengers ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "flight_id INTEGER,"
                + "FOREIGN KEY(flight_id) REFERENCES flights(id)"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            // Execute the create table statements
            stmt.execute(createFlightTable);
            stmt.execute(createPassengerTable);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    // Method to insert flight data
    public void insertFlight(String flightNumber, String destination, String departureTime) {
        String insertFlightSQL = "INSERT INTO flights(flight_number, destination, departure_time) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertFlightSQL)) {
            pstmt.setString(1, flightNumber);
            pstmt.setString(2, destination);
            pstmt.setString(3, departureTime);
            pstmt.executeUpdate();
            System.out.println("Flight added successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting flight: " + e.getMessage());
        }
    }

    // Method to insert passenger data
    public void insertPassenger(String name, int flightId) {
        String insertPassengerSQL = "INSERT INTO passengers(name, flight_id) VALUES(?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertPassengerSQL)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, flightId);
            pstmt.executeUpdate();
            System.out.println("Passenger added successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting passenger: " + e.getMessage());
        }
    }

    // Method to retrieve flight details
    public void displayFlights() {
        String query = "SELECT * FROM flights";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Flight ID: " + rs.getInt("id"));
                System.out.println("Flight Number: " + rs.getString("flight_number"));
                System.out.println("Destination: " + rs.getString("destination"));
                System.out.println("Departure Time: " + rs.getString("departure_time"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching flights: " + e.getMessage());
        }
    }

    // Method to retrieve passenger details
    public void displayPassengers() {
        String query = "SELECT passengers.name, flights.flight_number FROM passengers "
                + "JOIN flights ON passengers.flight_id = flights.id";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Passenger Name: " + rs.getString("name"));
                System.out.println("Flight Number: " + rs.getString("flight_number"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching passengers: " + e.getMessage());
        }
    }
}
