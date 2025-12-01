package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Light;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lights;
import org.firstinspires.ftc.teamcode.subsystems.MotorSpeeds;
import org.firstinspires.ftc.teamcode.Autonomous.Location;
import org.firstinspires.ftc.teamcode.geometry.PoseEstimator;

import org.firstinspires.ftc.teamcode.subsystems.LimeLight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.driveallclass;


@TeleOp(name = "TeleOp Blue", group = "Teleop")
public class SimpleFieldCentricDrive extends LinearOpMode {

    public LimeLight Limelight;
    Intake intake;
    boolean last_y;
    boolean last_up;
    boolean last_down;
    boolean shooting;
    RevBlinkinLedDriver.BlinkinPattern green;
    RevBlinkinLedDriver.BlinkinPattern red;
    RevBlinkinLedDriver.BlinkinPattern idle;

    Lights light;

    double launchpower = (850);

    public void setTargets() {
        Limelight = new LimeLight(hardwareMap, 20);
    }

    @Override
    public void runOpMode() {
        intake = new Intake(hardwareMap);
        light = new Lights(hardwareMap);
        driveallclass drive = new driveallclass(hardwareMap);
        Shooter launcher = new Shooter(hardwareMap);
        //MotorSpeeds launchpower = MotorSpeeds.NEAR;
        setTargets();
        green = RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_PARTY_PALETTE;
        red = RevBlinkinLedDriver.BlinkinPattern.RED;
        idle = RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_FOREST_PALETTE;

        waitForStart();
        // poseEstimator.update();
        while (opModeIsActive()) {

            double joystick_y = gamepad1.left_stick_y; // Forward/backward
            double joystick_x = gamepad1.left_stick_x;  // Strafe left/right
            double joystick_rx = -gamepad1.right_stick_x; // Rotation

            if (gamepad1.start) {
                drive.resetHeading(0);
            }

            if (!last_y && gamepad1.y) {
                shooting = !shooting;
            }
            last_y = gamepad1.y;

            if (gamepad1.x) {
                intake.spinKicker(0.75);
                intake.spinIntake(0.95);
            } else if (gamepad1.left_bumper) {
                intake.spinIntake(0.95);
                intake.spinKicker(-1);
            } else if (gamepad1.b) {
                intake.spinIntake(-0.95);
                intake.spinKicker(-0.75);
            } else {
                intake.spinKicker(0);
                intake.spinIntake(0);
            }

            if (gamepad1.right_bumper) {
                joystick_rx = joystick_rx - Limelight.getTx() / 1.75;
                LLResultTypes.FiducialResult result = Limelight.getResult();
                this.launchpower = 600;
                if (result == null) {

                } else {
                    double range = Math.abs(result.getCameraPoseTargetSpace().getPosition().z);
                    // launchpower = 0.4 + range / 4;
                    // was 0.3
//                    this.launchpower = launcher.distanceToSpeed(range);
                    telemetry.addData("fff", range);
                    if (result.getCameraPoseTargetSpace().getPosition().x < 67) {
                        light.setColor(green);
                        if (result.getCameraPoseTargetSpace().getPosition().z <= -2.5) {
                            this.launchpower = 1050;
                        }
                        else {
                            this.launchpower = 850;
                        }
                        launcher.execute(true, this.launchpower);
                    }
                    else {
                        light.setColor(red);
                    }
                }
            } else {
                light.setColor(idle);
                if (shooting) {
                    launcher.setShooterSpeed(this.launchpower);
                } else {
                    launcher.setShooterSpeed(MotorSpeeds.ZERO.speed);
                }

                if (gamepad1.x) {
                    launcher.toggleKicker(0.5);
                } else {
                    launcher.toggleKicker(0);
                }
            }

            if (gamepad1.back) {
                drive.driveToPosition(new Location(0,0,0), 0 ,telemetry);
            }
            else {
                drive.drive(joystick_y, joystick_x, joystick_rx);
            }


            last_down = gamepad1.dpad_down;
            last_up = gamepad1.dpad_up;
            telemetry.addData("shootSpeed", launcher.getShooterVelocity());
            telemetry.addData("LaunchPower", this.launchpower);
            telemetry.addData("Position", drive.getPosition());
            if (Limelight.getResult() != null) {
                telemetry.addData("Distance from AprilTag", Limelight.getResult().getCameraPoseTargetSpace().getPosition().z);
            }
            telemetry.update();
            drive.updateValues(telemetry);
        }
    }
}