package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import subsystems.LimeLight;
import org.firstinspires.ftc.teamcode.SimpleFieldCentricDrive;

@TeleOp(name = "TeleOp Red", group = "Concept")
public class teleopred extends SimpleFieldCentricDrive {
   @Override public void setTargets()
    {
        Limelight = new LimeLight(hardwareMap, 24);
    }
}
