package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class ArmBase {
    boolean spinWheel = false;
    boolean gateClosed = true;

    double kickSpeed;
    double intakeSpeed;
    MotorSpeeds currentSpeed;
    private Servo servo;
    private DcMotorEx shooter;

    public ArmBase(HardwareMap hardwareMap){
//        servo = hardwareMap.get(Servo.class, "gate");
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0,0));
        shooter.setPower(1.0);
        shooter.setVelocity(0, AngleUnit.DEGREES);
    }

    public void setSpeed(MotorSpeeds speed){
        kickSpeed = speed.speed;
        currentSpeed = speed;
    }

    public void setShooterSpeedFromAprilTag() {

    }
    public void setShooter(boolean on)
    {
        spinWheel = on;
    }

    public void toggleShooter(){
        spinWheel = !spinWheel;
    }

    public void toggleGate(){
        gateClosed = ! gateClosed;
    }

    public void toggleIntake(double speed) {
        intakeSpeed = speed;
    }

    //    public void toggleFeed(double input)
//    {
//        kicker.setPower(input);
//    }
    public void execute(){
//        if (Math.abs(getGateError()) > .1){
//            servo.setPosition(gateClosed ? 1. : 0.);
//        }
        if (spinWheel){
            shooter.setVelocity(-kickSpeed);
            //kicker.setPower(-kickSpeed);
        } else {
            shooter.setVelocity(0);
            //kicker.setPower(0);
        }
    }

    public MotorSpeeds getKickerVelocity()
    {
        return currentSpeed;
    }

    private double getGateError(){
        return 0;
//        double target = gateClosed ? 1. : 0.;
//        return target - servo.getPosition();
    }
}