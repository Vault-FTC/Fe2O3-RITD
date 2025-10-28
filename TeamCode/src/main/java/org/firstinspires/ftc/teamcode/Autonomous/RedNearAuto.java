package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Near", group = "Red Team")
public class RedNearAuto extends BaseNearAuto {

    @Override
    void setTargets()
    {
        nearShootPosition = new Location(-130, 0, 0);
        firstPickupPosition = new Location(-50, 90, -43);
        parkPosition = new Location(-130, 100, 0);
        secondPickupPosition = new Location(-130,0, -43);
        secondPickupPosition2 = new Location(-130, 130, -43);
    }
}
