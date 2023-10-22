package frc.robot.Components;

import frc.robot.Util.LERP;
import frc.robot.Util.PDConstant;
import frc.robot.Util.PDController;
import frc.robot.Util.Tickable;

public class LifterPD implements Tickable {
    private ArmLifter lifter;
    private LERP target = new LERP(90);
    private PDController con;
    

    public LifterPD(ArmLifter lifter, PDConstant con) {
        this.lifter = lifter;
        this.con = new PDController(con);
    }

    public void setTarget(double tar) {
        target.set(tar);
    }

    public void tick(double dTime) {
        target.tick(dTime);
        lifter.setVoltage(-con.solve(target.get() - lifter.getAngleDeg()));
    }
}
