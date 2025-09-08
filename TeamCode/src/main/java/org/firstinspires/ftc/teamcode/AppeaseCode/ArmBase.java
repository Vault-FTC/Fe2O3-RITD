package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.nio.charset.CharacterCodingException;

public class ArmBase {
    boolean spinWheel = false;
    boolean gateClosed = true;
    private Servo servo;
    private DcMotor kicker;
    public ArmBase(HardwareMap hardwareMap){
        servo = hardwareMap.get(Servo.class, "gate");
        kicker = hardwareMap.get(DcMotor.class, "kick");
        kicker.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void toggleKicker(){
        spinWheel = !spinWheel;
    }

    public void toggleGate(){
        gateClosed = ! gateClosed;
    }

    public void execute(){
        if (Math.abs(getGateError()) > .1){
            servo.setPosition(gateClosed ? 1. : 0.);
        }
        if (spinWheel){
            kicker.setPower(-.8);
        } else {
            kicker.setPower(0);
        }
    }

    private double getGateError(){
        double target = gateClosed ? 1. : 0.;
        return target - servo.getPosition();
    }
}
