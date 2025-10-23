package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Autonomous.Location;
import org.firstinspires.ftc.teamcode.CommandSystem.Command;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.MotorSpeeds;
import org.firstinspires.ftc.teamcode.subsystems.driveallclass;

public class IntakeCommand extends Command {
    private final Intake intake;

    private final double durationMs;
    private double startTime;

    public IntakeCommand(Intake intake, double durationMs) {
        this.intake = intake;
        this.durationMs = durationMs;
        addRequirements(this.intake);
    }

    @Override
    public void initialize() {
        startTime = timer.milliseconds();
    }

    @Override
    public void execute() {
        intake.spinIntake(0.75);
        intake.spinTransfer(0.75);
        intake.spinKicker(-0.75);
    }

    public boolean isFinished() {
        return timer.milliseconds() - startTime >= durationMs;
    }

    @Override
    public void end(boolean interrupted) {
        intake.spinIntake(0);
        intake.spinTransfer(0);
        intake.spinKicker(0);
    }
}
