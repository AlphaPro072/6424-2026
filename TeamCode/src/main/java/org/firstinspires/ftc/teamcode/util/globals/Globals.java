package org.firstinspires.ftc.teamcode.util.globals;

import com.pedropathing.math.Pose;

public class Globals {
    private static Alliance alliance = Alliance.UNKNOWN;
    public static final Pose initPoseRedShooting = new Pose(56, 7.2, 90);
    public static final Pose initPoseRedOther = new Pose(56, 134.1, 270);
    private static Pose pose = initPoseRedShooting;

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
        double heading = pose.heading() + Math.PI;
        heading %= 2 * Math.PI;
        pose = new Pose(144 - pose.x(), 144 - pose.y(), heading);
    }
}
