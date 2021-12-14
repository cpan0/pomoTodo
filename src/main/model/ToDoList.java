package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a to-do list of tasks
public class ToDoList implements Writable {
    private ArrayList<Task> toDoList;
    private String name;

    // Constructs a to-do list
    public ToDoList(String name) {
        this.name = name;
        this.toDoList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Task t is added to the ToDoList
    public void addTask(Task t) {
        toDoList.add(t);
        EventLog.getInstance().logEvent(new Event("A task was added to the " + name));
    }

    // REQUIRES: Task t is an element of the ToDoList
    // MODIFIES: this
    // EFFECTS: Task t is removed from the ToDoList
    public void deleteTask(Task t) {
        toDoList.remove(t);
        EventLog.getInstance().logEvent(new Event("Deleted a task from " + name));
    }

    // EFFECTS: Returns the number of tasks in the ToDoList
    public int getSize() {
        return toDoList.size();
    }

    // EFFECTS: Returns the name of the ToDoList
    public String getName() {
        return this.name;
    }

    // EFFECTS: Returns to-do list
    public ArrayList<Task> getToDoList() {
        return toDoList;
    }

    // EFFECTS: Returns true if Task t is in the ToDoList
    // and false otherwise
    public boolean contains(Task t) {
        return toDoList.contains(t);
    }

    // REQUIRES: toDoList is not empty
    // EFFECTS: Returns Task at index i
    public Task getTask(int i) {
        return toDoList.get(i);
    }

    // MODIFIES: this and Task t
    // EFFECTS: completed the uncompleted Task t in the ToDoList
    public void updateTask(Task t) {
        for (Task task: toDoList) {
            if (t == task) {
                task.setCompleted();
            }
        }
    }

    // EFFECTS: Returns list with complete tasks only
    public ToDoList filterByCompletion() {
        ToDoList filterCompletion = new ToDoList("completion filtering list");
        EventLog.getInstance().logEvent(new Event("A " + filterCompletion.getName() + " was created..."));
        for (Task t : toDoList) {
            if (t.isCompleted()) {
                filterCompletion.addTask(t);
            }
        }
        EventLog.getInstance().logEvent(new Event("The " + name + " was filtered by completion..."));

        return filterCompletion;
    }

    // EFFECTS: Returns list with incomplete tasks only
    public ToDoList filterByInCompletion() {
        ToDoList filterInCompletion = new ToDoList("incompletion filtering list");
        EventLog.getInstance().logEvent(new Event("A " + filterInCompletion.getName() + " was created..."));
        for (Task t : toDoList) {
            if (!(t.isCompleted())) {
                filterInCompletion.addTask(t);
            }
        }
        EventLog.getInstance().logEvent(new Event("The " + name + " was filtered by incompletion..."));

        return filterInCompletion;
    }

    // EFFECTS: returns tasks in this list as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tasks", toDoListToJson());
        return json;
    }

    // EFFECTS: returns tasks in this list as a JSON array
    private JSONArray toDoListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : toDoList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
