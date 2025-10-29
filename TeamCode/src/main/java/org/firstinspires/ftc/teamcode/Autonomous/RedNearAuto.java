package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.LimeLight;

@Autonomous(name = "Red Near", group = "Red Team")
public class RedNearAuto extends BaseNearAuto {

    @Override
    void setTargets()
    { LimeLight = new LimeLight(hardwareMap,24);
        nearShootPosition = new Location(-110, 0, 0);
        firstPickupPosition = new Location(-70, 90, -43);
        parkPosition = new Location(-130, 100, 0);
        secondPickupPosition = new Location(-140,100, -43);
        secondPickupPosition2 = new Location(-90, 130, -43);
    }
}
