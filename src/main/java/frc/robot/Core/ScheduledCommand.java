package frc.robot.Core;

public abstract class ScheduledCommand {
    private boolean completed = false;

    public boolean isComplete() {
        return completed;
    }

    public void complete() {
        completed = true;
    }

    protected abstract void onStart();

    protected abstract void onTick(double dTime);

    protected abstract void onEnd();

    private boolean started = false;

    public boolean isStarted() {
        return started;
    }

    public void start() {
        if (started)
            throw new Error("COMMAND ALREADY USED");
        started = true;
        onStart();
    }

    public void tick(double dTime) {
        onTick(dTime);
    }

    public void end() {
        completed = true;
        onEnd();
    }
}
