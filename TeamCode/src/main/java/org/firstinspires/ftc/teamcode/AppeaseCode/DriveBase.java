package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.geometry.Rotation2D;
import org.firstinspires.ftc.teamcode.geometry.Vector2D;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class DriveBase {
    DcMotor[] driveMotors;
    Supplier<Vector2D> transSupp;
    DoubleSupplier rotSupp;
    BetterIMU imu;

    boolean fieldCentric = false;
    public DriveBase(BetterIMU imu, DcMotor[] motors, Gamepad drivePad){
        driveMotors = motors;
        transSupp = () -> new Vector2D(drivePad.left_stick_x, -drivePad.left_stick_y);
        rotSupp = () -> drivePad.right_stick_x;
        this.imu = imu;
    }

    public void toggleFieldCentric(){
        fieldCentric = !fieldCentric;
    };

    public void drive(){
        Vector2D translation = transSupp.get();
        if (fieldCentric){
            translation.rotateBy(imu.getRot());
        }
        double forward = translation.getY();
        double strafe = translation.getX(); // Right +
        double rotate = rotSupp.getAsDouble(); // Clockwise +

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
        driveMotors[0].setPower(lfPow);
        driveMotors[1].setPower(rfPow);
        driveMotors[2].setPower(lbPow);
        driveMotors[3].setPower(rbPow);
    }


}
