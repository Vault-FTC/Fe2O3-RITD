package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autonomous.Location;
import org.firstinspires.ftc.teamcode.CommandSystem.Command;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.MotorSpeeds;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.driveallclass;

public class ShootCommand extends Command {
    Shooter shooter;
    Intake intake;
    private final double durationMs;
    private double startTime;

    public ShootCommand(Shooter shooter, Intake intake, double durationSeconds) {
        this.shooter = shooter;
        this.intake = intake;
        this.durationMs = durationSeconds * 1000;
        addRequirements(this.shooter);
    }

    @Override
    public void initialize() {
        shooter.setShooterSpeed(MotorSpeeds.NEAR);
        startTime = timer.milliseconds();
    }
    @Override
    public void execute() {
        double elapsed = timer.milliseconds() - startTime;
        if (elapsed > 2000) {
            intake.spinIntake(0.75);
            intake.spinTransfer(0.75);
            intake.spinKicker(0.75);
        } else {
            intake.spinIntake(0);
            intake.spinTransfer(0);
            intake.spinKicker(0);
        }
    }

    public boolean isFinished() {
        return timer.milliseconds() - startTime >= durationMs;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setShooterSpeed(MotorSpeeds.ZERO);
        intake.spinIntake(0);
        intake.spinTransfer(0);
        intake.spinKicker(0);
    }

}
