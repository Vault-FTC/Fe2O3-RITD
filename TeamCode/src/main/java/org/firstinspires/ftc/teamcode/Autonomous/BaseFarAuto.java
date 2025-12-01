package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CommandSystem.Command;
import org.firstinspires.ftc.teamcode.CommandSystem.CommandScheduler;
import org.firstinspires.ftc.teamcode.CommandSystem.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.CommandSystem.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.DriveToCommand;
import org.firstinspires.ftc.teamcode.Commands.FarShootCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.LimeLightTurnCommand;
import org.firstinspires.ftc.teamcode.Commands.TimedShootCommand;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.LimeLight;
import org.firstinspires.ftc.teamcode.subsystems.MotorSpeeds;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.driveallclass;

@Autonomous(name = "Blue Far", group = "Blue Team")
public class BaseFarAuto extends LinearOpMode {
    driveallclass drive;
    Shooter shooter;
    Intake intake;
    LimeLight LimeLight;
    Location doNotHitWall = new Location(30, 0, 25);
    Location farShootPosition = new Location(25, 0, 40);
    Location firstPickupPosition = new Location(80, 0, 95);
    Location firstPickupPosition2 = new Location(70,-130, 95);
    Location secondPickupPosition = new Location(120, 0, 95);
    Location secondPickupPosition2 = new Location(110,-130, 95);
    Location parkPosition = new Location(88, -10, 95);
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
        LimeLight = new LimeLight(hardwareMap,20);
        setTargets();

        SequentialCommandGroup auto = SequentialCommandGroup.getBuilder()
                .add(new DriveToCommand(drive, doNotHitWall, telemetry))
                .add(new DriveToCommand(drive, farShootPosition, telemetry))
                .add(new LimeLightTurnCommand(drive,LimeLight, telemetry))
                .add(new FarShootCommand(shooter, intake, 4, telemetry, MotorSpeeds.FAR))
                .add(new DriveToCommand(drive, firstPickupPosition, telemetry))
                .add(ParallelCommandGroup.getBuilder()
                        .add(new IntakeCommand(intake, 2, telemetry))
                        .add(new DriveToCommand(drive, firstPickupPosition2, telemetry))
                        .build()
                )
                .add(new DriveToCommand(drive, farShootPosition, telemetry))
                .add(new LimeLightTurnCommand(drive,LimeLight, telemetry))
                .add(new FarShootCommand(shooter, intake, 4, telemetry, MotorSpeeds.FAR))
                .add(new DriveToCommand(drive, secondPickupPosition, telemetry)) 
                .add(ParallelCommandGroup.getBuilder()
                        .add(new IntakeCommand(intake, 2, telemetry))
                        .add(new DriveToCommand(drive, secondPickupPosition2, telemetry))
                        .build()
                )
                .add(new DriveToCommand(drive, farShootPosition, telemetry))
                .add(new LimeLightTurnCommand(drive,LimeLight, telemetry))
                .add(new FarShootCommand(shooter, intake, 4, telemetry, MotorSpeeds.FAR))
                .add(new DriveToCommand(drive, parkPosition, telemetry))
                .build();

        waitForStart();

        auto.schedule();
        while(opModeIsActive()) {
            scheduler.run();
            telemetry.addData("Position", drive.getPosition());
            telemetry.update();
        }
    }
}
