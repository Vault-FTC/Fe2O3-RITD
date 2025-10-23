package org.firstinspires.ftc.teamcode.Old;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.geometry.Rotation2D;

public class BetterIMU {
    IMU realImu;
    Rotation2D realRot;
    Rotation2D offsetRot;
    public BetterIMU(HardwareMap hardwareMap, Rotation2D startOffset){
        realImu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot RHOON = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD);
        realImu.initialize(new IMU.Parameters(RHOON));
        realRot = new Rotation2D((realImu.getRobotYawPitchRollAngles().getYaw() + 180) * 2 * Math.PI);
        offsetRot = new Rotation2D(startOffset.getRad() - realRot.getRad());
    }

    public BetterIMU(HardwareMap hardwareMap){
        this(hardwareMap, new Rotation2D(0));
    }

    /**
     * @param rot is the rotation that the gyro will be. Rotates the offset to make the next value match rot
     */
    public void setRot(Rotation2D rot){
        offsetRot.setRad(rot.getRad() - getRot().getRad());
    }

    /**
     * @param rad is the rotation that the gyro will be. Rotates the offset to make the next value match rad
     */
    public void setRad(double rad){
        setRot(new Rotation2D(rad));
    }

    /**
     * @param deg is the rotation that the gyro will be. Rotates the offset to make the next value match deg
     */
    public void setDeg(double deg){
        setRot(new Rotation2D(0).rotateByDeg(deg));
    }

    public Rotation2D rotateBy(Rotation2D rot){
        return offsetRot.rotateBy(rot).copy();
    }

    public Rotation2D rotateByRad(double rad){
        return offsetRot.rotateByRad(rad).copy();
    }

    public Rotation2D rotateByDeg(double deg){
        return offsetRot.rotateByDeg(deg).copy();
    }

    /**
     * @return the direct output from the gyro
     */
    public Rotation2D getRealGyroRot(){
        return realRot;
    }

    /**
     * @return the offset rotation.
     * This should be called the majority of the time.
     */
    public Rotation2D getRot(){
        return realRot.copy().rotateBy(offsetRot);
    }

    public void update(){
        realRot.setRad((realImu.getRobotYawPitchRollAngles().getYaw() + 180) * 2 * Math.PI);
    }

}
