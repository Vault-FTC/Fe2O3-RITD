package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


@TeleOp(name = "Simple Field Centric Drive", group = "Concept")
public class SimpleFieldCentricDrive extends LinearOpMode {

    private DcMotorEx frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    private IMU imu;

    @Override
    public void runOpMode() {

        // Initialize motors
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "rf");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "lf");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "rb");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "lb");

        // Reverse one side of motors if needed (depends on robot configuration)
        frontLeftMotor.setDirection(DcMotorEx.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorEx.Direction.REVERSE);

        // Initialize IMU
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)); // Adjust based on your robot's hub orientation
        imu.initialize(parameters);

        // Reset yaw at the start of the match (or when desired)
        imu.resetYaw();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Forward/backward
            double x = gamepad1.left_stick_x;  // Strafe left/right
            double rx = gamepad1.right_stick_x; // Rotation

            // Get robot's current heading
            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement vector by the inverse of the robot's heading
            double rotatedX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotatedY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            // Calculate motor powers
            double frontLeftPower = rotatedY + rotatedX - rx;
            double backLeftPower = rotatedY - rotatedX - rx;
            double frontRightPower = rotatedY - rotatedX + rx;
            double backRightPower = rotatedY + rotatedX + rx;

            // Normalize motor powers to prevent exceeding 1.0
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
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            telemetry.addData("robot direction", Math.toDegrees(botHeading));
            telemetry.update();
        }
    }
}
