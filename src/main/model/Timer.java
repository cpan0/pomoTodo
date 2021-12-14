package model;

// Represents a Timer
public class Timer {
    private int minutes;
    private int seconds;
    private Boolean isOn;

    // Constructor
    // EFFECTS: time has been set to 0 minutes and 0 seconds and timer is off
    public Timer() {
        minutes = 0;
        seconds = 0;
        isOn = false;
    }

    // EFFECTS: Returns the minutes
    public int getMinutes() {
        return minutes;
    }

    // EFFECTS: Returns the seconds
    public int getSeconds() {
        return seconds;
    }

    // EFFECTS: Return true if the timer is on, false otherwise
    public Boolean isTimerOn() {
        return this.isOn;
    }

    // REQUIRES: min > 0
    // MODIFIES: this
    // EFFECTS: timer is set to the given time and timer is on
    public void setTimer(int time) {
        this.minutes = time;
        this.isOn = true;
    }

    // MODIFIES: this
    // EFFECTS: timer is off
    public void setTimerOff() {
        this.minutes = 0;
        this.seconds = 0;
        this.isOn = false;
    }

    // MODIFIES: this
    // EFFECTS: time in timer is decreased by 1 second
    public void tickTimer() {
        seconds--;
        if (seconds < 0) {
            seconds = 59;
            minutes--;
            if (minutes < 0) {
                setTimerOff();
            }
        }
    }
}

