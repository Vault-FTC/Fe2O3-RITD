package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AppeaseCode.ArmBase;
import org.firstinspires.ftc.teamcode.AppeaseCode.BetterIMU;
import org.firstinspires.ftc.teamcode.AppeaseCode.DriveBase;
import subsystems.Intake;
import org.firstinspires.ftc.teamcode.AppeaseCode.MotorSpeeds;
import subsystems.LimeLight;
import subsystems.driveallclass;

@Autonomous(name = "AutoShoot Near", group = "Blue Team")
public class autoshoot extends LinearOpMode {
    driveallclass driveallclass;
    ArmBase armBase;

    Intake intake;
    MotorSpeeds launchSpeed = MotorSpeeds.NEAR;

    Location firstPosition = new Location(-130, 0);

    //we need to turn here -45 degrees
    Location secondPosition = new Location(-115,114);

    //we need to prestart the motor to launch
    Location thirdPosition = new Location(-115,-114);

    //we need to turn here 45 degrees
    //after we turn we need to turn on the feeder
    Location fourthPosition = new Location(-115,100);

    //this moves the robot out of the triangle
    LimeLight limelight;

    void setTargets()
    {

    }

    @Override
    public void runOpMode() {
        BetterIMU betterIMU = new BetterIMU(hardwareMap);
        driveallclass = new driveallclass(hardwareMap);
        intake = new Intake(hardwareMap);
        armBase = new ArmBase(hardwareMap);
        limelight = new LimeLight(hardwareMap, 20);

        driveallclass.resetHeading(45);

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
                driveallclass.drive(0, 0, turnVal);
                armBase.setSpeed(launchSpeed);
                armBase.setShooter(true);
            } else if (time < 7) {//the 7 was 14
                armBase.setShooter(true);
                intake.spinTransfer(1);
                intake.spinIntake(1);
                intake.spinKicker(0.4);
            } else if (time < 8) {//the 8 was 15
                //armBase.setKicker(false);
                intake.spinTransfer(0);
                intake.spinIntake(0);
                intake.spinKicker(0);
                armBase.setShooter(false);
            } else if (time < 10) {//the 10 was 20
                driveallclass.driveToPosition(secondPosition, 0, null);



            } else if (time < 15) {

                driveallclass.driveToPosition(thirdPosition, 0, telemetry);

            } else if(time < 18) {// the 18 was 22
                driveallclass.drive(0, 0, 0);

            } else if (time < 24) {
                driveallclass.driveToPosition(fourthPosition, 0, telemetry);
            }

            driveallclass.update();
            driveallclass.updateValues(telemetry);
            armBase.execute();
//            armBase.setSpeed(launchSpeed); //Three quarters speed at 900, starts in initialize
//            armBase.execute();
//            armBase.toggleFeed(0.5); // starts when we
            telemetry.update();
        }
    }
}