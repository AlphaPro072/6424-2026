package org.firstinspires.ftc.teamcode.util.globals;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

public class Globals {
    public static Follower follower;
    public static Alliance alliance = Alliance.UNKNOWN;
    public static final Pose initPoseRedShooting = new Pose(56, 7.2, 90);
    public static final Pose initPoseRedOther = new Pose(56, 134.1, 270);

    public static Pose getPose() {
        return new Pose(follower.pose().x(), follower.pose().y(), follower.pose().heading());
    }

    public static void setPose(Pose newPose) {
        follower.setPose(new Pose(newPose.x(), newPose.y(), newPose.heading()));
    }
    public static void invertPose() {
        double heading = follower.pose().heading() + Math.PI;
        heading %= 2 * Math.PI;
        follower.setPose(new Pose(144 - follower.pose().x(), 144 - follower.pose().y(), heading));
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
