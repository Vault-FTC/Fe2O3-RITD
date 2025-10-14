package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import subsystems.Intake;
import org.firstinspires.ftc.teamcode.AppeaseCode.MotorSpeeds;
import org.firstinspires.ftc.teamcode.geometry.PoseEstimator;

import subsystems.LimeLight;
import subsystems.driveallclass;


@TeleOp(name = "TeleOp Blue", group = "Concept")
public class SimpleFieldCentricDrive extends LinearOpMode {

    private DcMotorEx frmotor, flmotor, brmotor, blmotor;
    public LimeLight Limelight;
    Intake intake;
    PoseEstimator poseEstimator;

    boolean last_y;
    boolean shooting;

    public void setTargets() {
        Limelight = new LimeLight(hardwareMap, 20);
    }

    @Override
    public void runOpMode() {
        intake = new Intake(hardwareMap);
        driveallclass drive = new driveallclass(hardwareMap);
        MotorSpeeds launchpower = MotorSpeeds.HALF;

        setTargets();
        DcMotorEx shooter = hardwareMap.get(DcMotorEx.class, "shooter");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0,0));
        shooter.setPower(1.0);
        shooter.setVelocity(0, AngleUnit.DEGREES);

        waitForStart();
        // poseEstimator.update();
        while (opModeIsActive()) {

            double y = gamepad1.left_stick_y; // Forward/backward
            double x = gamepad1.left_stick_x;  // Strafe left/right
            double rx = gamepad1.right_stick_x; // Rotation

//            if (gamepad1.start) {
//                poseEstimator.resetHeading(0);
//            }

            if (!last_y && gamepad1.y) {
                shooting = !shooting;
            }
            last_y = gamepad1.y;

            if(shooting) {
                shooter.setVelocity(-launchpower.speed);
            }
            else
            {
                shooter.setVelocity(0);
            }

//            if (gamepad1.x) {
//                kick.setPower(0.5);
//            } else {
//                kick.setPower(0);
//            }

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

            if (gamepad1.dpad_up){
                launchpower = MotorSpeeds.THREE_QUARTERS;
            } //long shot
            if (gamepad1.dpad_down){
                launchpower = MotorSpeeds.HALF;
            } //short shot
            if (gamepad1.dpad_left) {
                launchpower = MotorSpeeds.FULL;
            }

            drive.drive(rx, x, y);

//            telemetry.addData("OdoBack", backOdo.getAsInt());
//            telemetry.addData("OdoLeft", leftOdo.getAsInt());
//            telemetry.addData("OdoRight", rightOdo.getAsInt());
            telemetry.addData("shootSpeed", shooter.getVelocity());
            // telemetry.addData("heading", Math.toDegrees(poseEstimator.getHeading()) % 360);
            //telemetry.addData("Shooting", shooting);
            telemetry.addData("LaunchPower", launchpower);
            telemetry.update();
//            poseEstimator.update();
        }
    }
}
