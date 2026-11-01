package org.firstinspires.ftc.teamcode.util.vision;

import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

public class FindGamePiece {
    private static final double LIMELIGHT_HEIGHT = 0;
    private static final double LIMELIGHT_MOUNT_Y_DEGREES = 0;

    public static double GetGamePieceDistance(LLResultTypes.ColorResult target, Piece targetPiece) {
        double angle = Math.toRadians(LIMELIGHT_MOUNT_Y_DEGREES + target.getTargetYDegrees());
        return (targetPiece.getRadius() - LIMELIGHT_HEIGHT) / Math.tan(angle);
    }

    public static Pose GetGamePiecePositionFieldSpace(Pose robotPose, LLResultTypes.ColorResult target, Piece targetPiece) {
        double distance = GetGamePieceDistance(target, targetPiece);

        double horizontalAngle = Math.toRadians(target.getTargetXDegrees());
        double forwardDistance = Math.cos(horizontalAngle) * distance;
        double horizontalDistance = Math.sin(horizontalAngle) * distance;

        double xPose = robotPose.x() + (horizontalDistance * Math.cos(-robotPose.heading()) - forwardDistance * Math.sin(-robotPose.heading()));
        double yPose = robotPose.y() + (horizontalDistance * Math.sin(-robotPose.heading()) + forwardDistance * Math.cos(-robotPose.heading()));

        Pose piecePose = new Pose(xPose, yPose);

        return piecePose;
    }
}
