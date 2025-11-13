package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Autonomous.BaseNearAuto;
import org.firstinspires.ftc.teamcode.CommandSystem.Trigger;
@TeleOp
public class TestNearAuto extends BaseNearAuto {

    @Override
    public void scheduleCommands(){
        Trigger drivetofirstshootcommand = new Trigger(() -> gamepad1.x);
          drivetofirstshootcommand.whileTrue((driveFrontStartToFirstLaunch()));
        Trigger drivetofirstintakeposition = new Trigger(() -> gamepad1.y);
        drivetofirstintakeposition.whileTrue((driveToFirstRowAndLaunch()));
        Trigger drivetosecondpositionagain = new Trigger(() -> gamepad1.right_bumper);
        drivetosecondpositionagain.whileTrue((driveToSecondRowAndLaunch()));
        Trigger drivetopickuppositionthree = new Trigger(() -> gamepad1.left_bumper);
        drivetopickuppositionthree.whileTrue((driveToThirdRowAndLaunch()));
   //     Trigger drivetoshootposition = new Trigger(() -> gamepad1.left_bumper);
    //   drivetoshootposition.whileTrue()
    }
}
