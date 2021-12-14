package persistence;

import model.ToDoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests for JsonReader
public class JsonReaderTest extends JsonTest{

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ToDoList tdl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyToDoList.json");
        try {
            ToDoList tdl = reader.read();
            assertEquals(0, tdl.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralToDoList.json");
        try {
            ToDoList tdl = reader.read();
            assertEquals(3, tdl.getSize());
            checkTask(tdl.getTask(0), "do CPSC project phase 2", false);
            checkTask(tdl.getTask(1), "do WebWork", false);
            checkTask(tdl.getTask(2), "do CPSC project phase 1", true);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }
}
