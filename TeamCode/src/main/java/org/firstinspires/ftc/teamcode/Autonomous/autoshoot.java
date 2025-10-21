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
    Location secondPosition = new Location(-130, 100);

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
            if(result != null) {
                turnVal = result.getTargetXDegrees() * 0.045;
            }

            if(time < 6)
            {

                driveallclass.driveToPosition(firstPosition, 0, telemetry);
            }
            else if (time < 12)
            {
                driveallclass.drive(0, 0, turnVal);
                armBase.setSpeed(launchSpeed);
                armBase.setShooter(true);
            }
            else if (time < 14)
            {
                armBase.setShooter(true);
                intake.spinTransfer(1);
                intake.spinIntake(1);
                intake.spinKicker(0.4);
            }
            else if(time < 15)
            {
                //armBase.setKicker(false);
                intake.spinTransfer(0);
                intake.spinIntake(0);
                intake.spinKicker(0);
                armBase.setShooter(false);
            }
            else if(time < 20)
            {
                driveallclass.driveToPosition(secondPosition, 0, null);
            }
            else if(time < 22) {
                driveallclass.drive(0, 0, 0);
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