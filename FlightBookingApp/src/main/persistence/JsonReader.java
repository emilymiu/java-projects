package persistence;

import model.Bookings;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Flight;
import model.FlightManager;
import org.json.*;

// represents a reader that reads flight manager from JSON data stored in file
// used code from JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    // used code from JsonSerializationDemo
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads flight manager from file and returns it;
    // throws IOException if an error occurs reading data from file
    // used code from JsonSerializationDemo
    public FlightManager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFlightManager(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    // used code from JsonSerializationDemo
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses flight manager from JSON object and returns it
    // used code similar to JsonSerializationDemo
    private FlightManager parseFlightManager(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        FlightManager flightManager = new FlightManager(name);
        addBookings(flightManager, jsonObject);
        return flightManager;
    }

    // MODIFIES: flightManager
    // EFFECTS: parse bookings from JSON object and adds them to flight manager
    // used code from JsonSerializationDemo
    private void addBookings(FlightManager flightManager, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("bookings");
        for (Object json : jsonArray) {
            JSONObject nextBooking = (JSONObject) json;
            addBooking(flightManager, nextBooking);
        }
    }

    // MODIFIES: flightManager
    // EFFECTS: parses booking from JSON object and adds it to flight manager
    // used code from JsonSerializationDemo
    private void addBooking(FlightManager flightManager, JSONObject jsonObject) {
        String bookingID = jsonObject.getString("id");
        Bookings booking = new Bookings(bookingID);
        flightManager.setBookings(booking);
        JSONArray jsonArray = jsonObject.getJSONArray("bookings");
        createFlight(jsonArray, booking);
    }

    // MODIFIES: flightManager
    // EFFECTS: creates a flight from JSON object and adds it to a booking
    private void createFlight(JSONArray jsonArray, Bookings booking) {
        for (Object o : jsonArray) {
            JSONObject flightObject = (JSONObject) o;
            String departureTime = flightObject.getString("departureTime");
            int numSeatsLeft = flightObject.getInt("numSeatsLeft");
            double price = flightObject.getDouble("price");
            String origin = flightObject.getString("origin");
            String destination = flightObject.getString("destination");
            String departureDate = flightObject.getString("departureDate");
            int flightNumber = flightObject.getInt("flightNumber");
            Flight flight = new Flight(flightNumber, price, origin, destination, departureTime,
                    departureDate, numSeatsLeft);
            booking.addFlight(flight);
        }
    }



}