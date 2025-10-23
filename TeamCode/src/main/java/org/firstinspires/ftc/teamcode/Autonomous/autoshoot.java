package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Old.BetterIMU;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.MotorSpeeds;
import org.firstinspires.ftc.teamcode.subsystems.LimeLight;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.driveallclass;

@Autonomous(name = "AutoShoot Near", group = "Blue Team")
public class autoshoot extends LinearOpMode {
    driveallclass driveallclass;
    Shooter shooter;

    Intake intake;
    MotorSpeeds launchSpeed = MotorSpeeds.NEAR;

    Location firstPosition = new Location(-130, 0, 0);

    //we need to turn here -45 degrees
    Location secondPosition = new Location(-130, 0, -43);

    Location thirdPosition = new Location(-130, -100, -43);

    //we need to prestart the motor to launch
    Location fourthPosition = new Location(-130, 0, -43);

    //we need to turn here 45 degrees
    //after we turn we need to turn on the feeder
    Location fifthPosition = new Location(-130, -100, 0);

    //this moves the robot out of the triangle
    LimeLight limelight;

    void setTargets() {

    }

    @Override
    public void runOpMode() {
        BetterIMU betterIMU = new BetterIMU(hardwareMap);
        driveallclass = new driveallclass(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        limelight = new LimeLight(hardwareMap, 20);

        driveallclass.resetHeading(0);

        setTargets();

        waitForStart();
        resetRuntime();


        while (opModeIsActive() && !isStopRequested()) {

            double time = getRuntime();

            double turnVal = 0;

            LLResultTypes.FiducialResult result = limelight.getEitherResult();
            if (result != null) {
                turnVal = result.getTargetXDegrees() * 0.045;
            }

            if (time < 3) {
// the 3 was 6
                driveallclass.driveToPosition(firstPosition, 0, telemetry);
            } else if (time < 6) {//the 6 was 12
                driveallclass.drive(0, 0, 0);
                shooter.setShooterSpeed(launchSpeed);
            } else if (time < 14) {//the 7 was 14
                intake.spinTransfer(1);
                intake.spinIntake(1);
                intake.spinKicker(0.4);
            } else if (time < 16) {//the 8 was 15
                //armBase.setKicker(false);
                intake.spinTransfer(0);
                intake.spinIntake(0);
                intake.spinKicker(0);
                shooter.setShooterSpeed(MotorSpeeds.ZERO);
            } else if (time < 18) {//the 10 was 20
                driveallclass.driveToPosition(secondPosition, 0, telemetry);
            } else if (time < 20) {
                driveallclass.driveToPosition(thirdPosition, 0, telemetry);
            } else if (time < 22) {// the 18 was 22
                driveallclass.drive(0, 0, 0);

            } else if (time < 24) {
                driveallclass.driveToPosition(fourthPosition, 0, telemetry);
            } else if (time < 30) {
                driveallclass.driveToPosition(fifthPosition, 0, telemetry);

                driveallclass.update();
                driveallclass.updateValues(telemetry);
                shooter.execute(true,MotorSpeeds.NEAR);
//            armBase.setSpeed(launchSpeed); //Three quarters speed at 900, starts in initialize
//            armBase.execute();
//            armBase.toggleFeed(0.5); // starts when we
                telemetry.update();
            }
        }
    }
}