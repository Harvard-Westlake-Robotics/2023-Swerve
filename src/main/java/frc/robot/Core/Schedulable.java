package frc.robot.Core;

public abstract class Schedulable {
    private boolean done = false;

    public boolean isComplete() {
        return done;
    }

    protected void complete() {
        done = true;
    }

    public void unSchedule() {
        done = true;
    }

    protected abstract void start();

    protected abstract void tick(double dTime);

    protected abstract void end();
}
