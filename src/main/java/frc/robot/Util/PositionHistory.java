package frc.robot.Util;

import java.util.ArrayList;

class PositionHistoryEntry {
    double time;
    double pos;

    public PositionHistoryEntry(double time, double pos) {
        this.time = time;
        this.pos = pos;
    }
}

public class PositionHistory {
    ArrayList<PositionHistoryEntry> pos = new ArrayList<>();

    public void addPos(double newPos) {
        pos.add(new PositionHistoryEntry(Time.getTimeSincePower(), newPos));
        if (pos.size() > 5)
            pos.remove(0);
    }

    public double getPos() {
        return pos.get(pos.size() - 1).pos;
    }

    private double getSpeed(int i) {
        if (i < 1 || i >= pos.size())
            throw new IndexOutOfBoundsException(i);
        var current = pos.get(i);
        var prev = pos.get(i - 1);
        return (current.pos - prev.pos) / (current.time - prev.time);
    }

    public double getSpeed() {
        if (pos.size() <= 1)
            return 0;
        return getSpeed(pos.size() - 1);
    }

    public double getAcceleration() {
        if (pos.size() <= 2)
            return 0;
        var current = pos.size() - 1;
        var prev = pos.size() - 2;
        return (getSpeed(current) - getSpeed(prev)) / (pos.get(current).time - pos.get(prev).time);
    }

    public void reset() {
        pos = new ArrayList<>();
    }
}
