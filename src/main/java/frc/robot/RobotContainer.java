package frc.robot;

import frc.robot.Core.RobotPolicy;

public class RobotContainer {
    static RobotPolicy policy;

    static RobotPolicy init() {

        policy = new RobotPolicy() {
            public void teleop() {
            }

            public void autonomous() {

            }

            public void test() {

            }

            @Override
            public void disabled() {

            }
        };
        return policy;
    }
}
