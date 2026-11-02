package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.DriveFunctions;
import org.firstinspires.ftc.teamcode.util.LimelightFunctions;
import org.firstinspires.ftc.teamcode.util.ManipulateFunctions;
import org.firstinspires.ftc.teamcode.util.globals.Alliance;
import org.firstinspires.ftc.teamcode.util.globals.Globals;
import org.firstinspires.ftc.teamcode.util.vision.Piece;

@TeleOp(name = "OlyCowTeleOp")
public class OlyCowTeleOp extends OpMode {
    DriveFunctions drive = new DriveFunctions();
    ManipulateFunctions manip = new ManipulateFunctions();
    LimelightFunctions LL = new LimelightFunctions();
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
            LL.setPieceTracking(trackPiece, Globals.getAlliance());
        }
        telemetry.addData("tracking: ", trackPiece.name());
        telemetry.addLine("Press B to toggle fieldCentric");
        if (gamepad1.bWasPressed()) {
            drive.fieldCentric = !drive.fieldCentric;
        }
        telemetry.addData("fieldCentric: ", fieldCentric);
        telemetry.addLine("Press A to choose alliance, favors red pollen if unknown alliance");
        if (gamepad1.aWasPressed()) {
            if (Globals.getAlliance() == Alliance.RED) {
                Globals.setAlliance(Alliance.BLUE);
                Globals.invertPose();
            } else if (Globals.getAlliance() == Alliance.BLUE){
                Globals.setAlliance(Alliance.RED);
                Globals.invertPose();
            } else {
                Globals.setAlliance(Alliance.RED);
                Globals.setPose(Globals.initPoseRed);
            }
        }
        telemetry.addData("alliance", Globals.getAlliance().name());
    }

    @Override
    public void start() {
        LL.limelightStart(Globals.getAlliance());
    }

    @Override
    public void loop() {
        telemetry.addLine("left bumper to intake, right bumper to rev up shooter, both to shoot");
        drive.Drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        telemetry.addLine("Press X to toggle piece targeting");
        if (gamepad1.xWasPressed()) {
            trackPiece = trackPiece.other();
            LL.setPieceTracking(trackPiece, Globals.getAlliance());
        }
        telemetry.addData("tracking: ", trackPiece.name());
        LL.updatePieceMap(Globals.getPose());
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
