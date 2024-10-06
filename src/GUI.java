import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GUI {
    private DatabaseHandler dbHandler;

    public GUI() {
        dbHandler = new DatabaseHandler();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Airport Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel flightLabel = new JLabel("Flight Number:");
        flightLabel.setBounds(10, 20, 120, 25);
        panel.add(flightLabel);

        JTextField flightText = new JTextField(20);
        flightText.setBounds(150, 20, 165, 25);
        panel.add(flightText);

        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setBounds(10, 50, 120, 25);
        panel.add(destinationLabel);

        JTextField destinationText = new JTextField(20);
        destinationText.setBounds(150, 50, 165, 25);
        panel.add(destinationText);

        JLabel departureLabel = new JLabel("Departure Time:");
        departureLabel.setBounds(10, 80, 120, 25);
        panel.add(departureLabel);

        JTextField departureText = new JTextField(20);
        departureText.setBounds(150, 80, 165, 25);
        panel.add(departureText);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(10, 110, 120, 25);
        panel.add(statusLabel);

        JTextField statusText = new JTextField(20);
        statusText.setBounds(150, 110, 165, 25);
        panel.add(statusText);

        JButton addFlightButton = new JButton("Add Flight");
        addFlightButton.setBounds(10, 140, 150, 25);
        panel.add(addFlightButton);

        addFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String flightNumber = flightText.getText();
                String destination = destinationText.getText();
                String departureTime = departureText.getText();
                String status = statusText.getText();
                dbHandler.insertFlight(flightNumber, destination, departureTime, status);
                JOptionPane.showMessageDialog(panel, "Flight Added!");
                flightText.setText("");
                destinationText.setText("");
                departureText.setText("");
                statusText.setText("");
            }
        });

        JButton viewFlightsButton = new JButton("View Flights");
        viewFlightsButton.setBounds(170, 140, 150, 25);
        panel.add(viewFlightsButton);

        viewFlightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet flights = dbHandler.displayFlights();
                StringBuilder flightInfo = new StringBuilder();
                try {
                    while (flights != null && flights.next()) {
                        flightInfo.append("Flight ID: ").append(flights.getInt("id"))
                                .append(", Flight Number: ").append(flights.getString("flight_number"))
                                .append(", Destination: ").append(flights.getString("destination"))
                                .append(", Departure Time: ").append(flights.getString("departure_time"))
                                .append(", Status: ").append(flights.getString("status"))
                                .append("\n");
                    }
                    JOptionPane.showMessageDialog(panel, flightInfo.toString());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Additional components for passengers can be added here in a similar way
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
