package org.firstinspires.ftc.teamcode.util.globals;

import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.util.CoordinateInverter;

public class Globals {
    private static Alliance alliance = Alliance.UNKNOWN;
    public static Pose initPoseRed = new Pose(0, 0, 0); //TODO: get real value
    private static Pose pose = initPoseRed;

    public static Alliance getAlliance() {
        return alliance;
    }

    public static void setAlliance(Alliance newAlliance) {
        alliance = newAlliance;
    }

    public static Pose getPose() {
        return new Pose(pose.x(), pose.y(), pose.heading());
    }

    public static void setPose(Pose newPose) {
        pose = new Pose(newPose.x(), newPose.y(), newPose.heading());
    }
    public static void invertPose() {
        pose = CoordinateInverter.invert(pose);
    }
}
