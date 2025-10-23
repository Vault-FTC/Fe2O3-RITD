package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutoShoot red near", group = "Red Team")
public class AutoshootRedNear extends autoshoot {

    @Override
    void setTargets()
    {
        firstPosition = new Location(-24600, 0);
        secondPosition = new Location(-24600, 14000);
        //driveBase.resetHeading(65);
    }
}