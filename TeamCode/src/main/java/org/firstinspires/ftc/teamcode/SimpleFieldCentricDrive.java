package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import subsystems.Intake;
import org.firstinspires.ftc.teamcode.AppeaseCode.MotorSpeeds;

import subsystems.LimeLight;
import subsystems.Shooter;
import subsystems.driveallclass;


@TeleOp(name = "TeleOp Blue", group = "Concept")
public class SimpleFieldCentricDrive extends LinearOpMode {

    private DcMotorEx frmotor, flmotor, brmotor, blmotor;
    public LimeLight Limelight;
    Intake intake;

    boolean last_y;

    boolean last_dpad_up;
    boolean shooting;

    public void setTargets() {
        Limelight = new LimeLight(hardwareMap, 20);
    }

    @Override
    public void runOpMode() {
        intake = new Intake(hardwareMap);
        driveallclass drive = new driveallclass(hardwareMap);

        Shooter launcher = new Shooter(hardwareMap);

        MotorSpeeds launchpower = MotorSpeeds.NEAR;

        setTargets();

        waitForStart();
        // poseEstimator.update();
        while (opModeIsActive()) {

            double y = gamepad1.left_stick_y; // Forward/backward
            double x = gamepad1.left_stick_x;  // Strafe left/right
            double rx = gamepad1.right_stick_x; // Rotation

            if (gamepad1.start) {
                drive.resetHeading(0);
            }

            if (!last_y && gamepad1.y) {
                shooting = !shooting;
            }
            last_y = gamepad1.y;

            if(shooting) {
                launcher.setShooterSpeed(launchpower);
            }
            else
            {
                launcher.setShooterSpeed(MotorSpeeds.ZERO);
            }

            if (gamepad1.x) {
                launcher.toggleKicker(0.5);
            } else {
                launcher.toggleKicker(0);
            }

            if (gamepad1.x) {
                intake.spinKicker(0.75);
                intake.spinIntake(0.9);
                intake.spinTransfer(0.9);
            }
            else if (gamepad1.left_bumper) {
                intake.spinIntake(0.9);
                intake.spinTransfer(0.9);
                intake.spinKicker(-0.75);
            } else if (gamepad1.b) {
                intake.spinIntake(-0.9);
                intake.spinTransfer(-0.9);
                intake.spinKicker(-0.75);
            } else {
                intake.spinKicker(0);
                intake.spinIntake(0);
                intake.spinTransfer(0);
            }


            if (gamepad1.right_bumper) {
                rx = rx + Limelight.getTx() / 1.5;
                LLResultTypes.FiducialResult judge = Limelight.getResult();
                if (judge == null)
                {

                }
                else
                {
                   double range = Math.abs(judge.getCameraPoseTargetSpace().getPosition().z);
                   // launchpower = 0.4 + range / 4;
                   // was 0.3
                   telemetry.addData("fff", launchpower);
                }
            }

         if (!last_dpad_up && gamepad1.dpad_up) {
             if (launchpower == MotorSpeeds.ZERO)

             if (launchpower == MotorSpeeds.THREE_EIGHTS)

                 if (launchpower == MotorSpeeds.HALF)

                     if (launchpower == MotorSpeeds.FIVE_EIGHTS)

                         if (launchpower == MotorSpeeds.THREE_QUARTERS)

                             if (launchpower == MotorSpeeds.EIGHTY)

                                 if (launchpower == MotorSpeeds.NINETY)

                                     if (launchpower == MotorSpeeds.NINETY_FIVE)


                launchpower = MotorSpeeds.THREE_QUARTERS;
            }
            last_dpad_up = gamepad1.dpad_up;
            //long shot
            if (gamepad1.dpad_down){
                launchpower = MotorSpeeds.NEAR;
            } //short shot
            if (gamepad1.dpad_left) {
                launchpower = MotorSpeeds.FULL;
            }


            drive.drive(rx, x, y);

//            telemetry.addData("OdoBack", backOdo.getAsInt());
//            telemetry.addData("OdoLeft", leftOdo.getAsInt());
//            telemetry.addData("OdoRight", rightOdo.getAsInt());
            telemetry.addData("shootSpeed", launcher.getShooterVelocity());
            // telemetry.addData("heading", Math.toDegrees(poseEstimator.getHeading()) % 360);
            //telemetry.addData("Shooting", shooting);
            telemetry.addData("LaunchPower", launchpower);
            telemetry.update();
//            poseEstimator.update();
        }
    }
}