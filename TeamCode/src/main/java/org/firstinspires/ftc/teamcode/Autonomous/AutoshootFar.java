package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AppeaseCode.MotorSpeeds;

@Autonomous(name = "AutoShoot Far", group = "Blue Team")
public class AutoshootFar extends autoshoot {

    @Override
    void setTargets()
    {
        firstPosition = new Location(0, 0);
        secondPosition = new Location(7000,-14000);
        launchSpeed = MotorSpeeds.FAR;
        driveBase.resetHeading(65);
    }
}
