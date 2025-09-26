package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimeLight {
    Limelight3A limelight;

    public LimeLight(HardwareMap hardwareMap)
    {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    public LLResultTypes.FiducialResult getResult()
    {
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                for(LLResultTypes.FiducialResult res : result.getFiducialResults()) {
                    if(res.getFiducialId() == 19 || res.getFiducialId() == 20)
                    {
                        return res;
                    }
                }
            }
        }
        return null;
    }
    public double getTx()
    {
        LLResultTypes.FiducialResult res = getResult();
        if(res != null) {
            if(Math.abs(res.getTargetXDegrees()) > 0.5)
            {
                return res.getTargetXDegrees() * 0.05;
            }
        }
        return 0;
    }
}
