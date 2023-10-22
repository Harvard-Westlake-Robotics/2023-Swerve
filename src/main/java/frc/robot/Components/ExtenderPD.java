package frc.robot.Components;

import frc.robot.Util.DeSpam;
import frc.robot.Util.LERP;
import frc.robot.Util.PDConstant;
import frc.robot.Util.PDController;
import frc.robot.Util.Tickable;

public class ExtenderPD implements Tickable {
    private ArmExtender extender;
    private ArmLifter lifter;
    private LERP target = new LERP(90);
    private PDController con;

    public ExtenderPD(ArmExtender extender, PDConstant con, ArmLifter lifter) {
        this.extender = extender;
        this.lifter = lifter;
        this.con = new PDController(con);
        target.set(0);
    }

    public void setTarget(double tar) {
        target.set(tar);
    }

    public void forceSetTar(double tar) {
        target = new LERP(90);
        target.set(tar);
    }

    DeSpam dSpam = new DeSpam(1);

    public void tick(double dTime) {
        target.tick(dTime);
        var pdCorrect = con.solve(target.get() - extender.getExtensionInches());
        final var extenderVolt = ArmExtenderAntiGrav.getAntiGravVoltage(lifter.getAngleDeg())
                + pdCorrect;
        extender.setVoltage(extenderVolt);
        dSpam.exec(() -> {
            System.out.println("extendervolt: " + extenderVolt + " pdCorrect: " + pdCorrect);
        });
    }
}
