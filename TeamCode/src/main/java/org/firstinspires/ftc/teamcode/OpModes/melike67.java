package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CommandSystem.CommandScheduler;
import org.firstinspires.ftc.teamcode.subsystems.LimeLight;
import org.firstinspires.ftc.teamcode.subsystems.driveallclass;

@TeleOp
public class melike67 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        LimeLight Limes = new LimeLight(hardwareMap,20);
        driveallclass slay = new driveallclass(hardwareMap);

        waitForStart();
        while(opModeIsActive()){
            CommandScheduler.getInstance().run();

        }
    }
}
