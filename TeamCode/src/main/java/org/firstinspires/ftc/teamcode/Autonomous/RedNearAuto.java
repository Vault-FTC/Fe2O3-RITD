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
        collectFirstRowArtifacts = new Location(-75, 80, -43);
        prepareSecondRowArtifacts = new Location(-152,80, -43);
        collectSecondRowArtifacts = new Location(-90, 144, -43);
        prepareCollectThirdRowArtifacts = new Location(-192,124, -43);
        collectionThirdRowArtifacts = new Location(-135,190, -43);
        leaveZonePosition = new Location(-80, 80, -43);
    }
}
