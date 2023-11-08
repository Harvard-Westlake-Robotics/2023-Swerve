package frc.robot.Core;

/**
 * ScheduledCommand is an abstract class that provides a structure for commands
 * that are executed within the robot's scheduler.
 */
public abstract class ScheduledCommand {
    private boolean completed = false; // Tracks whether the command has been completed.

    /**
     * Checks if the command has been completed.
     * @return True if the command is complete, false otherwise.
     */
    public boolean isComplete() {
        return completed;
    }

    /**
     * Marks the command as completed.
     */
    public void complete() {
        completed = true;
    }

    /**
     * Abstract method to define what happens when the command starts.
     */
    protected abstract void onStart();

    /**
     * Abstract method to define what happens on each tick of the scheduler
     * during the command execution.
     * @param dTime The time passed since the last tick.
     */
    protected abstract void onTick(double dTime);

    /**
     * Abstract method to define what happens when the command ends.
     */
    protected abstract void onEnd();

    private boolean started = false; // Tracks whether the command has been started.

    /**
     * Checks if the command has been started.
     * @return True if the command has started, false otherwise.
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Starts the command if it hasn't been started already. Calls the onStart method.
     */
    public void start() {
        if (started)
            throw new Error("COMMAND ALREADY USED"); // Prevents reusing the same command.
        started = true;
        onStart(); // Calls the custom onStart implementation.
    }

    /**
     * Executes a tick of the command. Calls the onTick method.
     * @param dTime The time passed since the last tick.
     */
    public void tick(double dTime) {
        onTick(dTime); // Calls the custom onTick implementation.
    }

    /**
     * Ends the command. Sets the command as completed and calls the onEnd method.
     */
    public void end() {
        completed = true;
        onEnd(); // Calls the custom onEnd implementation.
    }
}
