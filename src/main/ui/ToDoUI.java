package ui;

import model.Task;
import model.ToDoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.PomoToDoUI.TOMATO_IMG;

// Represents application's to-do list panel UI
public class ToDoUI extends JPanel {
    private JPanel headerPanel;
    private JPanel buttonPanel;
    private TasksUI toDoListPanel;
    private ToDoList tdl;

    // Constructs a to-do panel UI with a header, buttons and a task panel
    public ToDoUI(ToDoList toDoList) {
        this.tdl = toDoList;

        BoxLayout l = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(l);

        addHeaderPanel();
        addButtonPanel();

        toDoListPanel = new TasksUI(tdl);
        add(toDoListPanel);

        add(Box.createVerticalGlue());
    }

    // MODIFIES: this
    // EFFECTS: create a header panel
    private void addHeaderPanel() {
        headerPanel = new JPanel();
        BoxLayout l = new BoxLayout(headerPanel, BoxLayout.LINE_AXIS);
        headerPanel.setLayout(l);
        JLabel pomoLabel = new JLabel(TOMATO_IMG);
        headerPanel.add(pomoLabel);

        JLabel nameLabel = new JLabel("PomoToDo");
        nameLabel.setFont(new Font("Futura", Font.PLAIN, 30));
        headerPanel.add(nameLabel);

        add(headerPanel);
    }

    // MODIFIES: this
    // EFFECTS: adds a button panel
    private void addButtonPanel() {
        buttonPanel = new JPanel();
        BoxLayout l = new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS);
        buttonPanel.setLayout(l);

        buttonPanel.add(addButton());
        buttonPanel.add(Box.createHorizontalGlue());

        JLabel showLabel = new JLabel("Show: ");
        buttonPanel.add(showLabel);

        buttonPanel.add(showAllButton());
        buttonPanel.add(showToDoButton());
        buttonPanel.add(showCompletedButton());

        add(buttonPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }

    // REQUIRES: tdl.getSize() < 10
    // MODIFIES: this
    // EFFECTS: creates a button that adds a task to the to-do list if list size < 10
    private JButton addButton() {
        JButton addButton = new JButton("Add...");

        addButton.setActionCommand("add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tdl.getSize() < 10) {
                    Object toDoObj = JOptionPane.showInputDialog(null, "What would you like to add to the to-do list?",
                            "Add...", JOptionPane.QUESTION_MESSAGE, TOMATO_IMG, null, "");
                    if (toDoObj != null) {
                        String toDo = toDoObj.toString();
                        Task t = new Task(toDo);
                        tdl.addTask(t);
                        toDoListPanel.showTask(t);
                    }
                    refresh();
                } else {
                    JOptionPane.showMessageDialog(null, "Oops! There are no more spaces left.");
                }
            }
        });

        return addButton;
    }

    // MODIFIES: this
    // EFFECTS: creates a button that shows all tasks in the list
    private JButton showAllButton() {
        JButton showAllButton = new JButton("All");

        showAllButton.setActionCommand("show all");
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toDoListPanel.removeAll();
                toDoListPanel.showTasks(tdl);

                refresh();
            }
        });

        return showAllButton;
    }

    // MODIFIES: this
    // EFFECTS: creates a button that shows all to-do tasks in the list
    private JButton showToDoButton() {
        JButton showToDoButton = new JButton("ToDo");

        showToDoButton.setActionCommand("show to-dos");
        showToDoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toDoListPanel.removeAll();
                ToDoList filtered = tdl.filterByInCompletion();
                toDoListPanel.showTasks(filtered);
                refresh();
            }
        });

        return showToDoButton;
    }

    // MODIFIES: this
    // EFFECTS: creates a button that shows all incomplete tasks in the list
    private JButton showCompletedButton() {
        JButton showIncompleteButton = new JButton("Completed");

        showIncompleteButton.setActionCommand("show completed");
        showIncompleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toDoListPanel.removeAll();
                ToDoList filtered = tdl.filterByCompletion();
                toDoListPanel.showTasks(filtered);
                refresh();
            }
        });

        return showIncompleteButton;
    }

    // EFFECTS: updates this panel
    private void refresh() {
        this.revalidate();
        this.repaint();
    }
}
