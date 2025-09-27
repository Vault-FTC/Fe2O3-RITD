package org.firstinspires.ftc.teamcode.geometry;

import java.util.function.IntSupplier;

public class PoseEstimator {
    // Constants
    double trackWidth = 16.2;
    double strafeOffset = 7.25;
    double ticksPerInch = 0.0013482;

    final private IntSupplier leftOdo, rightOdo, backOdo;
    double deltaLeft, deltaRight, deltaBack;
    private double x = 0;
    private double y = 0;
    private double heading = 0;
    private int prevLeft = 0;
    private int prevRight = 0;
    private int prevBack = 0;


    public PoseEstimator(IntSupplier left, IntSupplier right, IntSupplier back) {
        this.leftOdo = left;
        this.rightOdo = right;
        this.backOdo = back;
    }

    public void resetHeading(double targetAngle)
    {
        heading -= heading;
        heading += Math.toRadians(targetAngle);
    }

    public double getHeading() {
        double deltaTheta = (deltaRight - deltaLeft) / trackWidth;
        heading += deltaTheta;

        return heading;
    }
    public double getGlobalX() {
        return x;
    }
    public double getGlobalY() {
        return y;
    }

    public void update() {
        int currentLeft = leftOdo.getAsInt();
        int currentRight = rightOdo.getAsInt();
        int currentBack = backOdo.getAsInt();

        deltaLeft = currentLeft - prevLeft;
        deltaRight = currentRight - prevRight;
        deltaBack = currentBack - prevBack;

        prevLeft = currentLeft;
        prevRight = currentRight;
        prevBack = currentBack;

        double deltaLeftInches = deltaLeft / ticksPerInch;
        double deltaRightInches = deltaRight / ticksPerInch;
        double deltaBackInches = deltaBack / ticksPerInch;

        double deltaTheta = (deltaRightInches - deltaLeftInches) / trackWidth;

        heading += deltaTheta;

        double localDeltaX = (deltaLeftInches + deltaRightInches) / 2;
        double localDeltaY = deltaBackInches - (deltaTheta * strafeOffset);
        double globalDeltaX = (localDeltaX * Math.cos(heading) - localDeltaY * Math.sin(heading));
        double globalDeltaY = localDeltaX * Math.sin(heading) + localDeltaY * Math.cos(heading);
        x += globalDeltaX;
        y += globalDeltaY;
    }
}