package model;

import exceptions.FullFlightException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

// represents a flight manager that performs various tasks needed in order to book a flight
public class FlightManager implements Writable {
    private String name;
    protected List<Bookings> allBookings;
    protected Flight selected;
    protected Bookings newBooking;

    public FlightManager(String name) {
        this.name = name;
        selected = new Flight(237809, 0.0, "", "", "", "", 2);
        newBooking = new Bookings("256098");
        allBookings = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: sets bookings from JsonReader
    public void setBookings(Bookings booking) {
        newBooking = booking;
    }

    // EFFECTS: getter
    public List<Bookings> getBookings() {
        return allBookings;
    }

    // EFFECTS: getter
    public String getName() {
        return name;
    }

    // EFFECTS: returns number of bookings in the list of bookings
    public int numBookings() {
        return allBookings.size();
    }

    // MODIFIES: this
    // EFFECTS: return true and set origin and destination if they are different,
    //          false otherwise
    public boolean canFly(String o, String d) {
        if (!d.equals(o)) {
            selected.setDestination(d);
            selected.setOrigin(o);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: assigns departure time corresponding to user input
    public void assignDepartureTime(int departureChoice) {
        if (departureChoice == 1) {
            selected.setDepartureTime("8:30");
        } else if (departureChoice == 2) {
            selected.setDepartureTime("16:00");
        } else {
            selected.setDepartureTime("19:00");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets departure date
    public void assignDepartureDate(String date) {
        selected.setDepartureDate(date);
    }

    // MODIFIES: this
    // EFFECTS: sets the price of ticket corresponding to the class of flight
    public void assignClass(String classChoice) {
        if (classChoice.equals("e")) {
            selected.setPrice(300.00);
        } else if (classChoice.equals("b")) {
            selected.setPrice(500.00);
        } else {
            selected.setPrice(800.00);
        }
    }

    // MODIFIES: this
    // EFFECTS: books flight based on number of seats available and adds booking to flight manager
    //          return true if successful
    //          throw FullFlightException if !selected.checkSeatsLeft
    public boolean purchaseTicket() throws FullFlightException {
        if (selected.checkSeatsLeft()) {
            newBooking.addFlight(selected);
            addBookings(newBooking);
            return true;
        } else {
            throw new FullFlightException("Your chosen flight is full.");
        }
    }

    // EFFECTS: returns all flights in a booking
    public List<String> viewBookings() {
        List<String> s1 = new LinkedList<>();
        List<Flight> bookings = newBooking.displayBookings();
        for (Flight b : bookings) {
            s1.add(b.displayFlight());
        }
        return s1;
    }

    // MODIFIES: this
    // EFFECTS: adds the booking to a list of all bookings
    public void addBookings(Bookings b) {
        allBookings.add(b);
    }

    // EFFECTS: see interface Writable
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("bookings", bookingsToJson());
        return json;
    }

    // EFFECTS: returns bookings in this office as a JSON array
    // used code from JsonSerializationDemo
    private JSONArray bookingsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Bookings b : allBookings) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }

}
