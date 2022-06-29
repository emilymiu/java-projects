package model;

import org.json.JSONObject;
import persistence.Writable;
import java.util.LinkedList;
import java.util.List;

// represents a booking which is a list of flights
public class Bookings implements Writable {
    LinkedList<Flight> bookings;
    String bookingID;

    // EFFECTS: constructor, bookings is a list of flights, booking id is a string of 6 numbers
    public Bookings(String id) {
        bookings = new LinkedList<>();
        bookingID = id;
    }

    // EFFECTS: getter
    public String getBookingID() {
        return bookingID;
    }

    // MODIFIES: this
    // EFFECTS: adds a flight to the list of bookings and assigns flight number
    public void addFlight(Flight f) {
        int flightNumber = f.getFlightNumber() + 1;
        bookings.add(f);
        f.setFlightNumber(flightNumber);
    }

    // EFFECTS: returns the flights of bookings
    public List<Flight> displayBookings() {
        List<Flight> flightList = new LinkedList<>();
        for (int i = 0; i < bookings.size(); i++) {
            flightList.add(bookings.get(i));
        }
        return flightList;
    }

    // EFFECTS: see interface Writable
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", bookingID);
        json.put("bookings", bookings);
        return json;
    }
}


