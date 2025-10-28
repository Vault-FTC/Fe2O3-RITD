package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "Red Far", group = "Red Team")
public class RedFar extends BaseFarAuto {
    @Override
    void setTargets() {
        farShootPosition = new Location(0, 0, 0);
        firstPickupPosition = new Location(80, 0, -75);
        firstPickupPosition2 = new Location(80, 100, -75);
        parkPosition = new Location(88, 0, 0);

    }
}


