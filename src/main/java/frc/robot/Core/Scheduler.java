package frc.robot.Core;

import java.util.Arrays;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Util.*;

/**
 * The Scheduler class allows for scheduling and execution of tasks and functions
 * at specified times or intervals, which is especially useful during autonomous
 * robot operations.
 */
class ScheduleItem {
    public Lambda executable;   // The function to execute.
    public double executeTime;  // The scheduled time for execution.

    public ScheduleItem(Lambda executable, double executeTime) {
        this.executable = executable;
        this.executeTime = executeTime;
    }
}

public class Scheduler {
    private ScheduleItem[] items = new ScheduleItem[] {}; // Array to hold scheduled items.

    /**
     * Registers a Tickable object to be called every tick.
     * @param tickable The Tickable instance to run every tick.
     * @return A Lambda to remove the tickable from the event loop.
     */
    public Lambda registerTick(Tickable tickable) {
        double[] lastTime = new double[] { Timer.getFPGATimestamp() };
        return setInterval(() -> {
            tickable.tick(Timer.getFPGATimestamp() - lastTime[0]);
            lastTime[0] = Timer.getFPGATimestamp();
        }, 0);
    }

    /**
     * Schedules a function to be called repeatedly at a fixed delay.
     * @param callBack The function to call.
     * @param delay The interval between calls.
     * @return A Lambda to cancel the interval.
     */
    public Lambda setInterval(Lambda callBack, double delay) {
        var item = new ScheduleItem(null, delay + Timer.getFPGATimestamp());
        Container<Lambda> interval = new Container<Lambda>(null);
        interval.val = () -> {
            callBack.run();
            item.executeTime = Timer.getFPGATimestamp() + delay;
        };
        interval.val.run(); // Run the interval once immediately.

        item.executable = interval.val;
        items = Arrays.copyOf(items, items.length + 1); // Extend the array for the new item.
        items[items.length - 1] = item;

        return () -> {
            item.executable = () -> {}; // Empty runnable to effectively remove the item.
        };
    }

    /**
     * Schedules a function to be called after a delay.
     * @param callBack The function to call after the delay.
     * @param delay The time to wait before calling the function.
     * @return A CancelablePromise to cancel the timeout.
     */
    public CancelablePromise setTimeout(Lambda callBack, double delay) {
        Container<CancelablePromise> prom = new Container<CancelablePromise>(null);

        items = Arrays.copyOf(items, items.length + 1); // Extend the array for the new item.
        var item = new ScheduleItem(() -> {
            callBack.run();
            prom.val.resolve();
        }, Timer.getFPGATimestamp() + delay);

        prom.val = new CancelablePromise(() -> {
            item.executable = () -> {}; // Empty runnable to effectively remove the item.
        });

        items[items.length - 1] = item;
        return prom.val;
    }

    /**
     * Processes all scheduled items and executes those whose time has come.
     */
    public void tick() {
        boolean runCleanUp = false;
        double currentTime = Timer.getFPGATimestamp();
        for (var e : items.clone()) {
            if (currentTime >= e.executeTime) {
                e.executable.run();
                if (currentTime >= e.executeTime)
                    runCleanUp = true;
            }
        }
        if (runCleanUp) {
            // Filter out executed items and create a new list of pending items.
            var newItems = Arrays.stream(items).filter((e) -> {
                return currentTime < e.executeTime;
            }).toList();
            items = new ScheduleItem[newItems.size()];
            int index = 0;
            for (var item : newItems) {
                items[index] = item;
                index++;
            }
        }
    }

    /**
     * Starts and manages the lifecycle of a ScheduledCommand.
     * @param command The command to execute.
     * @return A CancelablePromise that can cancel the command.
     */
    CancelablePromise runCommand(ScheduledCommand command) {
        command.start();
        var checkAndStop = new Container<Lambda>(null);
        var prom = new CancelablePromise(() -> {
            command.complete();
            checkAndStop.val.run();
        });
        var cancel = registerTick((double dT) -> {
            command.tick(dT);
            checkAndStop.val.run();
        });
        checkAndStop.val = () -> {
            if (command.isComplete()) {
                cancel.run(); // Cancel the tick registration.
                command.end(); // End the command.
                prom.resolve(); // Resolve the promise.
            }
        };
        return prom;
    }

    /**
     * Clears all scheduled items from the scheduler.
     */
    public void clear() {
        items = new ScheduleItem[0];
    }
}
