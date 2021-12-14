package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a task
public class Task implements Writable {
    public final Boolean complete = true;

    private String taskToDo;
    private Boolean isCompleted;

    // Constructs a task
    public Task(String taskToDo) {

        this.taskToDo = taskToDo;
        this.isCompleted = false;
    }

    // EFFECTS: Returns the task to do
    public String getTaskToDo() {
        return this.taskToDo;
    }

    // EFFECTS: Returns whether the task is completed or not
    public Boolean isCompleted() {
        return this.isCompleted;
    }

    // MODIFIES: this
    // EFFECTS: Completes the to-do task
    public void setCompleted() {
        if (!isCompleted) {
            isCompleted = complete;
            EventLog.getInstance().logEvent(new Event("A task was set to completed"));
        }
    }

    // EFFECTS: Returns task as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("task", taskToDo);
        json.put("is completed?", isCompleted);
        return json;
    }
}
