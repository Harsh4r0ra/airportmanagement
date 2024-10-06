public class Airport {
    private DatabaseHandler db;

    public Airport(DatabaseHandler dbHandler) {
        this.db = dbHandler;
    }

    public void addFlight(String flightNumber, String destination, String departureTime) {
        db.insertFlight(flightNumber, destination, departureTime);
    }

    public void addPassenger(String name, int flightId) {
        db.insertPassenger(name, flightId);
    }

    public void showFlights() {
        db.displayFlights();
    }

    public void showPassengers() {
        db.displayPassengers();
    }
}
