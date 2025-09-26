package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;
import org.firstinspires.ftc.teamcode.Autonomous.Location;

import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

@TeleOp
public class TestOpMode extends OpMode {
    DcMotor[] driveMotors;
    DriveBase driveBase;
    BetterIMU betterIMU;
    MotorSpeeds launchSpeed = MotorSpeeds.HALF;
    ArmBase armBase;
    Limelight3A limelight;
    boolean last_start;
    boolean last_b;
    boolean last_x;
    boolean last_y;
    boolean last_up;
    boolean last_down;
    @Override
    public void init(){
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        limelight.pipelineSwitch(0);
        limelight.start();

        // Right Odo - 3
        // Left Odo - 2
        // Back Odo - 1

        DcMotorEx lf = hardwareMap.get(DcMotorEx.class, "lf"); // 0
        DcMotorEx rf = hardwareMap.get(DcMotorEx.class, "rf"); // 1
        DcMotorEx lb = hardwareMap.get(DcMotorEx.class, "lb"); // 2
        DcMotorEx rb = hardwareMap.get(DcMotorEx.class, "rb"); // 3

        IntSupplier rightOdo = () -> rb.getCurrentPosition();
        IntSupplier leftOdo = () -> lb.getCurrentPosition();
        IntSupplier backOdo = () -> rf.getCurrentPosition();

        driveMotors = new DcMotor[]{rf, lf, rb, lb};
        for (DcMotor motor : driveMotors){
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        betterIMU = new BetterIMU(hardwareMap);

        driveBase = new DriveBase(betterIMU, hardwareMap, gamepad1);

        armBase = new ArmBase(hardwareMap);
    }

    @Override
    public void loop(){
        if(gamepad1.b) {
            driveBase.driveToPosition(new Location(0, 0), 0);
            driveBase.updateValues(telemetry);
        }
        else {
            driveBase.drive();
            driveBase.updateValues(telemetry);
        }

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
        if(gamepad1.dpad_left)
        {
            armBase.setSpeed(MotorSpeeds.EIGHTY);
        }
        if(gamepad1.dpad_right)
        {
            armBase.setSpeed(MotorSpeeds.FIVE_EIGHTS);
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

        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                Pose3D botpose = result.getBotpose();
                telemetry.addData("tx", result.getTx());
                telemetry.addData("ty", result.getTy());
                telemetry.addData("Botpose", botpose.toString());
            }
        }
        telemetry.addData("Centric", driveBase.fieldCentric);
        //telemetry.addData("Kick bool", armBase.spinWheel);
        //telemetry.addData("Gate bool", armBase.gateClosed);
        telemetry.addData( "Kick Speed", armBase.getKickerVelocity());
//        telemetry.addData("ResultAngle", betterIMU.getRot().getDeg());
//        telemetry.addData("RealAngle", betterIMU.realRot.getDeg());
//        telemetry.addData("OffsetAngle", betterIMU.offsetRot.getDeg());
    }
}
