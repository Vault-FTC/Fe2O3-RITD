package org.firstinspires.ftc.teamcode.AppeaseCode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;
import org.firstinspires.ftc.teamcode.Autonomous.Location;
import org.firstinspires.ftc.teamcode.LimeLight;

import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

@TeleOp(name = "Teleop Blue")
public class TestOpMode extends OpMode {
    DcMotor[] driveMotors;
    DriveBase driveBase;
    BetterIMU betterIMU;
    MotorSpeeds launchSpeed = MotorSpeeds.HALF;
    ArmBase armBase;
    LimeLight limelight;
    Intake intake;
    boolean last_start;
    boolean last_b;
    boolean last_x;
    boolean last_y;
    boolean last_up;
    boolean last_down;

    void setTargets()
    {
        limelight = new LimeLight(hardwareMap, 20);
    }

    @Override
    public void init(){
        setTargets();
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
        intake = new Intake(hardwareMap);
    }

    @Override
    public void loop(){
        //driveBase.updateValues(telemetry);

        LLResultTypes.FiducialResult result = limelight.getResult();
        double turnVal = 0;
        double distance=-1;

        if(result != null)
        {
            turnVal = result.getTargetXDegrees() * 0.025;
            distance = result.getRobotPoseTargetSpace().getPosition().y;
        }

        if(!gamepad1.right_bumper)
        {
            turnVal=0;
        }
        if (gamepad1.left_bumper) {
            intake.spinIntake(1);
            intake.spinTransfer(1);
        }
        else {
            intake.spinIntake(0);
            intake.spinTransfer(0);
        }
        if (gamepad1.x) {
            intake.spinKicker(1);
        }
        else {
            intake.spinKicker(0);
        }
        if (gamepad1.a) {
            intake.spinKicker(-1);
        }
        else {
            intake.spinKicker(1);
        }
//        if(gamepad1.b) {
//            driveBase.driveToPosition(new Location(0, 0), 0);
//            driveBase.updateValues(telemetry);
//        }
//        else {
        driveBase.drive(turnVal);
//        }

        if (!last_start && gamepad1.start){
            driveBase.toggleFieldCentric();
            driveBase.resetHeading();
        }

//        if (!last_b && gamepad1.b){
//            betterIMU.setDeg(0);
//        }
//       if (!last_x && gamepad1.x){
//            armBase.toggleGate();
//        }
        if (!last_y && gamepad1.y){
            armBase.toggleShooter();
            armBase.setSpeed(launchSpeed);
        }
//        if (gamepad1.a)
//        {
//            intake.spinKicker(1);
//        }
        if(gamepad1.dpad_up && ! last_up) {
            if ((launchSpeed.ordinal() + 1) < MotorSpeeds.values().length) {
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
        driveBase.update();

        telemetry.addData("heading", Math.toDegrees(driveBase.getHeading()));
        telemetry.addData("pose", driveBase.pose);
        telemetry.addData("Centric", driveBase.fieldCentric);
        //telemetry.addData("Kick bool", armBase.spinWheel);
        //telemetry.addData("Gate bool", armBase.gateClosed);
        telemetry.addData( "Kick Speed", armBase.getKickerVelocity());
        driveBase.updateValues(telemetry);
//        telemetry.addData("ResultAngle", betterIMU.getRot().getDeg());
//        telemetry.addData("RealAngle", betterIMU.realRot.getDeg());
//        telemetry.addData("OffsetAngle", betterIMU.offsetRot.getDeg());
    }
}