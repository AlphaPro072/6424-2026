package org.firstinspires.ftc.teamcode.util.vision;

import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

public class FindGamePiece {
    private static final double LIMELIGHT_LENS_HEIGHT_INCHES = 7;
    private static final double LIMELIGHT_LENS_FORWARD_INCHES = 6;
    private static final double LIMELIGHT_MOUNT_Y_DEGREES = 0;

    public static double GetGamePieceDistance(LLResultTypes.ColorResult target, Piece targetPiece) {
        double angle = Math.toRadians(LIMELIGHT_MOUNT_Y_DEGREES + target.getTargetYDegrees());
        return ((targetPiece.getRadius() - LIMELIGHT_LENS_HEIGHT_INCHES) / Math.tan(angle)) - LIMELIGHT_LENS_FORWARD_INCHES;
    }

    public static double GetGamePieceHorizontalAngleRadians(LLResultTypes.ColorResult target) {
        return Math.toRadians(target.getTargetXDegrees());
    }

    public static Pose GetGamePiecePositionFieldSpace(Pose robotPose, LLResultTypes.ColorResult target, Piece targetPiece) {
        double distance = GetGamePieceDistance(target, targetPiece);

        double horizontalAngleRadians = GetGamePieceHorizontalAngleRadians(target);
        double forwardDistance = Math.cos(horizontalAngleRadians) * distance;
        double horizontalDistance = Math.sin(horizontalAngleRadians) * distance;

        double xPose = robotPose.x() + (horizontalDistance * Math.cos(-robotPose.heading()) - forwardDistance * Math.sin(-robotPose.heading()));
        double yPose = robotPose.y() + (horizontalDistance * Math.sin(-robotPose.heading()) + forwardDistance * Math.cos(-robotPose.heading()));

        return new Pose(xPose, yPose);
    }
}
