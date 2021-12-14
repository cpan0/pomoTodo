package model;

import com.sun.corba.se.impl.oa.toa.TOA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Tests for ToDoLIst
public class ToDoListTest {
    private Task a;
    private Task b;
    private Task c;
    private ToDoList list;

    @BeforeEach
    public void runBefore() {
        a = new Task("eat food");
        b = new Task("sleep");
        c = new Task("play");

        list = new ToDoList("test PomoList");

    }

    @Test
    public void testConstructor() {
        list.addTask(a);
        list.addTask(b);

        assertTrue(list.contains(a));
        assertTrue(list.contains(b));
        assertEquals(2, list.getSize());
    }

    @Test
    public void testAdd() {
        list.addTask(a);
        assertTrue(list.contains(a));

        list.addTask(b);
        assertTrue(list.contains(b));
        assertTrue(list.contains(a));
        assertFalse(list.contains(c));
    }

    @Test
    public void testDeleteOne() {
        list.addTask(a);
        list.deleteTask(a);
        assertEquals(0, list.getSize());

    }

    @Test
    public void testDeleteLots() {
        list.addTask(a);
        list.addTask(b);
        list.addTask(c);
        list.deleteTask(b);
        list.deleteTask(a);
        assertEquals(1, list.getSize());
        assertFalse(list.contains(b));
        assertFalse(list.contains(a));
        assertTrue(list.contains(c));

    }

    @Test
    public void testGetSize() {
        assertEquals(0, list.getSize());

        list.addTask(a);
        assertEquals(1, list.getSize());

        list.addTask(b);
        list.addTask(c);
        assertEquals(3, list.getSize());

    }

    @Test
    public void testContains() {
        list.addTask(a);
        assertEquals(1, list.getSize());
        assertTrue(list.contains(a));

        list.addTask(b);
        assertEquals(2, list.getSize());
        assertTrue(list.contains(b));

    }

    @Test
    public void testGetName() {
        list.addTask(a);
        list.updateTask(a);

        Task completedTask = list.getTask(0);
        assertTrue(completedTask.isCompleted());
    }


    @Test
    public void testGetTask() {
        list.addTask(a);
        assertEquals(a, list.getTask(0));
        list.addTask(b);
        assertEquals(b, list.getTask(1));
        list.addTask(c);
        assertEquals(c, list.getTask(2));
    }

    @Test
    public void testGetToDoList() {
        ArrayList test = new ArrayList();
        assertEquals(test, list.getToDoList());

        test.add(a);
        test.add(b);
        test.add(c);

        list.addTask(a);
        list.addTask(b);
        list.addTask(c);

        assertEquals(test, list.getToDoList());
    }


    @Test
    public void testUpdateTask() {
        list.addTask(a);
        list.updateTask(a);

        Task completedTask = list.getTask(0);
        assertTrue(completedTask.isCompleted());
    }

    @Test
    public void testFilterByCompletion() {
        list.addTask(a);
        list.addTask(b);
        list.addTask(c);

        ToDoList emptyFiltered = list.filterByCompletion();
        assertEquals(0, emptyFiltered.getSize());
        list.updateTask(b);
        ToDoList filtered = list.filterByCompletion();
        assertEquals(1, filtered.getSize());

    }

    @Test
    public void testFilterByInCompletion() {
        list.addTask(a);
        list.addTask(b);
        list.addTask(c);

        ToDoList filtered = list.filterByInCompletion();
        assertEquals(3, filtered.getSize());

        Task d = new Task("nap");
        d.setCompleted();
        list.addTask(d);

        ToDoList newFiltered = list.filterByInCompletion();
        assertEquals(3, filtered.getSize());



    }
}

