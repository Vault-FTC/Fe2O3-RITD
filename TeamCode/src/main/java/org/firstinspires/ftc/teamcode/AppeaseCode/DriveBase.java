package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autonomous.Location;
import org.firstinspires.ftc.teamcode.geometry.PoseEstimator;
import org.firstinspires.ftc.teamcode.geometry.Rotation2D;
import org.firstinspires.ftc.teamcode.geometry.Vector2D;

import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class DriveBase {
    DcMotor[] driveMotors;
    Supplier<Vector2D> transSupp;
    DoubleSupplier rotSupp;
    BetterIMU imu;
    boolean fieldCentric = false;
    IntSupplier odoRight, odoLeft, odoBack;
    PoseEstimator pose;

    public DriveBase(BetterIMU imu, HardwareMap hardwareMap, Gamepad drivePad)
    {
        DcMotorEx rf = hardwareMap.get(DcMotorEx.class, "rf"); // 0
        DcMotorEx lf = hardwareMap.get(DcMotorEx.class, "lf"); // 1
        DcMotorEx rb = hardwareMap.get(DcMotorEx.class, "rb"); // 2
        DcMotorEx lb = hardwareMap.get(DcMotorEx.class, "lb"); // 3

        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        odoRight = () ->  rb.getCurrentPosition();
        odoLeft = () -> lb.getCurrentPosition();
        odoBack = () -> rf.getCurrentPosition();

        driveMotors = new DcMotor[]{lf, rf, lb, rb};

        pose = new PoseEstimator(odoLeft, odoRight, odoBack);

        transSupp = () -> new Vector2D(drivePad.left_stick_x, -drivePad.left_stick_y);
        rotSupp = () -> drivePad.right_stick_x;
        this.imu = imu;
    }
    public void toggleFieldCentric(){
        fieldCentric = !fieldCentric;
    }

    public void driveToPosition(Location target, double turnVal)
    {
        double p = 0.0002;
        double strafe = (target.Strafe - odoBack.getAsInt());
        double forward1 = (target.Forward - odoLeft.getAsInt());
        double forward2 = (target.Forward - odoRight.getAsInt());
        double forward = (forward1 + forward2) / 2;
        if(Math.abs(forward) <  400)
        {
            forward = 0;
        }
        if(Math.abs(strafe) < 400)
        {
            strafe = 0;
        }
        drive(forward * p, strafe * p, turnVal);
    }

    public void drive(double gge)
    {
        Vector2D translation = transSupp.get();
        if (fieldCentric){
            translation.rotateBy(new Rotation2D().setRad(-pose.getHeading()));
        }

        double forward = translation.getY();
        double strafe = translation.getX(); // Right +
        double rotate = rotSupp.getAsDouble() + gge; // Clockwise +
        drive(forward, strafe, rotate);
    }

    public void drive(double forward, double strafe, double rotate){

        double lfPow, rfPow, lbPow, rbPow;
        lfPow = -forward - strafe - rotate;
        rfPow = forward - strafe - rotate;
        lbPow = -forward + strafe - rotate;
        rbPow = forward + strafe - rotate;

        double maxInput = 0;

        for (double motorPow : new double[] {lfPow, rfPow, lbPow, rbPow}){
            maxInput = Math.max(maxInput, Math.abs(motorPow));
        }

        if (maxInput > Constants.DriveConst.maxPower){
            lfPow *= Constants.DriveConst.maxPower / maxInput;
            rfPow *= Constants.DriveConst.maxPower / maxInput;
            lbPow *= Constants.DriveConst.maxPower / maxInput;
            rbPow *= Constants.DriveConst.maxPower / maxInput;
        }

        driveMotors[0].setPower(rfPow);
        driveMotors[1].setPower(lfPow);
        driveMotors[2].setPower(rbPow);
        driveMotors[3].setPower(lbPow);
    }

    public void update()
    {
        pose.update();
    }

    public void resetHeading(double targetAngle)
    {
        pose.resetHeading(targetAngle);
    }

    public void resetHeading()
    {
        resetHeading(0);
    }

    public double getHeading()
    {return pose.getHeading();}
    public void updateValues(Telemetry telemetry)
    {
        //telemetry.addData("Robot backOdo", odoBack.getAsInt() * pose.centimetersPerTick);
        //telemetry.addData("Robot rightOdo", odoRight.getAsInt() * pose.centimetersPerTick);
        //telemetry.addData("Robot leftOdo", odoLeft.getAsInt() * pose.centimetersPerTick);
    }
}
