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
    Telemetry telemetry;
    Shooter shooter;
    Intake intake;
    MotorSpeeds motorSpeed;
    private final double durationMs;
    private double startTime;

    public ShootCommand(Shooter shooter, Intake intake, double durationSeconds, Telemetry telemetry, MotorSpeeds motorSpeed) {
        this.shooter = shooter;
        this.intake = intake;
        this.telemetry = telemetry;
        this.motorSpeed = motorSpeed;
        this.durationMs = durationSeconds * 1000;
        addRequirements(this.shooter);
    }

    @Override
    public void initialize() {
        shooter.setShooterSpeed(motorSpeed);
        timer.reset();
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
        telemetry.addData("Running", "Shoot Command");
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
