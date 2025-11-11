package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.CommandSystem.Command;
import org.firstinspires.ftc.teamcode.subsystems.LimeLight;
import org.firstinspires.ftc.teamcode.subsystems.driveallclass;

public class LimeLightTurnCommand extends Command
{
    driveallclass moved;
    LimeLight light;
    Telemetry tele;
    ElapsedTime tenseistime = new ElapsedTime();

    public LimeLightTurnCommand(driveallclass move, LimeLight lime, Telemetry tele){
        moved = move;
        light = lime;
        this.tele = tele;
    }

    @Override
    public void execute() {
        LLResultTypes.FiducialResult res = light.getResult();
        if(res == null)
        {
            moved.drive(0,0, 0);
            return;
        }
        Pose3D solo_fida = res.getRobotPoseTargetSpace(); // Why Josiah
        double p = 0.05;
        double x = (0 + solo_fida.getPosition().x) * p; // correct = 0;
        double z = (-1.8 + solo_fida.getPosition().z) * p;  // correct = -1.8;
        double yaw = solo_fida.getOrientation().getYaw(AngleUnit.DEGREES);
        moved.drive(0,0, yaw / 9);
        tele.addData("yaw", yaw);
    }

    @Override
    public void initialize() {
        tenseistime.reset();
    }

    @Override
    public boolean isFinished(){
       return tenseistime.seconds() > 200;

    }
}
