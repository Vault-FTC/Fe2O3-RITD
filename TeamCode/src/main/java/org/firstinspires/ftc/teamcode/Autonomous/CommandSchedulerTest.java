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
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.driveallclass;

@Autonomous
public class CommandSchedulerTest extends LinearOpMode {
    driveallclass drive;
    Shooter shooter;
    Intake intake;
    Location firstPosition = new Location(-130, 0, 0);
    Location secondPosition = new Location(-130, 0, -43);
    Location thirdPosition = new Location(-90, -90, 43);
    Location fourthPosition = new Location(-130, 0, -43);
    Location fifthPosition = new Location(-130, -100, 0);
    CommandScheduler scheduler = CommandScheduler.getInstance();
    Command auto;
    ParallelCommandGroup auto2;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new driveallclass(hardwareMap);
        shooter = new Shooter(hardwareMap);
        intake = new Intake(hardwareMap);
        scheduler.clearRegistry();
        auto = SequentialCommandGroup.getBuilder()
                .add(new DriveToCommand(drive, firstPosition))
                .add(new ShootCommand(shooter, intake, 4))
                .build();
//        auto2 = ParallelCommandGroup.getBuilder()
//                .add(new IntakeCommand(intake, 5000))
//                .add(new DriveToCommand(drive, thirdPosition))
//                .build();

        waitForStart();

        auto.schedule();
//      auto2.schedule();
        while(opModeIsActive()) {
            scheduler.run();
        }
    }
}
