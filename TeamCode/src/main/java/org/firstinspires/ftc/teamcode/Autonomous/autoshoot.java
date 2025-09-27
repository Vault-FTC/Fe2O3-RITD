package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.AppeaseCode.ArmBase;
import org.firstinspires.ftc.teamcode.AppeaseCode.BetterIMU;
import org.firstinspires.ftc.teamcode.AppeaseCode.DriveBase;
import org.firstinspires.ftc.teamcode.AppeaseCode.MotorSpeeds;
import org.firstinspires.ftc.teamcode.LimeLight;

@Autonomous(name = "AutoShoot Near", group = "Linear Op Mode")
public class autoshoot extends LinearOpMode {
    DriveBase driveBase;
    ArmBase armBase;
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
        armBase = new ArmBase(hardwareMap);
        limelight = new LimeLight(hardwareMap);

        driveBase.resetHeading(45);

        setTargets();

        waitForStart();
        resetRuntime();

        while (opModeIsActive() && !isStopRequested()) {

            double time = getRuntime();

            double turnVal = 0;

            LLResultTypes.FiducialResult result = limelight.getResult();
            if(result != null) {
                turnVal = result.getTargetXDegrees() * 0.05;
            }

            if(time < 8)
            {
                driveBase.driveToPosition(firstPosition, turnVal);
            }
            else if (time < 10)
            {
                driveBase.drive(0, 0, 0);
                armBase.setSpeed(launchSpeed);
                armBase.setKicker(true);
            }
            else if (time < 13)
            {
                armBase.setKicker(true);
                armBase.toggleFeed(0.5);
            }
            else if(time < 15)
            {
                //armBase.setKicker(false);
                armBase.toggleFeed(0);
            }
            else if(time < 20)
            {
                driveBase.driveToPosition(secondPosition, 0);
            }
            else if(time < 22) {
                driveBase.drive(0, 0, 0);
            }

            driveBase.update();
            armBase.execute();
//            armBase.setSpeed(launchSpeed); //Three quarters speed at 900, starts in initialize
//            armBase.execute();
//            armBase.toggleFeed(0.5); // starts when we
        }
    }
}