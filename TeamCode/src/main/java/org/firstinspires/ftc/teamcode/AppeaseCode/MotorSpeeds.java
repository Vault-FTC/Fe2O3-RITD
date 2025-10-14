package org.firstinspires.ftc.teamcode.AppeaseCode;

public enum MotorSpeeds {
    ZERO(0),
//    THREE_EIGHTS(1000),
    NEAR(700),
//    FIVE_EIGHTS(1200),
//    THREE_QUARTERS(1500),
    FAR(1000),
//    NINETY(5400),
//    NINETY_FIVE(1400),
    FULL(1400);

    public final double speed;

    MotorSpeeds(double val)
    {
        speed = val;
    }
}
