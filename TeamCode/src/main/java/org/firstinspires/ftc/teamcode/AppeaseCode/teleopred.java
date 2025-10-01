package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.LimeLight;

@TeleOp
public class teleopred extends TestOpMode{

   @Override void setTargets()
    {
        limelight = new LimeLight(hardwareMap, 24);
    }
}
