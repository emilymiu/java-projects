package model;

// represents a flight having a flight number, price, origin, destination, departure time and date, and capacity
public class Flight {
    private int flightNumber;             // flight number
    private double price;                 // price for flight (economy = 300.00, business = 500.00, first = 800.00)
    private String origin;                // origin of flight
    private String destination;           // destination of flight
    private String departureTime;         // departure time of flight (24hr clock)
    private String departureDate;         // departure date of flight
    private int flightCapacity;           // seat capacity of flight
    private int numSeatsLeft;             // number of seats remaining
    private int numTickets;               // number of tickets

    // REQUIRES: origin, destination, departure time/date must be non-zero length
    // EFFECTS: flight number, original price, origin, destination, departure time/date,
    //          flight capacity are set to appropriate fields
    public Flight(int fn, double p, String o, String d, String dt, String dd, int fc) {
        flightNumber = fn;
        price = p;
        origin = o;
        destination = d;
        departureTime = dt;
        departureDate = dd;
        flightCapacity = fc;
        numSeatsLeft = fc;
        numTickets = 1;
    }

    // getters
    public int getFlightNumber() {
        return flightNumber;
    }

    public double getPrice() {
        return price;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public int getFlightCapacity() {
        return flightCapacity;
    }

    public int getNumSeatsLeft() {
        return numSeatsLeft;
    }

    // MODIFIES: this
    // EFFECTS: returns true if there are seats left, false otherwise
    public boolean checkSeatsLeft() {
        if (numSeatsLeft > 0) {
            numSeatsLeft -= numTickets;
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: sets destination for flight
    public void setDestination(String d) {
        destination = d;
    }

    // MODIFIES: this
    // EFFECTS: sets origin for flight
    public void setOrigin(String o) {
        origin = o;
    }

    // MODIFIES: this
    // EFFECTS: sets departure time for flight
    public void setDepartureTime(String dt) {
        departureTime = dt;
    }

    // MODIFIES: this
    // EFFECTS: sets departure date for flight
    public void setDepartureDate(String dd) {
        departureDate = dd;
    }

    // MODIFIES: this
    // EFFECTS: sets price of ticket
    public void setPrice(double p) {
        price = p;
    }

    // MODIFIES: this
    // EFFECTS: sets flight number
    public void setFlightNumber(int fn) {
        flightNumber = fn + 1;
    }

    // MODIFIES: this
    // EFFECTS: sets number of seats left
    public void setNumSeatsLeft(int fc) {
        numSeatsLeft = fc;
    }

    // EFFECTS: displays flight details
    public String displayFlight() {
        return "Flight " + flightNumber + " from " + origin + " to " + destination + " leaving on "
                + departureDate + " at " + departureTime + " for " + "$" + price;
    }

}