package subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autonomous.Location;
import org.firstinspires.ftc.teamcode.geometry.PoseEstimator;

import java.util.function.IntSupplier;

public class driveallclass {
    private DcMotorEx frmotor, flmotor, brmotor, blmotor;
    IntSupplier rightOdo, leftOdo, backOdo;
    PoseEstimator poseEstimator;

    public driveallclass(HardwareMap hardwareMap) {
        frmotor = hardwareMap.get(DcMotorEx.class, "rf");
        flmotor = hardwareMap.get(DcMotorEx.class, "lf");
        brmotor = hardwareMap.get(DcMotorEx.class, "rb");
        blmotor = hardwareMap.get(DcMotorEx.class, "lb");

// Reverse one side of motors if needed (depends on robot configuration)
        frmotor.setDirection(DcMotorEx.Direction.REVERSE);
        brmotor.setDirection(DcMotorEx.Direction.REVERSE);
        flmotor.setDirection(DcMotorSimple.Direction.FORWARD);

        leftOdo = () -> brmotor.getCurrentPosition(); // left
        backOdo = () -> blmotor.getCurrentPosition(); // back
        rightOdo = () -> frmotor.getCurrentPosition(); // right

        poseEstimator = new PoseEstimator(leftOdo, rightOdo, backOdo);
        poseEstimator.update();
        poseEstimator.resetHeading(0);
    }

    public void resetHeading(double heading) {
        poseEstimator.resetHeading(heading);
    }

    public String getPosition() {
        return "X: " + poseEstimator.getGlobalX() + " Y: " + poseEstimator.getGlobalY() + " Heading: " + poseEstimator.getHeading();
    }

    public void drive(double forward, double right, double rotate) {
        double botHeading = poseEstimator.getHeading();

        // Rotate the movement vector by the inverse of the robot's heading
        // X is positive to the right, Y is positive up
        double rotatedX = forward * Math.sin(botHeading) - right * Math.cos(botHeading);
        double rotatedY = forward * Math.cos(botHeading) + right * Math.sin(botHeading);

        // Calculate motor powers
        double frontLeftPower = rotatedY + rotatedX + rotate;
        double backLeftPower = rotatedY - rotatedX + rotate;
        double frontRightPower = rotatedY - rotatedX - rotate;
        double backRightPower = rotatedY + rotatedX - rotate;
        double maxPower = Math.max(Math.abs(frontLeftPower),
                Math.max(Math.abs(backLeftPower),
                        Math.max(Math.abs(frontRightPower), Math.abs(backRightPower))));

        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            backLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backRightPower /= maxPower;
        }

        // Set motor powers
        frmotor.setPower(frontRightPower);
        brmotor.setPower(backRightPower);
        flmotor.setPower(frontLeftPower);
        blmotor.setPower(backLeftPower);

        poseEstimator.update();
    }

//    public void driveToPosition(Location target, double turnVal)
//    {
//        double p = 0.0001;
//        double strafe = (target.Strafe + (backOdo.getAsInt() * poseEstimator.centimetersPerTick));
//        double forward1 = (target.Forward - (leftOdo.getAsInt() * poseEstimator.centimetersPerTick));
//        double forward2 = (target.Forward - (rightOdo.getAsInt() * poseEstimator.centimetersPerTick));
//
//        // double strafe = (target.Strafe
//        double forward = (forward1 + forward2) / 2;
//        if(Math.abs(forward) <  400)
//        {
//            forward = 0;
//        }
//        if(Math.abs(strafe) < 400)
//        {
//            strafe = 0;
//        }
//        drive(forward * p, strafe * p, turnVal);
//    }

    public void driveToPosition(Location target, double turnVal, Telemetry telemetry) {
        double p = 0.004;
        double strafe = (target.Strafe - poseEstimator.getGlobalY());
        double forward = (target.Forward - poseEstimator.getGlobalX());

        if (telemetry != null) {
            telemetry.addData("Strafe", strafe);
            telemetry.addData("Forward", forward);
        }

        drive(forward * p, strafe * p, turnVal);

    }
    public void update()
    {
        poseEstimator.update();
    }
    public void updateValues(Telemetry telemetry)
    {
        telemetry.addData("Robot backOdo", backOdo.getAsInt() * poseEstimator.centimetersPerTick);
        telemetry.addData("Robot rightOdo", rightOdo.getAsInt() * poseEstimator.centimetersPerTick);
        telemetry.addData("Robot leftOdo", leftOdo.getAsInt() * poseEstimator.centimetersPerTick);
        telemetry.addData("Robot backOdo ticks ", backOdo.getAsInt());
        telemetry.addData("Robot rightOdo ticks", rightOdo.getAsInt());
        telemetry.addData("Robot leftOdo ticks", leftOdo.getAsInt());
    }
}