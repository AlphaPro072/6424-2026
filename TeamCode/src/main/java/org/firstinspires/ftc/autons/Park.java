package org.firstinspires.ftc.autons;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.ivy.pedro.PedroCommands;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.util.globals.Alliance;
import org.firstinspires.ftc.teamcode.util.globals.Globals;

@Autonomous
public class Park extends LinearOpMode {
    private Follower follower;
    private Alliance alliance = Alliance.UNKNOWN;
    @Override
    public void runOpMode() throws InterruptedException {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);

        while (opModeInInit()) {
            if (gamepad1.bWasPressed()) {
                alliance = Alliance.RED;
                follower.setPose(Paths.parkRed().get(0));
                telemetry.addLine("Red alliance");
                telemetry.update();
            }
            if (gamepad1.xWasPressed()) {
                alliance = Alliance.BLUE;
                follower.setPose(Paths.parkBlue().get(0));
                telemetry.addLine("Blue alliance");
                telemetry.update();
            }
        }
        if (opModeIsActive()) {
            if (alliance != Alliance.UNKNOWN) {
                if (alliance == Alliance.RED) {
                    Scheduler.schedule(PedroCommands.follow(follower, Paths.parkRed()));
                }
                else {
                    Scheduler.schedule(PedroCommands.follow(follower, Paths.parkBlue()));
                }
                while (opModeIsActive()) {
                    follower.update();
                    Scheduler.execute();
                    telemetry.addData("x", follower.pose().x());
                    telemetry.addData("y", follower.pose().y());
                    telemetry.addData("heading", follower.pose().heading());
                    telemetry.update();
                }
                Globals.setPose(follower.pose());
                Globals.alliance = this.alliance;
            }
            else {
                throw new InterruptedException("Please specify an alliance");
            }
        }
    }
}
