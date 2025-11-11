package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.LimeLight;

@Autonomous(name = "Red Near", group = "Red Team")
public class RedNearAuto extends BaseNearAuto {

    @Override
    void setTargets()
    {
        LimeLight = new LimeLight(hardwareMap,24);
        launchPosition = new Location(-110, 0, 0);
        openGatePositions = new Location(-70, 90, -43);
        collectSecondRowArtifacts = new Location(-130, 100, 0);
        collectionThirdRowArtifacts = new Location(-140,100, -43);
        leaveZonePosition = new Location(-90, 130, -43);
    }
}
