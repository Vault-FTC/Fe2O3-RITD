package org.firstinspires.ftc.teamcode.Autonomous;

public class RedCollectHumanPlayerZone extends BaseCollectHumanPlayerZone {
    @Override
    void setTargets() {
        doNotHitWall = new Location(30, 0, -25);
        farShootPosition = new Location(30, 0, -40);
        humanPlayerPickupPosition = new Location(0, 100, -95);
        parkPosition = new Location(88, 10, -95);
    }
}
