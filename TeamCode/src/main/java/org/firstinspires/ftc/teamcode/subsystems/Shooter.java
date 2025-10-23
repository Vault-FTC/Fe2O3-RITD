package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Shooter {
    boolean spinKicker = true;
    MotorSpeeds currentSpeed;
    private DcMotorEx kicker;
    private DcMotorEx shooter;
    public Shooter(HardwareMap hardwareMap) {
        kicker = hardwareMap.get(DcMotorEx.class, "kicker");
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(800, 0, 0,0));
        shooter.setPower(1.0);
        shooter.setVelocity(0, AngleUnit.DEGREES);
    }

//    public void shoot()
//    {
//        shooter.setVelocity();
//    }
//    public void intake()
//    {
//
//    }

    public void setShooterSpeed(MotorSpeeds speed){
        currentSpeed = speed;
        shooter.setVelocity(-currentSpeed.speed, AngleUnit.DEGREES);
    }


    public void execute(boolean shoot, MotorSpeeds motorSpeed) {
        if (shoot) {
            setShooterSpeed(motorSpeed);
            if (Math.abs(shooter.getVelocity(AngleUnit.DEGREES) - currentSpeed.speed) < 100) {
                toggleKicker(0.5);
            }
            else {
                toggleKicker(0);
            }
        } else {
            shooter.setVelocity(0);
            kicker.setPower(0);
        }
    }

    public void setShooterSpeedFromAprilTag() {

    }
    public void toggleKicker(double speed)
    {
        kicker.setPower(speed);
    }
//    public void execute(){
////        if (Math.abs(getGateError()) > .1){
////            servo.setPosition(gateClosed ? 1. : 0.);
////        }
//        if (spinShooter){
//            shooter.setVelocity(-shooterSpeed, AngleUnit.DEGREES);
//            //kicker.setPower(-kickSpeed);
//        } else {
//            shooter.setVelocity(0);
//            //kicker.setPower(0);
//        }
//    }

    public double getShooterVelocity()
    {
        return shooter.getVelocity(AngleUnit.DEGREES);
    }

    private double getGateError(){
        return 0;
//        double target = gateClosed ? 1. : 0.;
//        return target - servo.getPosition();
    }
}

