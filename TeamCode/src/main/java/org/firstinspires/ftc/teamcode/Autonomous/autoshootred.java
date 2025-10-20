package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.AppeaseCode.MotorSpeeds;

@Autonomous(name = "AutoShoot red Far", group = "Red Team")
public class autoshootred extends autoshoot {

    @Override
    void setTargets()
    {
        firstPosition = new Location(0, 0);
        secondPosition = new Location(7000,14000);
        launchSpeed = MotorSpeeds.NEAR;
        driveBase.resetHeading(65);
    }
}
