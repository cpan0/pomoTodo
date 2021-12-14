package persistence;

import org.json.JSONObject;

// This JsonReader references code from the JsonSerializationDemo - Writable
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
