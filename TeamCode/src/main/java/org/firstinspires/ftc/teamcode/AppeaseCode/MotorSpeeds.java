package org.firstinspires.ftc.teamcode.AppeaseCode;

public enum MotorSpeeds {
    HALF(600),
    THREE_QUARTERS(900),
    FULL(1200);

    public final double speed;

    MotorSpeeds(double val)
    {
        speed = val;
    }
}
