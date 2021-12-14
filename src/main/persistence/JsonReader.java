package persistence;

import model.Event;
import model.EventLog;
import model.Task;
import model.ToDoList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads to-do list from JSON data stored in file
// This JsonReader references code from the JsonSerializationDemo - JsonReader
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads to-do list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ToDoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loading saved PomoToDo..."));

        return parseToDoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses to-do list from JSON object and returns it
    private ToDoList parseToDoList(JSONObject jsonObject) {
        ToDoList tdl = new ToDoList("PomoList");
        addTasks(tdl, jsonObject);
        EventLog.getInstance().logEvent(new Event("Loading finished..."));

        return tdl;
    }

    // MODIFIES: tdl
    // EFFECTS: parses tasks from JSON object and adds them to to-do list
    private void addTasks(ToDoList tdl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(tdl, nextTask);
        }
    }

    // MODIFIES: tdl
    // EFFECTS: parses task from JSON object and adds it to to-do list
    private void addTask(ToDoList wr, JSONObject jsonObject) {
        String taskToDo = jsonObject.getString("task");
        Boolean isCompleted = jsonObject.getBoolean("is completed?");
        Task task = new Task(taskToDo);
        if (isCompleted) {
            task.setCompleted();
        }
        wr.addTask(task);
    }
}
