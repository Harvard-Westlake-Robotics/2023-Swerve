package frc.robot.Core;

import java.util.LinkedList;

import frc.robot.Robot;
import frc.robot.Util.Lambda;
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
                Robot.getPhase() == Phase.Init ? Clear.Never : Clear.TaskEnd);
        schedulables.add(entry);
        return entry.promise;
    }

    public static Lambda registerTick(Lambda everyTick) {
        var task = new Schedulable() {
            @Override
            protected void start() {

            }

            @Override
            protected void tick(double dTime) {
                everyTick.run();
            }

            @Override
            protected void end() {

            }
        };
        return () -> {
            task.complete();
        };
    }

    public void onTaskEnd() {
        for (int i = 0; i < schedulables.size(); i++) {
            if (schedulables.get(i).clear == Clear.TaskEnd) {
                schedulables.remove(i);
                i--;
            }
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
