package persistence;

import model.Task;
import model.ToDoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests for JsonWriter
public class JsonWriterTest extends JsonTest{

    @Test
    public void testWriterInvalidFile() {
        try {
            ToDoList tdl = new ToDoList("test PomoList");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyToDoList() {
        try {
            ToDoList tdl = new ToDoList("test PomoList");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyToDoList.json");
            writer.open();
            writer.write(tdl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyToDoList.json");
            tdl = reader.read();
            assertEquals(0, tdl.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralToDoList() {
        try {
            ToDoList tdl = new ToDoList("test PomoList");
            tdl.addTask(new Task("do CPSC project phase 2"));
            tdl.addTask(new Task("do WebWork"));
            tdl.addTask(new Task("do CPSC project phase 1"));
            tdl.updateTask(tdl.getTask(2));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralToDoList.json");
            writer.open();
            writer.write(tdl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralToDoList.json");
            tdl = reader.read();
            assertEquals(3, tdl.getSize());
            checkTask(tdl.getTask(0), "do CPSC project phase 2", false);
            checkTask(tdl.getTask(1), "do WebWork", false);
            checkTask(tdl.getTask(2), "do CPSC project phase 1", true);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
