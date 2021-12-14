package ui;

import model.Event;
import model.EventLog;

// Represents a log printer
// This Event references code from the AlarmSystem - LogPrinter
// Link: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
public class LogPrinter {
    private EventLog el;

    // Constructs a log printer
    public LogPrinter(EventLog el) {
        this.el = el;
    }

    // EFFECTS: prints the event log
    public void outputLog() {
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }


}
