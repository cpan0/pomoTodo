package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for Timer
public class TimerTest {
    private Timer timer;

    @BeforeEach
    public void runBefore() {
        timer = new Timer();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, timer.getMinutes());
        assertEquals(0, timer.getSeconds());
        assertFalse(timer.isTimerOn());
    }

    @Test
    public void testIsTimerOn() {
        assertFalse(timer.isTimerOn());

        timer.setTimer(1);
        assertTrue(timer.isTimerOn());
    }

    @Test
    public void testSetTimerOff() {
        timer.setTimer(1);
        assertEquals(1, timer.getMinutes());
        assertEquals(0, timer.getSeconds());
        assertTrue(timer.isTimerOn());

        timer.setTimerOff();
        assertEquals(0, timer.getMinutes());
        assertEquals(0, timer.getSeconds());
        assertFalse(timer.isTimerOn());

    }

    @Test
    public void testRunTimerOnce() {
        timer.setTimer(1);

        timer.tickTimer();
        assertEquals(0, timer.getMinutes());
        assertEquals(59, timer.getSeconds());
        assertTrue(timer.isTimerOn());
    }

    @Test
    public void testRunTimerMultiple() {
        timer.setTimer(25);

        timer.tickTimer();
        assertEquals(24, timer.getMinutes());
        assertEquals(59, timer.getSeconds());
        assertTrue(timer.isTimerOn());

        timer.tickTimer();
        assertEquals(24, timer.getMinutes());
        assertEquals(58, timer.getSeconds());
        assertTrue(timer.isTimerOn());

    }

    @Test
    public void testRunTimer1MinToEnd() {
        timer.setTimer(1);
        int totalSeconds = timer.getMinutes() * 60;

        for (int sec = totalSeconds; sec > 0; sec = totalSeconds--) {
            timer.tickTimer();
        }

        assertEquals(0, timer.getSeconds());
        assertEquals(0, timer.getMinutes());
        timer.tickTimer();

        assertEquals(0, timer.getSeconds());
        assertEquals(0, timer.getMinutes());
        assertFalse(timer.isTimerOn());
    }

    @Test
    public void testRunTimer5MinToEnd() {
        timer.setTimer(5);
        int totalSeconds = timer.getMinutes() * 60;

        for (int sec = totalSeconds; sec > 0; sec = totalSeconds--) {
            timer.tickTimer();
        }

        assertEquals(0, timer.getSeconds());
        assertEquals(0, timer.getMinutes());
        timer.tickTimer();

        assertEquals(0, timer.getSeconds());
        assertEquals(0, timer.getMinutes());
        assertFalse(timer.isTimerOn());
    }
}
