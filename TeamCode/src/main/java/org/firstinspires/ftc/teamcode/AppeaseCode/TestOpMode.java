package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class TestOpMode extends OpMode {
    DcMotor[] driveMotors;
    DriveBase driveBase;
    BetterIMU betterIMU;
    MotorSpeeds launchSpeed = MotorSpeeds.HALF;

    ArmBase armBase;
    boolean last_start;
    boolean last_b;
    boolean last_x;
    boolean last_y;
    boolean last_up;
    boolean last_down;
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

        if (!last_start && gamepad1.start){
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
        if (gamepad1.a)
        {
            armBase.toggleFeed(0.5);
        }
        else
        {
            armBase.toggleFeed(0);
        }
        if (gamepad1.dpad_up && !last_up) {
            if((launchSpeed.ordinal() + 1) < MotorSpeeds.values().length) {
                launchSpeed = MotorSpeeds.values()[launchSpeed.ordinal() + 1];
                armBase.setSpeed(launchSpeed);
            }
        }
        if (gamepad1.dpad_down && !last_down){
            if((launchSpeed.ordinal() - 1) >= 0) {
                launchSpeed = MotorSpeeds.values()[launchSpeed.ordinal() - 1];
                armBase.setSpeed(launchSpeed);
            }
        }

        last_start = gamepad1.start;
        last_b = gamepad1.b;
        last_x = gamepad1.x;
        last_y = gamepad1.y;
        last_up = gamepad1.dpad_up;
        last_down = gamepad1.dpad_down;
        betterIMU.update();
        armBase.execute();
        telemetry.addData("Centric", driveBase.fieldCentric);
        telemetry.addData("Kick bool", armBase.spinWheel);
        telemetry.addData("Gate bool", armBase.gateClosed);
        telemetry.addData( "Kick Speed", armBase.getKickerVelocity());
        telemetry.addData("ResultAngle", betterIMU.getRot().getDeg());
        telemetry.addData("RealAngle", betterIMU.realRot.getDeg());
        telemetry.addData("OffsetAngle", betterIMU.offsetRot.getDeg());
    }
}
