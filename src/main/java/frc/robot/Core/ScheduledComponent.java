package frc.robot.Core;

import frc.robot.Robot;
import frc.robot.Util.Lambda;
import frc.robot.Util.Setter;

// in its constructor, register itself with the scheduler as a scheduleItem
public abstract class ScheduledComponent {

    public ScheduledComponent() {
        if (!Robot.isInit()) {
            throw new Error("ScheduledComponent must be initialized during phase initialization");
        }
        var component = this;
        Setter<Double> componentTick = (Double dTime) -> {
            component.tick(dTime);
        };
        Lambda componentEnd = () -> {
            component.cleanUp();
        };
        Schedulable schedulable = new Schedulable() {
            public void start() {
                return;
            }

            public void tick(double dTime) {
                componentTick.set(dTime);
            }

            public void end() {
                componentEnd.run();
            }
        };
        Scheduler.runTask(schedulable);
    }

    protected abstract void tick(double dTime);

    protected abstract void cleanUp();
}
