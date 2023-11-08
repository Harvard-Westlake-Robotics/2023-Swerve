package frc.robot.Util;

import edu.wpi.first.wpilibj.Timer;

/**
 * The DeSpam class is a utility to prevent a piece of code from running too frequently,
 * effectively "debouncing" it to avoid spamming the system or logs.
 */
public class DeSpam {
    private double lastTime = 0;     // The last time the code was executed.
    private double minTimeDiff;      // The minimum time difference between successive executions.

    /**
     * Constructs a DeSpam object with a specified minimum time difference.
     *
     * @param minTimeDiffSec The minimum time in seconds between allowed executions.
     */
    public DeSpam(double minTimeDiffSec) {
        this.minTimeDiff = minTimeDiffSec;
    }

    /**
     * Executes the given function if the minimum time has elapsed since the last execution.
     *
     * @param func The function to execute.
     * @return true if the function was executed, false otherwise.
     */
    public boolean exec(Lambda func) {
        // Check if the current time is greater than the last execution time plus the minimum difference.
        if (Timer.getFPGATimestamp() - lastTime > minTimeDiff) {
            // If enough time has passed, run the function.
            func.run();
            // Update the last time to the current time.
            lastTime = Timer.getFPGATimestamp();
            // Return true to indicate the function was executed.
            return true;
        }
        // If not enough time has passed, return false and do not execute the function.
        return false;
    }
}
