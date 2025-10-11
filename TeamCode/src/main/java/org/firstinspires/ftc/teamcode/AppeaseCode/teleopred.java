package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.LimeLight;
import org.firstinspires.ftc.teamcode.SimpleFieldCentricDrive;

@TeleOp
public class teleopred extends SimpleFieldCentricDrive {

   @Override public void setTargets()
    {
        Limelight = new LimeLight(hardwareMap, 24);
    }
}
