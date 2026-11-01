package org.firstinspires.ftc.teamcode;

import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.util.globals.Alliance;
import org.firstinspires.ftc.teamcode.util.vision.FindGamePiece;
import org.firstinspires.ftc.teamcode.util.vision.Piece;

import java.util.List;

@TeleOp(name = "LimelightTest")
public class LimelightTest extends OpMode {
    Limelight3A limelight;
    Alliance currentAlliance = Alliance.UNKNOWN;
    Piece targetPiece = Piece.NECTAR;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.addData("Status", "Initialized");
    }
    public void init_loop() {
        telemetry.addLine("must reinitialize to change these");
        telemetry.addLine("Press A to toggle target piece");
        if (gamepad1.aWasPressed()) {
            if (targetPiece == Piece.NECTAR) {
                targetPiece = Piece.POLLEN;
            } else {
                targetPiece = Piece.NECTAR;
            }
        }
        telemetry.addData("targetPiece", targetPiece.name());
        telemetry.addLine("Press B to choose alliance, favors red pollen if unknown alliance");
        if (gamepad1.bWasPressed()) {
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
        limelight.pipelineSwitch(targetPiece.getPipeline(currentAlliance));
        limelight.setPollRateHz(10);
        limelight.start();
    }
    @Override
    public void loop() {
        LLResult limelightResult = limelight.getLatestResult();
        if (limelightResult.isValid()) {
            telemetry.clearAll();
            List<LLResultTypes.ColorResult> colorResults = limelightResult.getColorResults();
            for (LLResultTypes.ColorResult result : colorResults) {
                Pose3D targetPose = result.getCameraPoseTargetSpace();
                telemetry.addLine("Target " + colorResults.indexOf(result) + "\tArea: " + result.getTargetArea());
                telemetry.addLine("Target\tx: " + targetPose.getPosition().x + "\ty: " + targetPose.getPosition().y);
                telemetry.addLine("Distance:" + FindGamePiece.GetGamePieceDistance(result, targetPiece));
                telemetry.addLine("Pose: " + FindGamePiece.GetGamePiecePositionFieldSpace(new Pose(0, 0), result, targetPiece));
                telemetry.addLine();
            }
            telemetry.update();
        }
    }
}
