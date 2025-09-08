package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.geometry.Rotation2D;
import org.firstinspires.ftc.teamcode.geometry.TimeStamp;
import org.firstinspires.ftc.teamcode.geometry.Vector2D;

import java.util.function.Supplier;

public class TestOpMode extends OpMode {
    DcMotor[] driveMotors;
    DriveBase driveBase;
    BetterIMU betterIMU;

    ArmBase armBase;
    boolean last_a;
    boolean last_b;
    boolean last_x;
    boolean last_y;
    @Override
    public void init(){
        DcMotor lf = hardwareMap.get(DcMotor.class, "lf");
        DcMotor rf = hardwareMap.get(DcMotor.class, "rf");
        DcMotor lb = hardwareMap.get(DcMotor.class, "lb");
        DcMotor rb = hardwareMap.get(DcMotor.class, "rb");

        driveMotors = new DcMotor[]{rf, lf, rb, lb};
        for (DcMotor motor : driveMotors){
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        betterIMU = new BetterIMU(hardwareMap);

        driveBase = new DriveBase(betterIMU, new DcMotor[]{lf, rf, lb, rb}, gamepad1);

        armBase = new ArmBase(hardwareMap);
    }

    @Override
    public void loop(){
        driveBase.drive();

        if (!last_a && gamepad1.a){
            driveBase.toggleFieldCentric();
        }
        if (!last_b && gamepad1.b){
            betterIMU.setDeg(0);
        }
        if (!last_x && gamepad1.x){
            armBase.toggleGate();
        }
        if (!last_y && gamepad1.y){
            armBase.toggleKicker();
        }
        last_a = gamepad1.a;
        last_b = gamepad1.b;
        last_x = gamepad1.x;
        last_y = gamepad1.y;
        betterIMU.update();
        armBase.execute();
        telemetry.addData("Centric", driveBase.fieldCentric);
        telemetry.addData("Kick bool", armBase.spinWheel);
        telemetry.addData("Gate bool", armBase.gateClosed);
        telemetry.addData("ResultAngle", betterIMU.getRot().getDeg());
        telemetry.addData("RealAngle", betterIMU.realRot.getDeg());
        telemetry.addData("OffsetAngle", betterIMU.offsetRot.getDeg());
    }
}
