package persistence;

import model.FlightManager;
import org.json.JSONObject;

import java.io.*;

// represents a writer that writes JSON representation of flight manager to file
// used code from JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    // used code from JsonSerializationDemo
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    // used code from JsonSerializationDemo
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of office to file
    // used code from JsonSerializationDemo
    public void write(FlightManager flightManager) {
        JSONObject json = flightManager.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    // used code from JsonSerializationDemo
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    // used code from JsonSerializationDemo
    private void saveToFile(String json) {
        writer.print(json);
    }
}
