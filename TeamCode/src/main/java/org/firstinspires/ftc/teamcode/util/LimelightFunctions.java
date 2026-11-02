package org.firstinspires.ftc.teamcode.util;

import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.teamcode.util.globals.Alliance;
import org.firstinspires.ftc.teamcode.util.vision.Piece;
import org.firstinspires.ftc.teamcode.util.vision.FindGamePiece;

import java.util.List;

public class LimelightFunctions {
    private Limelight3A limelight;
    private Piece targetPiece = Piece.POLLEN;

    public void limelightInit(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap){
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
    }
    public void limelightStart(Alliance currentAlliance) {
        final int LL_POLL_RATE = 30;
        limelight.pipelineSwitch(targetPiece.getPipeline(currentAlliance));
        limelight.setPollRateHz(LL_POLL_RATE);
        limelight.start();
    }
    public void setPieceTracking(Piece newPiece, Alliance currentAlliance) {
        targetPiece = newPiece;
        limelight.pipelineSwitch(targetPiece.getPipeline(currentAlliance));
    }
    public void pathfind(Pose robotPose) {
        LLResult limelightResult = limelight.getLatestResult();
        if (limelightResult.isValid()) {
            List<LLResultTypes.ColorResult> colorResults = limelightResult.getColorResults();
            for (LLResultTypes.ColorResult result : colorResults) {
                Pose piecePose = FindGamePiece.GetGamePiecePositionFieldSpace(robotPose, result, targetPiece);
            }
        }
    }
}
