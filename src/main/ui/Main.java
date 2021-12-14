package ui;

import java.io.FileNotFoundException;

// Creates an instance of the PomoToDoApp class
public class Main {
    public static void main(String[] args)  {
        try {
            new PomoToDoApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
