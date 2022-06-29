package ui;

import exceptions.FullFlightException;
import model.FlightManager;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Flight booking application
public class FlightApp {
    private static final String JSON_STORE = "./data/FlightManager.json";
    private Scanner input;
    private FlightManager flightManager;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // MODIFIES: this
    // EFFECTS: runs the booking application and initializes flight and user
    public FlightApp() {
        input = new Scanner(System.in);
        flightManager = new FlightManager("My Flight Manager");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // used some code from TellerApp
    public void runFlight() throws FullFlightException {
        boolean keepRunning = true;
        String command;

        while (keepRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nFarewell!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    // used some code from TellerApp
    private void processCommand(String command) throws FullFlightException {
        if (command.equals("b")) {
            bookFlight();
        } else if (command.equals("v")) {
            System.out.println(flightManager.viewBookings());
        } else if (command.equals("s")) {
            saveFlightManager();
        } else if (command.equals("l")) {
            loadFlightManager();
        } else {
            System.out.println("Selection not valid...");
        }

    }

    // EFFECTS: displays menu of options for user to pick
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tb -> book a flight");
        System.out.println("\tv -> view your bookings");
        System.out.println("\ts -> save booking to file");
        System.out.println("\tl -> load booking from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: conducts a booking of a flight
    // used some similar code to TellerApp
    private void bookFlight() throws FullFlightException {
        boolean bookedSuccessfully = false;
        while (!bookedSuccessfully) {
            selectClass();
            System.out.println("Where are you flying from?");
            String origin = input.next();
            System.out.println("Where would you like to go?");
            String destination = input.next();
            bookedSuccessfully = flightManager.canFly(origin, destination);
            if (!bookedSuccessfully) {
                System.out.println("Cannot book your flight.\n");
            }
        }

        System.out.println("When would you like to depart? " + "\tPlease enter as: Month.day");
        String departureDate = input.next();
        flightManager.assignDepartureDate(departureDate);

        System.out.println("\nSelect from these departure times:");
        System.out.println("\t1 -> 8:30");
        System.out.println("\t2 -> 16:00");
        System.out.println("\t3 -> 19:00");
        int departureChoice = input.nextInt();
        flightManager.assignDepartureTime(departureChoice);
        bookingSuccessful();
    }

    // EFFECTS: displays booking confirmation message if booking successful
    private void bookingSuccessful() throws FullFlightException {
        if (flightManager.purchaseTicket()) {
            System.out.println("Your flight has been booked!");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select economy, business, or first class
    // used some similar code to TellerApp
    public void selectClass() {
        String selection;

        System.out.println("e for economy");
        System.out.println("b for business");
        System.out.println("f for first class");

        selection = input.next();
        flightManager.assignClass(selection);
    }

    // EFFECTS: saves the flight manager to file
    // used code from JsonSerializationDemo
    private void saveFlightManager() {
        try {
            jsonWriter.open();
            jsonWriter.write(flightManager);
            jsonWriter.close();
            System.out.println("Saved " + flightManager.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads flight manager from file
    // used code from JsonSerializationDemo
    private void loadFlightManager() {
        try {
            flightManager = jsonReader.read();
            System.out.println("Loaded " + flightManager.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}



