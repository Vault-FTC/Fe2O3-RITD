package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.LimeLight;


@Autonomous(name = "Red Far", group = "Red Team")
public class RedFar extends BaseFarAuto {
    @Override
    void setTargets() {
        LimeLight = new LimeLight(hardwareMap,24);
        farShootPosition = new Location(0, 0, 0);
        firstPickupPosition = new Location(80, 0, -75);
        firstPickupPosition2 = new Location(80, 100, -75);
        parkPosition = new Location(88, 0, 0);

    }
}


