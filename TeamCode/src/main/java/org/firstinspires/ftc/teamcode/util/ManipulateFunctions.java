package org.firstinspires.ftc.teamcode.util;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class ManipulateFunctions {
    private DcMotorEx shooterMotor = null;
    private DcMotor intakeMotor = null;
    private CRServo leftIntake = null;
    private CRServo rightIntake = null;
    private final double PulsesPerRotation = 28.0; // From 6000 RPM yellow jacket motor datasheet
    public final double RPMtoVelocity = PulsesPerRotation/60.0;
    public void manipulateInit(com.qualcomm.robotcore.hardware.HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooter");
        leftIntake = hardwareMap.get(CRServo.class, "leftIntake");
        rightIntake = hardwareMap.get(CRServo.class, "rightIntake");
        shooterMotor.setDirection(DcMotor.Direction.REVERSE);

        intakeMotor.setZeroPowerBehavior(BRAKE);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));

        leftIntake.setPower(0);
        rightIntake.setPower(0);
        leftIntake.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void intake(boolean doIntake) {
        double intakePower = 0;
        if (doIntake) {
            intakePower = 1;
        }
        intakeMotor.setPower(intakePower);
        leftIntake.setPower(intakePower);
        rightIntake.setPower(intakePower);
    }
    public void shoot(boolean doShoot, double targetRPM, org.firstinspires.ftc.robotcore.external.Telemetry telemetry) {
        if (doShoot) {
            shooterMotor.setVelocity(targetRPM * RPMtoVelocity);
            double trueRPM = shooterMotor.getVelocity()/RPMtoVelocity;
            telemetry.addData("True RPM: ", trueRPM);
            telemetry.addData("Goal RPM: ", targetRPM);
        } else {
            shooterMotor.setVelocity(0);
        }
    }
}
