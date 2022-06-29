package persistence;

import org.json.JSONObject;

public interface Writable {

    // EFFECTS: returns this as JSON object
    // used code from JsonSerializationDemo
    JSONObject toJson();
}
