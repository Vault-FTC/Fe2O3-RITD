package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.CommandSystem.Command;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.MotorSpeeds;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

public class FarShootCommand extends Command {
    Telemetry telemetry;
    Shooter shooter;
    Intake intake;
    MotorSpeeds motorSpeed;
    private double lastKickTime = 0;
    private boolean kickerOn = false;
    private static final double KICK_INTERVAL = 500; // ms
    private final double durationMs;
    private double startTime;

    public FarShootCommand(Shooter shooter, Intake intake, double durationSeconds, Telemetry telemetry, MotorSpeeds motorSpeed) {
        this.shooter = shooter;
        this.intake = intake;
        this.telemetry = telemetry;
        this.motorSpeed = motorSpeed;
        this.durationMs = durationSeconds * 1000;
        addRequirements(this.shooter);
    }

    @Override
    public void initialize() {
        shooter.setShooterSpeed(motorSpeed.speed);
        timer.reset();
        startTime = timer.milliseconds();
        lastKickTime = startTime;
        kickerOn = false;
    }
    @Override
    public void execute() {
        double now = timer.milliseconds();
        double elapsed = now - startTime;

        if (elapsed > 2000) {
            if (now - lastKickTime >= KICK_INTERVAL) {
                kickerOn = !kickerOn;
                lastKickTime = now;
            }
            intake.spinIntake(kickerOn ? 0.75 : 0);
            intake.spinKicker(kickerOn ? 0.75 : 0);
        } else {
            intake.spinIntake(0);
            intake.spinKicker(0);
            shooter.execute(true, motorSpeed.speed);
        }
        telemetry.addData("Running", "Shoot Command");
    }

    public boolean isFinished() {
        return timer.milliseconds() - startTime >= durationMs;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setShooterSpeed(MotorSpeeds.ZERO.speed);
        intake.spinIntake(0);
        intake.spinKicker(0);
    }
}
