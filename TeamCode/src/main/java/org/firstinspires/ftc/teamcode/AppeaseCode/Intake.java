package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private DcMotor intake;
    private DcMotor transfer;
    private DcMotor kicker;
    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotor.class, "intake1");
        transfer = hardwareMap.get(DcMotor.class, "intake2");
        kicker = hardwareMap.get(DcMotor.class, "kicker");
        intake.setDirection(DcMotor.Direction.REVERSE);
        transfer.setDirection(DcMotor.Direction.REVERSE);
        kicker.setDirection(DcMotor.Direction.REVERSE);
    }
    public void spinIntake(double power) {
        intake.setPower(power);
    }
    public void spinTransfer(double power) {
        transfer.setPower(power);
    }
    public void spinKicker(double power) {
        kicker.setPower(power);
    }
}