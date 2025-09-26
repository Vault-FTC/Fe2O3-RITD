package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.nio.charset.CharacterCodingException;

public class ArmBase {
    boolean spinWheel = false;
    boolean gateClosed = true;
    double kickSpeed;
    MotorSpeeds currentSpeed;
    private Servo servo;
    private DcMotorEx kicker;
    private DcMotor feed;

    public ArmBase(HardwareMap hardwareMap){
        servo = hardwareMap.get(Servo.class, "gate");
        kicker = hardwareMap.get(DcMotorEx.class, "shooter");
        feed = hardwareMap.get(DcMotor.class, "feed");
        kicker.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        kicker.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        kicker.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0,0));
        kicker.setPower(1.0);
        kicker.setVelocity(0, AngleUnit.DEGREES);
    }

    public void setSpeed(MotorSpeeds speed){
        kickSpeed = speed.speed;
        currentSpeed = speed;
    }

    public void setKicker(boolean on)
    {
       spinWheel = on;
    }

    public void toggleKicker(){
        spinWheel = !spinWheel;
    }

    public void toggleGate(){
        gateClosed = ! gateClosed;
    }

    public void toggleFeed(double input)
    {
        feed.setPower(input);
    }
    public void execute(){
        if (Math.abs(getGateError()) > .1){
            servo.setPosition(gateClosed ? 1. : 0.);
        }
        if (spinWheel){
            kicker.setVelocity(-kickSpeed, AngleUnit.DEGREES);
            //kicker.setPower(-kickSpeed);
        } else {
            kicker.setVelocity(0);
            //kicker.setPower(0);
        }
    }

    public MotorSpeeds getKickerVelocity()
    {
        return currentSpeed;
    }

    private double getGateError(){
        double target = gateClosed ? 1. : 0.;
        return target - servo.getPosition();
    }
}
