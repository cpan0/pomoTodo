package ui;

import model.EventLog;
import model.Task;
import model.Timer;
import model.ToDoList;
import persistence.JsonWriter;
import persistence.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// PomoToDoApp application
// This PomoToDoApp references code from the AccountNoRobust - TellerApp
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
public class PomoToDoApp {
    private static final String JSON_STORE = "./data/todolist.json";

    private ToDoList pomoToDo;
    private Scanner input;
    private String command = null;
    private Timer timer;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the PomoToDo application
    public PomoToDoApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        pomoToDo = new ToDoList("PomoList");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runPomoToDoApp();
    }

    // MODIFIES: this
    // EFFECTS: Processes user's input
    private void runPomoToDoApp() {

        init();

        while (true) {

            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                break;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\n Great work today!");
        printLog();
    }

    private void printLog() {
        LogPrinter lp = new LogPrinter(EventLog.getInstance());
        lp.outputLog();
    }

    // MODIFIES: this
    // EFFECTS: initializes the pomoToDo list
    private void init() {
        this.pomoToDo = new ToDoList("PomoList");
        this.input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\n Select from:");
        System.out.println("\t v -> view todo and completed lists");
        System.out.println("\t + -> add a todo task");
        System.out.println("\t c -> complete a todo task");
        System.out.println("\t - -> delete task from todo/completed list");
        System.out.println("\t t -> set a timer");
        System.out.println("\t s -> save todo list to file");
        System.out.println("\t l -> load todo list from file");
        System.out.println("\t q -> quit");
    }

    // MODIFIES: this
    // EFFECTS: Processes user command
    private void processCommand(String command) {
        if (command.equals("v")) {
            doView();
        } else if (command.equals("+")) {
            doAdd();
        } else if (command.equals("c")) {
            displayCompleteOptions();
        } else if (command.equals("-")) {
            chooseDelete();
        } else if (command.equals("t")) {
            selectTimer();
        } else if (command.equals("s")) {
            savePomoToDo();
        } else if (command.equals("l")) {
            loadPomoToDo();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: Show all the to-do tasks and completed tasks
    private void doView() {
        System.out.print("\n To-Do:");
        ToDoList tasksToDo = pomoToDo.filterByInCompletion();
        for (Task t : tasksToDo.getToDoList()) {
            System.out.print("\n\t - " + t.getTaskToDo());
        }

        System.out.print("\n");

        System.out.print("\n Completed:");
        ToDoList completedTasks = pomoToDo.filterByCompletion();
        for (Task t : completedTasks.getToDoList()) {
            System.out.print("\n\t - " + t.getTaskToDo());
        }
        System.out.print("\n");

    }

    // MODIFIES: this
    // EFFECTS: Adds a task
    private void doAdd() {
        System.out.println("What would you like to add to the to-do list?");
        Task task = new Task(input.nextLine());
        pomoToDo.addTask(task);
        ToDoList tasksToDo = pomoToDo.filterByInCompletion();
        System.out.println("You have " + tasksToDo.getSize() + " tasks to do.");
    }

    // EFFECTS: Displays all in-completed tasks that can be completed from the list
    // and process user's complete command
    private void displayCompleteOptions() {
        ToDoList list = pomoToDo.filterByInCompletion();
        if (list.getSize() != 0) {
            System.out.println("Great Job! Which task did you finish? Select the corresponding task number:");
            int index = 0;
            for (Task t : list.getToDoList()) {
                System.out.println("\t " + (index + 1) + " " + t.getTaskToDo());
                index++;
            }
            doCompleteOptions(list);
        } else {
            System.out.println("You don't have any tasks in the list!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Completes a selected task from the list
    private void doCompleteOptions(ToDoList list) {
        try {
            int selection = Integer.parseInt(input.nextLine());

            if (selection <= list.getSize() && selection > 0) {
                Task completingTask = list.getTask(selection - 1);
                if (completingTask != null) {
                    System.out.println("Finished " + completingTask.getTaskToDo() + "? (yes/no)");
                    String selectComplete = input.nextLine();
                    selectComplete = selectComplete.toLowerCase();
                    if (selectComplete.equals("yes")) {
                        pomoToDo.updateTask(completingTask);
                        System.out.println(completingTask.getTaskToDo() + " was completed.");
                    } else if (selectComplete.equals("no")) {
                        System.out.println("Keep working! You got this!");
                    } else {
                        System.out.println("Selection not valid...");
                    }
                }
            } else {
                System.out.println("Selection not valid...");
            }
        } catch (NumberFormatException e) {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes user's selection of which list to delete task from
    private void chooseDelete() {
        System.out.println("Would you like to delete a task from the to-do list or the completed list?");
        System.out.println("\t t -> To-do list");
        System.out.println("\t c -> Completed list");
        String selectList = input.nextLine();
        selectList = selectList.toLowerCase();
        if (selectList.equals("t")) {
            ToDoList tasksToSelect = pomoToDo.filterByInCompletion();
            displayDeleteOptions(tasksToSelect);
        } else if (selectList.equals("c")) {
            ToDoList tasksToSelect = pomoToDo.filterByCompletion();
            displayDeleteOptions(tasksToSelect);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays all tasks that can be deleted from the list
    // and process user's delete command
    private void displayDeleteOptions(ToDoList list) {
        if (list.getSize() != 0) {
            System.out.println("What would you like to delete from the list? Select the corresponding number:");
            int index = 0;
            for (Task t : list.getToDoList()) {
                System.out.println("\t " + (index + 1) + " " + t.getTaskToDo());
                index++;
            }
            doDeleteOptions(list);
        } else {
            System.out.println("You don't have any tasks in the list!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Deletes a selected task from the list
    private void doDeleteOptions(ToDoList list) {
        try {
            int selection = Integer.parseInt(input.nextLine());

            if (selection <= list.getSize() && selection > 0) {
                Task deletingTask = list.getTask(selection - 1);
                if (deletingTask != null) {
                    System.out.println("Deleting " + deletingTask.getTaskToDo() + "? (yes/no)");
                    String selectDelete = input.nextLine();
                    selectDelete = selectDelete.toLowerCase();
                    if (selectDelete.equals("yes")) {
                        pomoToDo.deleteTask(deletingTask);
                        System.out.println(deletingTask.getTaskToDo() + " was deleted.");
                    } else if (selectDelete.equals("no")) {
                        System.out.println("Changed your mind? No Worries!");
                    } else {
                        System.out.println("Selection not valid...");
                    }
                }
            } else {
                System.out.println("Selection not valid...");
            }
        } catch (NumberFormatException e) {
            System.out.println("Selection not valid...");
        }
    }


    // EFFECTS: Processes user's selection of timer
    // and start the timer
    private void selectTimer() {
        System.out.println("Want to use the Pomodoro Timer?");
        System.out.println("\t Select the timer you want to set:");
        System.out.println("\t 1 -> PomoTime: 25 minutes");
        System.out.println("\t 2 -> Short Break: 5 minutes");
        System.out.println("\t 3 -> Long Break: 10 minutes");
        String selectTime = input.nextLine();
        selectTime = selectTime.toLowerCase();
        timer = new Timer();
        if (selectTime.equals("1")) {
            timer.setTimer(25);
            runTimer();
        } else if (selectTime.equals("2")) {
            timer.setTimer(5);
            runTimer();
        } else if (selectTime.equals("3")) {
            timer.setTimer(10);
            runTimer();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Runs the selected timer
    // This runTimer references code from JavaConcept of The Day - Thread.sleep() Method In Java
    // Link: https://javaconceptoftheday.com/thread-sleep-method-java/
    private void runTimer() {
        int totalSeconds = timer.getMinutes() * 60;
        for (int sec = totalSeconds; sec > 0; sec = totalSeconds--) {
            if (timer.getSeconds() < 10) {
                System.out.println(timer.getMinutes() + " : 0" + timer.getSeconds());
            } else {
                System.out.println(timer.getMinutes() + " : " + timer.getSeconds());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Oops! Timer has encountered an error.");
            }
            timer.tickTimer();
        }
        System.out.println("Time's up!");
    }

    // EFFECTS: saves the PomoToDo to file
    private void savePomoToDo() {
        try {
            jsonWriter.open();
            jsonWriter.write(pomoToDo);
            jsonWriter.close();
            System.out.println("Saved PomoToDo to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads PomoToDo from file
    private void loadPomoToDo() {
        try {
            pomoToDo = jsonReader.read();
            System.out.println("Loaded PomoToDo from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
