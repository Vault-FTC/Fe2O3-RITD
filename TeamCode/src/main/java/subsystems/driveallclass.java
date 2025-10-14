package subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.geometry.PoseEstimator;

import java.util.function.IntSupplier;

public class driveallclass {
    private DcMotorEx frmotor, flmotor, brmotor, blmotor;
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

        IntSupplier leftOdo = () -> brmotor.getCurrentPosition(); // left
        IntSupplier backOdo = () -> blmotor.getCurrentPosition(); // back
        IntSupplier rightOdo = () -> frmotor.getCurrentPosition(); // right

        poseEstimator = new PoseEstimator(leftOdo, rightOdo, backOdo);
        poseEstimator.update();
        poseEstimator.resetHeading(0);
    }


    public void drive(double rx, double x, double y) {
        double botHeading = poseEstimator.getHeading();

        // Rotate the movement vector by the inverse of the robot's heading
        double rotatedX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
        double rotatedY = x * Math.sin(botHeading) + y * Math.cos(botHeading);

        // Calculate motor powers
        double frontLeftPower = rotatedY + rotatedX + rx;
        double backLeftPower = rotatedY - rotatedX + rx;
        double frontRightPower = rotatedY - rotatedX - rx;
        double backRightPower = rotatedY + rotatedX - rx;
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
        frmotor.setPower(frontLeftPower);
        brmotor.setPower(backLeftPower);
        flmotor.setPower(frontRightPower);
        blmotor.setPower(backRightPower);

        poseEstimator.update();
    }
}