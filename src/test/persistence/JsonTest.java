package persistence;

import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTask(Task pomoTask, String task, Boolean isComplete) {
        assertEquals(task, pomoTask.getTaskToDo());
        assertEquals(isComplete, pomoTask.isCompleted());
    }
}
