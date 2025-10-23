package org.firstinspires.ftc.teamcode.subsystems;

public enum MotorSpeeds {
    ZERO(0),
//    THREE_EIGHTS(1000),
    NEAR(800),
//    FIVE_EIGHTS(1200),
//    THREE_QUARTERS(1500),
    FAR(1750),
//    NINETY(5400),
//    NINETY_FIVE(1400),
    FULL(2000);

    public final double speed;

    MotorSpeeds(double val)
    {
        speed = val;
    }
}
