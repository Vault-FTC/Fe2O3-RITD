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

@Autonomous(name = "AutoShoot Near", group = "Blue Team")
public class autoshoot extends LinearOpMode {
    DriveBase driveBase;
    ArmBase armBase;

    Intake intake;
    MotorSpeeds launchSpeed = MotorSpeeds.FIVE_EIGHTS;

    Location firstPosition = new Location(-24600, 0);
    Location secondPosition = new Location(-24600, -14000);

    LimeLight limelight;

    void setTargets()
    {

    }

    @Override
    public void runOpMode() {
        BetterIMU betterIMU = new BetterIMU(hardwareMap);
        driveBase = new DriveBase(betterIMU, hardwareMap, gamepad1);
        intake = new Intake(hardwareMap);
        armBase = new ArmBase(hardwareMap);
        limelight = new LimeLight(hardwareMap, 20);

        driveBase.resetHeading(45);

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
                driveBase.driveToPosition(firstPosition, turnVal);
            }
            else if (time < 12)
            {
                driveBase.drive(0, 0, turnVal);
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
                driveBase.driveToPosition(secondPosition, 0);
            }
            else if(time < 22) {
                driveBase.drive(0, 0, 0);
            }

            driveBase.update();
            driveBase.updateValues(telemetry);
            armBase.execute();
//            armBase.setSpeed(launchSpeed); //Three quarters speed at 900, starts in initialize
//            armBase.execute();
//            armBase.toggleFeed(0.5); // starts when we
        }
    }
}