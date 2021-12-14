package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for Task
public class TaskTest {
    private Task testTask;

    @BeforeEach
    public void runBefore() {
        testTask = new Task("CPSC 210 Project");
    }

    @Test
    public void testConstructor() {
        assertEquals("CPSC 210 Project", testTask.getTaskToDo());
        assertEquals(false, testTask.isCompleted());
    }

    @Test
    public void testGetTaskToDo() {
        assertEquals("CPSC 210 Project", testTask.getTaskToDo());
    }

    @Test
    public void testGetIsCompleted() {
        assertEquals(false, testTask.isCompleted());
        testTask.setCompleted();
        testTask.setCompleted();

        assertEquals(true, testTask.isCompleted());
    }

}