package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    double maxInput;
    HardwareMap hardwareMap;
    DcMotorEx intake = hardwareMap.get(DcMotorEx.class, "intake");

    public  void runIntake() {
        intake.setPower(Constants.DriveConst.maxPower / maxInput);
    }
}
