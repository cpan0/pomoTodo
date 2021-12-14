package ui;

import model.Event;
import model.EventLog;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents application's main window frame UI and start menu panel UI
public class PomoToDoUI extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 1050;
    public static final String TOMATO = "./data/tomato.png";
    public static final ImageIcon TOMATO_IMG = new ImageIcon(TOMATO);
    private static final String JSON_STORE = "./data/todolist.json";

    private Container container;
    private CardLayout cardLayout;
    private JPanel startMenuPanel;
    private ToDoUI toDoListPanel;
    private ToDoList tdl;
    private JLabel logoLabel;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Constructs a PomoToDo UI with a start menu
    public PomoToDoUI() {
        super("PomoToDo");
        setVisible(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        container = getContentPane();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        tdl = new ToDoList("PomoList");
        toDoListPanel = new ToDoUI(tdl);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        startMenu();

        pack();
        handleClose();
    }

    // MODIFIES: this
    // EFFECTS: a helper method that processes window closing event
    private void handleClose() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int input = JOptionPane.showConfirmDialog(null, "Save?", "Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, TOMATO_IMG);
                if (input == JOptionPane.OK_OPTION) {
                    savePomoToDo();
                }
                try {
                    printLog();
                } catch (Exception exception) {
                    System.out.println("Oops! An error has occurred");
                }
                System.exit(0);
            }
        });
    }

    public void printLog() {
        LogPrinter lp = new LogPrinter(EventLog.getInstance());
        lp.outputLog();
    }


    // MODIFIES: this
    // EFFECTS: add a start menu panel to container
    private void startMenu() {
        startMenuPanel = new JPanel();
        startMenuPanel.setLayout(new GridBagLayout());

        startMenuPanel.add(startPanel());

        container.add(startMenuPanel, "1");
    }

    // EFFECTS: returns a start panel with logo and buttons
    private JPanel startPanel() {
        JPanel panel = new JPanel();
        GridBagLayout l = new GridBagLayout();
        panel.setLayout(l);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;

        logoLabel = new JLabel(TOMATO_IMG);
        c.gridy = 0;
        panel.add(logoLabel, c);

        JLabel nameLabel = new JLabel("PomoToDo");
        nameLabel.setFont(new Font("Futura", Font.PLAIN, 30));
        c.gridy = 1;
        panel.add(nameLabel, c);

        c.gridy = 2;
        panel.add(createPomoButton(), c);

        c.gridy = 3;
        panel.add(loadPomoButton(), c);

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: returns a button that creates a new to-do list
    public JButton createPomoButton() {
        JButton createButton = new JButton("Create new...");

        createButton.setActionCommand("create");
        createButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                container.add(toDoListPanel, "2");
                cardLayout.show(container, "2");
            }
        });

        return createButton;
    }

    // MODIFIES: this
    // EFFECTS: returns a button that loads a saved to-do list
    public JButton loadPomoButton() {
        JButton loadButton = new JButton("Load saved...");

        loadButton.setActionCommand("load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tdl = jsonReader.read();
                    toDoListPanel = new ToDoUI(tdl);
                    container.add(toDoListPanel, "2");
                    cardLayout.show(container, "2");
                    JOptionPane.showMessageDialog(null, "Loaded PomoToDo from " + JSON_STORE, "Message",
                            JOptionPane.INFORMATION_MESSAGE, TOMATO_IMG);
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);
                }
            }
        });

        return loadButton;
    }

    // EFFECTS: saves the PomoToDo to file
    public void savePomoToDo() {
        try {
            jsonWriter.open();
            jsonWriter.write(tdl);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved PomoToDo to " + JSON_STORE, "Message",
                    JOptionPane.INFORMATION_MESSAGE, TOMATO_IMG);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: runs the PomoToDo UI
    public static void main(String[] args) {
        new PomoToDoUI();
    }
}
