package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CommandSystem.Command;
import org.firstinspires.ftc.teamcode.CommandSystem.CommandScheduler;
import org.firstinspires.ftc.teamcode.CommandSystem.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.CommandSystem.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.DriveToCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.ShootCommand;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.MotorSpeeds;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.driveallclass;

@Autonomous(name = "Blue Near", group = "Blue Team")
public class BaseNearAuto extends LinearOpMode {
    driveallclass drive;
    Shooter shooter;
    Intake intake;
    Location nearShootPosition = new Location(-130, 0, 0);
    Location firstPickupPosition = new Location(-50, -90, 43);
    Location parkPosition = new Location(-130, -100, -0);
    Location secondPickupPosition = new Location(-130,0, 43);
    Location secondPickupPosition2 = new Location(-130, -130, 43);
    CommandScheduler scheduler = CommandScheduler.getInstance();
    Command auto;

    void setTargets() {

    }

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new driveallclass(hardwareMap);
        shooter = new Shooter(hardwareMap);
        intake = new Intake(hardwareMap);
        scheduler.clearRegistry();

        setTargets();

        SequentialCommandGroup auto = SequentialCommandGroup.getBuilder()
                .add(new DriveToCommand(drive, nearShootPosition, telemetry))
                .add(new ShootCommand(shooter, intake, 4, telemetry, MotorSpeeds.NEAR))
                .add(ParallelCommandGroup.getBuilder()
                        .add(new IntakeCommand(intake, 3, telemetry))
                        .add(new DriveToCommand(drive, firstPickupPosition, telemetry))
                        .build()
                )
                .add(new DriveToCommand(drive, nearShootPosition, telemetry))
                .add(new ShootCommand(shooter, intake, 4, telemetry, MotorSpeeds.NEAR))
                .add(new DriveToCommand(drive, secondPickupPosition, telemetry))
                .add(ParallelCommandGroup.getBuilder()
                        .add(new IntakeCommand(intake, 3, telemetry))
                        .add(new DriveToCommand(drive, secondPickupPosition2, telemetry))
                        .build()
                )
                .add(new DriveToCommand(drive, nearShootPosition, telemetry))
                .add(new ShootCommand(shooter, intake, 4, telemetry, MotorSpeeds.NEAR))
                .add(new DriveToCommand(drive, parkPosition, telemetry))
                .build();

        waitForStart();

        auto.schedule();
        while(opModeIsActive()) {
            scheduler.run();
            telemetry.update();
        }
    }
}
