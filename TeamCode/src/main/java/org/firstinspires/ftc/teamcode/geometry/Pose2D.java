package org.firstinspires.ftc.teamcode.geometry;

public class Pose2D {
    Vector2D translation;
    Rotation2D heading;

    public Pose2D(Vector2D translation, Rotation2D heading){
        this.translation = translation;
        this.heading = heading;
    }

    public Pose2D(){
        this.translation = new Vector2D();
        this.heading = new Rotation2D();
    }

    public Vector2D  getTranslation(){
        return translation.copy();
    }

    public double getX(){
        return translation.getX();
    }

    public double getY(){
        return translation.getY();
    }

    public Vector2D setTranslation(Vector2D translation){
        this.translation = translation;
        return translation;
    }

    public Vector2D addTranslation(Vector2D movement){
        return this.translation.addVector(movement); // Not a reference, addVector returns copy
    }

    public Rotation2D getHeading(){
        return heading.copy();
    }

    public Rotation2D setHeading(Rotation2D heading){
        this.heading = heading;
        return heading.copy();
    }

    public Rotation2D rotateHeadingBy(Rotation2D rot){
        return heading.rotateBy(rot).copy();
    }

    public Rotation2D rotateHeadingByDeg(double deg){
        return heading.rotateByDeg(deg).copy();
    }

    public Rotation2D rotateHeadingByRad(double rad){
        return heading.rotateByRad(rad).copy();
    }

    public Pose2D copy(){
        return new Pose2D(translation.copy(), heading.copy());
    }
}
