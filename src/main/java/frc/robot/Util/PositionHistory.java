package frc.robot.Util;

import java.util.ArrayList;

/**
 * Represents a single entry in the position history.
 */
class PositionHistoryEntry {
    double time; // The timestamp at which the position was recorded.
    double pos;  // The position at the recorded time.

    /**
     * Constructs a new PositionHistoryEntry.
     *
     * @param time The timestamp of the entry.
     * @param pos  The position of the entry.
     */
    public PositionHistoryEntry(double time, double pos) {
        this.time = time;
        this.pos = pos;
    }
}

/**
 * Maintains a history of positions over time to calculate speed and acceleration.
 */
public class PositionHistory {
    // List to keep track of the position entries.
    ArrayList<PositionHistoryEntry> pos = new ArrayList<>();

    /**
     * Adds a new position to the history.
     *
     * @param newPos The new position to be added.
     */
    public void addPos(double newPos) {
        // Add a new position entry with the current time and the new position.
        pos.add(new PositionHistoryEntry(Time.getTimeSincePower(), newPos));
        // Limit the history size to the most recent 5 entries.
        if (pos.size() > 5)
            pos.remove(0);
    }

    /**
     * Gets the most recent position.
     *
     * @return The latest position recorded.
     */
    public double getPos() {
        return pos.get(pos.size() - 1).pos;
    }

    /**
     * Calculates the speed between two historical positions.
     *
     * @param i The index of the newer position entry.
     * @return  The speed calculated over the interval between the two positions.
     */
    private double getSpeed(int i) {
        if (i < 1 || i >= pos.size())
            throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for position history.");
        var current = pos.get(i);
        var prev = pos.get(i - 1);
        // Speed is calculated as the difference in position over the difference in time.
        return (current.pos - prev.pos) / (current.time - prev.time);
    }

    /**
     * Gets the most recent speed.
     *
     * @return The latest speed calculated from the position history.
     */
    public double getSpeed() {
        if (pos.size() <= 1)
            return 0; // Not enough data to calculate speed.
        return getSpeed(pos.size() - 1);
    }

    /**
     * Calculates the acceleration based on the speed changes between the most recent positions.
     *
     * @return The latest acceleration calculated from the position history.
     */
    public double getAcceleration() {
        if (pos.size() <= 2)
            return 0; // Not enough data to calculate acceleration.
        var current = pos.size() - 1;
        var prev = pos.size() - 2;
        // Acceleration is calculated as the difference in speed over the difference in time.
        return (getSpeed(current) - getSpeed(prev)) / (pos.get(current).time - pos.get(prev).time);
    }

    /**
     * Resets the position history, clearing all entries.
     */
    public void reset() {
        pos.clear();
    }
}
