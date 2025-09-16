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
    private Servo servo;
    private DcMotorEx kicker;
    private DcMotor feed;

    public ArmBase(HardwareMap hardwareMap){
        servo = hardwareMap.get(Servo.class, "gate");
        kicker = hardwareMap.get(DcMotorEx.class, "shooter");
        feed = hardwareMap.get(DcMotor.class, "feed");
        kicker.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        kicker.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(100, 0, 0,0));
        kicker.setPower(1.0);
    }

    public void setSpeed(MotorSpeeds speed){
        kickSpeed = speed.speed;
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
    public void setKickSpeed(){

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

    public double getKickerVelocity()
    {
        return kicker.getVelocity(AngleUnit.DEGREES);
    }

    private double getGateError(){
        double target = gateClosed ? 1. : 0.;
        return target - servo.getPosition();
    }
}
