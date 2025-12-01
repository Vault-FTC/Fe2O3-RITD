package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.LimeLight;

@Autonomous(name = "Red Near", group = "Red Team")
public class RedNearAuto extends BaseNearAuto {

    @Override
    void setTargets()
    {
        LimeLight = new LimeLight(hardwareMap,24);
        launchPosition = new Location(-110, -10, 0);
        firstLaunchPosition = new Location(-90, -10, 0);
        prepareFirstRowArtifacts = new Location(-120, 20, -43);
        collectFirstRowArtifacts = new Location(-90, 95, -43);
        prepareSecondRowArtifacts = new Location(-152,80, -43);
        collectSecondRowArtifacts = new Location(-90, 144, -43);
        prepareCollectThirdRowArtifacts = new Location(-192,124, -43);
        collectionThirdRowArtifacts = new Location(-135,190, -43);
        leaveZonePosition = new Location(-80, 80, -43);

        Location launchPosition = new Location(-110, 10, 0);
        Location firstLaunchPosition = new Location(-90, 10, 0);
        Location prepareFirstRowArtifacts = new Location(-120, -20, 43);
        Location collectFirstRowArtifacts = new Location(-90, -95, 43);
        Location prepareSecondRowArtifacts = new Location(-152,-80, 43);
        Location collectSecondRowArtifacts = new Location(-90, -144, 43);
        Location prepareCollectThirdRowArtifacts = new Location(-192,-124, 43);
        Location collectionThirdRowArtifacts = new Location(-135,-190, 43);
        Location leaveZonePosition = new Location(-80, -80, 43);
    }
}
