package subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.AppeaseCode.MotorSpeeds;

public class Shooter {
    boolean spinShooter = false;
    boolean spinKicker = true;
    double shooterSpeed;
    MotorSpeeds currentSpeed;
    private DcMotorEx kicker;
    private DcMotorEx shooter;
    public Shooter(HardwareMap hardwareMap) {
        kicker = hardwareMap.get(DcMotorEx.class, "kicker");
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0,0));
        shooter.setPower(1.0);
        shooter.setVelocity(0, AngleUnit.DEGREES);
    }

    public void setShooterSpeed(MotorSpeeds speed){
        shooterSpeed = speed.speed;
        currentSpeed = speed;
    }

    public void setShooterSpeedFromAprilTag() {

    }
    public void setShooter(boolean on)
    {
        spinShooter = on;
    }

    public void toggleShooter(){
        spinShooter = !spinShooter;
    }

    public void toggleKicker(double speed)
    {
        kicker.setPower(speed);
    }
    public void execute(){
//        if (Math.abs(getGateError()) > .1){
//            servo.setPosition(gateClosed ? 1. : 0.);
//        }
        if (spinShooter){
            shooter.setVelocity(-shooterSpeed, AngleUnit.DEGREES);
            //kicker.setPower(-kickSpeed);
        } else {
            shooter.setVelocity(0);
            //kicker.setPower(0);
        }
    }

    public MotorSpeeds getKickerVelocity()
    {
        return currentSpeed;
    }

    private double getGateError(){
        return 0;
//        double target = gateClosed ? 1. : 0.;
//        return target - servo.getPosition();
    }
}

