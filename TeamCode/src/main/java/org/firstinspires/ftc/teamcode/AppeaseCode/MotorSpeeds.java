package org.firstinspires.ftc.teamcode.AppeaseCode;

public enum MotorSpeeds {
    HALF(600),
    FIVE_EIGHTS(740),
    THREE_QUARTERS(900),
    EIGHTY(1000),
    NINETY(1100),
    FULL(1200);

    public final double speed;

    MotorSpeeds(double val)
    {
        speed = val;
    }
}
