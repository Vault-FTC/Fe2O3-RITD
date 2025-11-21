package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.LimeLight;


@Autonomous(name = "Red Far", group = "Red Team")
public class RedFar extends BaseFarAuto {
    @Override
    void setTargets() {
        LimeLight = new LimeLight(hardwareMap,24);
        doNotHitWall = new Location(30, 0, -25);
        farShootPosition = new Location(30, 0, -40);
        firstPickupPosition = new Location(80, 0, -95);
        firstPickupPosition2 = new Location(70, 130, -95);
        secondPickupPosition = new Location(160, 0, -95);
        secondPickupPosition2 = new Location(150,130, -95);
        parkPosition = new Location(88, 10, -95);
    }
}


