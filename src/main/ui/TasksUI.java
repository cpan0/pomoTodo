package ui;

import model.Task;
import model.ToDoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static ui.PomoToDoUI.TOMATO_IMG;

// Represents application's tasks panel UI
public class TasksUI extends JPanel {
    private static final String TOMATO_OUTLINE = "./data/tomatoOutline.png";

    private ToDoList toDoList;
    private JLabel imageLabel;
    private JLabel taskLabel;

    // Constructs a tasks panel UI
    public TasksUI(ToDoList tdl) {

        GridLayout l = new GridLayout(10, 1);
        setLayout(l);

        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));

        toDoList = tdl;
        showTasks(toDoList);
    }

    // MODIFIES: this
    // EFFECTS: shows each task in the to-do list
    public void showTasks(ToDoList tdl) {
        for (Task t : tdl.getToDoList()) {
            showTask(t);
            refresh();
        }
    }

    // MODIFIES: this
    // EFFECTS: helper method that shows a task
    public void showTask(Task t) {
        JPanel taskPanel = new JPanel();
        Boolean isDone = t.isCompleted();

        BoxLayout l = new BoxLayout(taskPanel, BoxLayout.LINE_AXIS);
        taskPanel.setLayout(l);

        if (!isDone) {
            imageLabel = new JLabel(new ImageIcon(TOMATO_OUTLINE));
        } else {
            imageLabel = new JLabel(TOMATO_IMG);
        }

        taskPanel.add(imageLabel);
        taskPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        taskLabel = new JLabel(t.getTaskToDo());

        taskPanel.add(taskLabel);
        taskPanel.setAlignmentX(LEFT_ALIGNMENT);
        taskPanel.add(Box.createHorizontalGlue());

        popUp(taskPanel, t);

        add(taskPanel);
    }

    // MODIFIES: this
    // EFFECTS: returns a menu item that completes a task
    private JMenuItem completeButton(Task t) {
        JMenuItem button = new JMenuItem("Complete");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t.setCompleted();
                removeAll();
                showTasks(toDoList);
                refresh();
            }
        });

        return button;
    }

    // MODIFIES: this
    // EFFECTS: returns a menu item that deletes a task
    private JMenuItem deleteButton(Task t) {
        JMenuItem button = new JMenuItem("Delete");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toDoList.deleteTask(t);
                removeAll();
                showTasks(toDoList);
                refresh();
            }
        });

        return button;
    }

    // MODIFIES: this
    // EFFECTS: creates a pop-up menu that handles actions on a task
    private void popUp(JPanel p, Task t) {
        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JPopupMenu select = new JPopupMenu();
                select.add(completeButton(t));
                select.add(deleteButton(t));
                select.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    // EFFECTS: updates this panel
    private void refresh() {
        this.revalidate();
        this.repaint();
    }
}
