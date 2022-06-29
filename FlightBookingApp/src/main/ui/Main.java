package ui;

import exceptions.FullFlightException;

// runs FlightApp

public class Main {
    public static void main(String[] args) {
        try {
            FlightApp flightApp = new FlightApp();
            flightApp.runFlight();
        } catch (FullFlightException e) {
            System.out.println("Your chosen flight is full.");
        }

    }
}
