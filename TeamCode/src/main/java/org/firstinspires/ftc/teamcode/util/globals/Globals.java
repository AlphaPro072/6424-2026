package org.firstinspires.ftc.teamcode.util.globals;

import com.pedropathing.math.Pose;

public class Globals {
    public static Alliance alliance = Alliance.UNKNOWN;
    public static final Pose initPoseRedShooting = new Pose(56, 7.2, 90);
    public static final Pose initPoseRedOther = new Pose(56, 134.1, 270);
    private static Pose pose = initPoseRedShooting;

    public static Pose getPose() {
        return new Pose(pose.x(), pose.y(), pose.heading());
    }

    public static void setPose(Pose newPose) {
        pose = new Pose(newPose.x(), newPose.y(), newPose.heading());
    }
    public static Pose invertPose(Pose oldPose) {
        double heading = oldPose.heading() + Math.PI;
        heading %= 2 * Math.PI;
        return new Pose(144 - oldPose.x(), 144 - oldPose.y(), heading);
    }

    public static Pose invertPoseNoHeading(Pose oldPose) {
        return new Pose(144 - oldPose.x(), 144 - oldPose.y());
    }

    public static double invertHeading(double oldHeading) {
        double heading = oldHeading + Math.PI;
        heading %= 2 * Math.PI;
        return heading;
    }
}
