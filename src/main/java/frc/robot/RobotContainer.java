package frc.robot;

import frc.robot.Components.LimeLight;
import frc.robot.Core.RobotPolicy;
import frc.robot.Core.Schedulable;
import frc.robot.Core.Scheduler;

public class RobotContainer {

    static RobotPolicy init() {
        return new RobotPolicy() {
            public void teleop() {

                LimeLight.setCamMode(false);
                Scheduler.runTask(
                    new Schedulable() {
                        public void start() {
                        }

                        public void tick(double dTime) {
                            
                        }

                        public void end() {
                        }
                    }
                );
            }

            public void autonomous() {
                LimeLight.setCamMode(true);
            }

            public void test() {

            }

            @Override
            public void disabled() {

            }
        };
    }
}
