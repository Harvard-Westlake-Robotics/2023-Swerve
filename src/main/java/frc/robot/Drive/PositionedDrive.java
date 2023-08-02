package frc.robot.Drive;

public class PositionedDrive extends Drive {
    private double x = 0;
    private double y = 0;
    private double angle = 0; // deg
    private double[][] lastWheelPositions = new double[4][2];

    public void reset() {
        updateLastWheelPositions();
    }

    public PositionedDrive(SwerveModulePD frontLeft, SwerveModulePD frontRight, SwerveModulePD backLeft,
            SwerveModulePD backRight) {
        super(frontLeft, frontRight, backLeft, backRight);

        updateLastWheelPositions();
    }

    public void updateLastWheelPositions() {
        lastWheelPositions[0] = new double[] { frontRight.getAngle(), frontRight.getDist() };
        lastWheelPositions[1] = new double[] { frontLeft.getAngle(), frontLeft.getDist() };
        lastWheelPositions[2] = new double[] { backLeft.getAngle(), backLeft.getDist() };
        lastWheelPositions[3] = new double[] { backRight.getAngle(), backRight.getDist() };
    }

    @Override
    public void tick(double dTime) {
        double frontRightAngle = (frontRight.getAngle() + lastWheelPositions[0][0]) / 2;
        double frontLeftAngle = (frontLeft.getAngle() + lastWheelPositions[1][0]) / 2;
        double backLeftAngle = (backLeft.getAngle() + lastWheelPositions[2][0]) / 2;
        double backRightAngle = (backRight.getAngle() + lastWheelPositions[3][0]) / 2;

        double frontRightDist = lastWheelPositions[0][1] - frontRight.getDist();
        double frontLeftDist = lastWheelPositions[1][1] - frontLeft.getDist();
        double backLeftDist = lastWheelPositions[2][1] - backLeft.getDist();
        double backRightDist = lastWheelPositions[3][1] - backRight.getDist();

        

        updateLastWheelPositions();
        super.tick(dTime);
    };
}
