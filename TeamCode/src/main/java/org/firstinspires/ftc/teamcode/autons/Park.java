package org.firstinspires.ftc.teamcode.autons;

import com.pedropathing.ivy.Scheduler;
import com.pedropathing.ivy.pedro.PedroCommands;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.util.globals.Alliance;
import org.firstinspires.ftc.teamcode.util.globals.Globals;

@Autonomous
public class Park extends LinearOpMode {
    private Alliance alliance = Alliance.UNKNOWN;
    @Override
    public void runOpMode() throws InterruptedException {
        Scheduler.reset();
        Globals.follower = Constants.create(hardwareMap);

        while (opModeInInit()) {
            if (gamepad1.bWasPressed()) {
                alliance = Alliance.RED;
                Globals.follower.setPose(Paths.parkRed().get(0));
            }
            if (gamepad1.xWasPressed()) {
                alliance = Alliance.BLUE;
                Globals.follower.setPose(Paths.parkBlue().get(0));
            }
            telemetry.addData("Telemetry:", alliance.name());
            telemetry.update();
        }
        if (opModeIsActive()) {
            if (alliance != Alliance.UNKNOWN) {
                if (alliance == Alliance.RED) {
                    Scheduler.schedule(PedroCommands.follow(Globals.follower, Paths.parkRed()));
                }
                else {
                    Scheduler.schedule(PedroCommands.follow(Globals.follower, Paths.parkBlue()));
                }
                while (opModeIsActive()) {
                    Globals.follower.update();
                    Scheduler.execute();
                    telemetry.addData("x", Globals.follower.pose().x());
                    telemetry.addData("y", Globals.follower.pose().y());
                    telemetry.addData("heading", Globals.follower.pose().heading());
                    telemetry.update();
                }
                Globals.alliance = this.alliance;
            }
            else {
                throw new InterruptedException("Please specify an alliance");
            }
        }
    }
}
