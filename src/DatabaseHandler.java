import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {
    private Connection connection;

    public DatabaseHandler() {
        try {
            // Connect to the SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:airport.db");
            createTables(); // Create tables if they don't exist
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        String createFlightsTable = "CREATE TABLE IF NOT EXISTS flights (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "flight_number TEXT," +
                "destination TEXT," +
                "departure_time TEXT," +
                "status TEXT)";

        String createPassengersTable = "CREATE TABLE IF NOT EXISTS passengers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "flight_id INTEGER," +
                "FOREIGN KEY(flight_id) REFERENCES flights(id))";

        try (PreparedStatement pstmt = connection.prepareStatement(createFlightsTable)) {
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement pstmt = connection.prepareStatement(createPassengersTable)) {
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertFlight(String flightNumber, String destination, String departureTime, String status) {
        String sql = "INSERT INTO flights (flight_number, destination, departure_time, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, flightNumber);
            pstmt.setString(2, destination);
            pstmt.setString(3, departureTime);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPassenger(String name, int flightId) {
        String sql = "INSERT INTO passengers (name, flight_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, flightId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet displayFlights() {
        String sql = "SELECT * FROM flights";
        ResultSet rs = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet displayPassengers() {
        String sql = "SELECT * FROM passengers";
        ResultSet rs = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
