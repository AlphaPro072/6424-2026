package org.firstinspires.ftc.teamcode.teleops;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.DriveFunctions;
import org.firstinspires.ftc.teamcode.util.LimelightFunctions;
import org.firstinspires.ftc.teamcode.util.ManipulateFunctions;
import org.firstinspires.ftc.teamcode.util.globals.Alliance;
import org.firstinspires.ftc.teamcode.util.vision.Piece;

@TeleOp(name = "OlyCowTeleOp")
public class OlyCowTeleOp extends OpMode {
    Pose robotPose = Pose.zero();
    DriveFunctions drive = new DriveFunctions();
    ManipulateFunctions manip = new ManipulateFunctions();
    LimelightFunctions LL = new LimelightFunctions();
    Alliance currentAlliance = Alliance.UNKNOWN; // TODO: copy from auton
    Piece trackPiece = Piece.POLLEN;
    boolean fieldCentric = false;
    double targetRPM = 800;

    @Override
    public void init() {
        drive.driveIMUInit(hardwareMap);
        manip.manipulateInit(hardwareMap);
        LL.limelightInit(hardwareMap);

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {
        telemetry.addLine("These adjustments also work when running");
        telemetry.addLine("Press up and down to change launcher RPM");
        if (gamepad1.dpadDownWasPressed()) {
            targetRPM -= 20;
        }
        if (gamepad1.dpadUpWasPressed()) {
            targetRPM += 20;
        }
        telemetry.addData("goal RPM: ", targetRPM);
        telemetry.addLine("Press X to toggle initial piece targeting");
        if (gamepad1.xWasPressed()) {
            trackPiece = trackPiece.other();
            LL.setPieceTracking(trackPiece, currentAlliance);
        }
        telemetry.addData("tracking: ", trackPiece.name());
        telemetry.addLine("Press B to toggle fieldCentric");
        if (gamepad1.bWasPressed()) {
            drive.fieldCentric = !drive.fieldCentric;
        }
        telemetry.addData("fieldCentric: ", fieldCentric);
        telemetry.addLine("Press A to choose alliance, favors red pollen if unknown alliance");
        if (gamepad1.aWasPressed()) {
            if (currentAlliance == Alliance.RED) {
                currentAlliance = Alliance.BLUE;
            } else {
                currentAlliance = Alliance.RED;
            }
        }
        telemetry.addData("alliance", currentAlliance.name());
    }

    @Override
    public void start() {
        LL.limelightStart(currentAlliance);
    }

    @Override
    public void loop() {
        telemetry.addLine("left bumper to intake, right bumper to rev up shooter, both to shoot");
        drive.Drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        telemetry.addLine("Press X to toggle piece targeting");
        if (gamepad1.xWasPressed()) {
            trackPiece = trackPiece.other();
            LL.setPieceTracking(trackPiece, currentAlliance);
        }
        telemetry.addData("tracking: ", trackPiece.name());
        //TODO: robotPose =
        LL.updatePieceMap(robotPose);
        manip.manipulate(gamepad1.left_bumper, gamepad1.right_bumper, targetRPM, telemetry);

        if (gamepad1.dpadDownWasPressed()) {
            targetRPM -= 20;
        }
        if (gamepad1.dpadUpWasPressed()) {
            targetRPM += 20;
        }

        if (gamepad1.bWasPressed()) {
            fieldCentric = !fieldCentric;
        }
        telemetry.addData("fieldCentric: ", fieldCentric);
    }
}
