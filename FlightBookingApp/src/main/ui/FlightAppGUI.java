package ui;

import model.FlightManager;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;

// graphical user interface that books flights
public class FlightAppGUI {
    public static final String JSON_STORE = "./data/FlightManager.json";
    protected FlightManager flightManager;
    protected JsonWriter jsonWriter;
    protected JsonReader jsonReader;
    private JFrame frame;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    
    public FlightAppGUI() {
        frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);

        initializeFields();
        initializeBackground();
        initializeMenu();
        frame.revalidate();
        frame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: initializes fields
    private void initializeFields() {
        flightManager = new FlightManager("My Flight Manager");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: creates buttons for main menu
    private void initializeMenu() {
        JPanel homeMenu = new HomeMenu(this);
        frame.add(homeMenu);

    }

    // EFFECTS: creates background for GUI
    private void initializeBackground() {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // EFFECTS: runs GUI
    public static void main(String[] args) {
        new FlightAppGUI();
    }

    // EFFECTS: return flight manager controlled by this UI
    public FlightManager getFlightManager() {
        return flightManager;
    }

    // EFFECTS: return flight manager controlled by this UI
    public JsonReader getJsonReader() {
        return jsonReader;
    }

    // EFFECTS: return flight manager controlled by this UI
    public JsonWriter getJsonWriter() {
        return jsonWriter;
    }

}
