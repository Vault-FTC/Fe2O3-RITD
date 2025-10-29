package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandSystem.Command;
import org.firstinspires.ftc.teamcode.CommandSystem.CommandScheduler;
import org.firstinspires.ftc.teamcode.CommandSystem.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.CommandSystem.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.Commands.DriveToCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.LimeLightTurnCommand;
import org.firstinspires.ftc.teamcode.Commands.ShootCommand;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.LimeLight;
import org.firstinspires.ftc.teamcode.subsystems.MotorSpeeds;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.driveallclass;

@Autonomous(name = "Blue Far", group = "Blue Team")
public class BaseFarAuto extends BaseNearAuto {
    Location farShootPosition = new Location(0, 0, 0);
    Location firstPickupPosition = new Location(80, 0, 75);
    Location firstPickupPosition2 = new Location(80,-100, 75);
    Location parkPosition = new Location(88, 0, 0);
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
        LimeLight = new LimeLight(hardwareMap,24);
        setTargets();

        SequentialCommandGroup auto = SequentialCommandGroup.getBuilder()
                .add(new DriveToCommand(drive, farShootPosition, telemetry))
                .add(new LimeLightTurnCommand(drive,LimeLight, telemetry))
                .add(new ShootCommand(shooter, intake, 4, telemetry, MotorSpeeds.FAR))
                .add(new DriveToCommand(drive, firstPickupPosition, telemetry))
                .add(ParallelCommandGroup.getBuilder()
                        .add(new IntakeCommand(intake, 3, telemetry))
                        .add(new DriveToCommand(drive, firstPickupPosition2, telemetry))
                        .build()
                )
                .add(new DriveToCommand(drive, farShootPosition, telemetry))
                .add(new LimeLightTurnCommand(drive,LimeLight, telemetry))
                .add(new ShootCommand(shooter, intake, 4, telemetry, MotorSpeeds.FAR))
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
