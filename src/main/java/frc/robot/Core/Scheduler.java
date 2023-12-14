package frc.robot.Core;

import java.util.LinkedList;

import frc.robot.Robot;
import frc.robot.Util.Phase;
import frc.robot.Util.Promise;
import frc.robot.Util.SimplePromise;

enum Clear {
    TaskEnd,
    Never
}

class Entry {
    Schedulable task;
    Clear clear;
    SimplePromise promise;

    public Entry(Schedulable task, Clear clear) {
        this.task = task;
        this.clear = clear;
        this.promise = new SimplePromise();
    }
}

public class Scheduler {
    static Scheduler instance = new Scheduler();

    private Scheduler() {
    }

    public static Scheduler getCore() {
        return instance;
    }

    private static LinkedList<Entry> schedulables = new LinkedList<>();

    public static Promise runTask(Schedulable task) {
        var entry = new Entry(task,
                Robot.getPhase() == Phase.Init ? Clear.TaskEnd : Clear.Never);
        schedulables.add(entry);
        return entry.promise;
    }

    public void clear() {
        for (Entry item : schedulables) {
            schedulables.remove(item);
        }
    }

    // protected methods

    public void tick(double dTime) {
        for (var item : schedulables) {
            item.task.tick(dTime);
            if (item.task.isComplete()) {
                item.task.end();
                item.promise.resolve();
                schedulables.remove(item);
            }
        }
    }
}
