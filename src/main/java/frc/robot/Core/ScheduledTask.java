package frc.robot.Core;

import frc.robot.Util.Lambda;

/**
 * ScheduledTask is a class that represents a task to be executed by the robot's scheduler.
 * It can have custom actions defined for when the task starts, during each scheduler tick, and when it ends.
 */
public class ScheduledTask extends ScheduledCommand {
    Lambda onStartF; // Function to run when the task starts.
    Lambda onTickF;  // Function to run on each scheduler tick.
    Lambda onEndF;   // Function to run when the task ends.

    /**
     * Constructor for creating a new ScheduledTask with specified start, tick, and end actions.
     * @param onStartF Function to run at the start of the task.
     * @param onTickF Function to run on each tick during the task.
     * @param onEndF Function to run when the task ends.
     */
    public ScheduledTask(Lambda onStartF, Lambda onTickF, Lambda onEndF) {
        this.onStartF = onStartF;
        this.onTickF = onTickF;
        this.onEndF = onEndF;
    }

    /**
     * Called when the task starts. It triggers the onStartF lambda if it's not null.
     */
    public void onStart() {
        if (onStartF != null)
            onStartF.run();
    }

    /**
     * Called on each tick during the task's execution. It triggers the onTickF lambda if it's not null.
     * @param tick The current tick count or time since the task started.
     */
    public void onTick(double tick) {
        if (onTickF != null)
            onTickF.run();
    }

    /**
     * Called when the task ends. It triggers the onEndF lambda if it's not null.
     */
    public void onEnd() {
        if (onEndF != null)
            onEndF.run();
    }
}