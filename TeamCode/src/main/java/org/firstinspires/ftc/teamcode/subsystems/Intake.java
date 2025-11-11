package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.CommandSystem.Subsystem;

public class Intake extends Subsystem {
    private DcMotor intake;
    private DcMotor kicker;
    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotor.class, "intake1");
        kicker = hardwareMap.get(DcMotor.class, "kicker");
    }
    public void spinIntake(double power) {
        intake.setPower(power);
    }
    public void spinKicker(double power) {
        kicker.setPower(power);
    }
}