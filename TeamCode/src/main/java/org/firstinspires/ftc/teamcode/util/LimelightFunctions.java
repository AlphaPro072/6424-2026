package org.firstinspires.ftc.teamcode.util;

import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.globals.Globals;
import org.firstinspires.ftc.teamcode.util.vision.Piece;
import org.firstinspires.ftc.teamcode.util.vision.FindGamePiece;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LimelightFunctions {
    private final ElapsedTime runtime = new ElapsedTime();
    private static class knownPiece {
        public Pose piecePose;
        public Piece pieceType;
        public double lastTimeSeenSeconds;
    }
    private Limelight3A limelight;
    private Piece targetPiece = Piece.POLLEN;
    private final ArrayList<knownPiece> pieceMap = new ArrayList<>();

    public void limelightInit(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap){
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
    }
    public void limelightStart() {
        final int LL_POLL_RATE = 90;
        limelight.pipelineSwitch(targetPiece.getPipeline(Globals.alliance));
        limelight.setPollRateHz(LL_POLL_RATE);
        limelight.start();
        runtime.reset();
    }
    public void setPieceTracking(Piece newPiece) {
        targetPiece = newPiece;
        limelight.pipelineSwitch(targetPiece.getPipeline(Globals.alliance));
    }
    private void clearFOV(Pose robotPose) {
        for (Iterator<knownPiece> oldPieceIterator = pieceMap.iterator(); oldPieceIterator.hasNext();) {
            knownPiece oldPiece = oldPieceIterator.next();
            final double VISIBILITY_DIST_INCHES = 65;
            double distanceToPiece = Math.sqrt(Math.pow((robotPose.y()-oldPiece.piecePose.y()), 2) + Math.pow((robotPose.x()-oldPiece.piecePose.x()), 2));
            double headingToPiece = Math.atan2(oldPiece.piecePose.y()-robotPose.y(), oldPiece.piecePose.x()-robotPose.x())-robotPose.heading();
            headingToPiece = ((headingToPiece + Math.PI) - Math.PI) % (2 * Math.PI);
            final double EDGE_CUTOFF_DEG = 4.5;
            final double xFOV_Cutoff = (54.5 - (2 * EDGE_CUTOFF_DEG)) * Math.PI / 180.0;
            boolean inView = Math.abs(headingToPiece) < (xFOV_Cutoff / 2);
            if (inView && distanceToPiece < VISIBILITY_DIST_INCHES) {
                oldPieceIterator.remove();
            }
        }
    }
    private void processColorResult(LLResultTypes.ColorResult result, Pose robotPose) {
        final double AREA_FILTER = 0.04;
        if (result.getTargetArea() > AREA_FILTER) {
            knownPiece newPiece = new knownPiece();
            newPiece.piecePose = FindGamePiece.GetGamePiecePositionFieldSpace(robotPose, result, targetPiece);
            newPiece.pieceType = targetPiece;
            newPiece.lastTimeSeenSeconds = runtime.seconds();
            pieceMap.add(newPiece);
        }
    }
    public void updatePieceMap(Pose robotPose) {
        LLResult limelightResult = limelight.getLatestResult();
        if (limelightResult.isValid()) {
            clearFOV(robotPose);
            List<LLResultTypes.ColorResult> colorResults = limelightResult.getColorResults();
            for (LLResultTypes.ColorResult result : colorResults) {
                processColorResult(result, robotPose);
            }
        }
    }
}
