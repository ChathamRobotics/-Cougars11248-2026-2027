package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
// roger code
@TeleOp(name = "Teleop")
public class Teleop extends OpMode {

    private DcMotor frontLeft, backLeft, frontRight, backRight, intakeMotor;

    private boolean intakeOn = false;
    private boolean lastBumper = false;

    @Override
    public void init() {
        frontLeft   = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft    = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight");
        backRight   = hardwareMap.get(DcMotor.class, "backRight");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        // ---------- DRIVING ----------
        double forward = -gamepad1.left_stick_y;
        double strafe  = gamepad1.left_stick_x;
        double turn    = gamepad1.right_stick_x;

        double scale = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(turn), 1);

        frontLeft.setPower((forward + strafe + turn) / scale);
        backLeft.setPower((forward - strafe + turn) / scale);
        frontRight.setPower((forward - strafe - turn) / scale);
        backRight.setPower((forward + strafe - turn) / scale);

        // ---------- INTAKE (toggle) ----------
        if (gamepad1.left_bumper && !lastBumper) {
            intakeOn = !intakeOn;        // flips once per press
        }
        lastBumper = gamepad1.left_bumper;

        intakeMotor.setPower(intakeOn ? 1.0 : 0);

        // ---------- TELEMETRY: sticks ----------
        telemetry.addData("left stick x", gamepad1.left_stick_x);
        telemetry.addData("left stick y", gamepad1.left_stick_y);
        telemetry.addData("right stick x", gamepad1.right_stick_x);
        telemetry.addData("right stick y", gamepad1.right_stick_y);
        telemetry.addData("left stick press", gamepad1.left_stick_button);
        telemetry.addData("right stick press", gamepad1.right_stick_button);

        // ---------- TELEMETRY: face buttons ----------
        telemetry.addData("A", gamepad1.a);
        telemetry.addData("B", gamepad1.b);
        telemetry.addData("X", gamepad1.x);
        telemetry.addData("Y", gamepad1.y);

        // ---------- TELEMETRY: bumpers and triggers ----------
        telemetry.addData("left bumper", gamepad1.left_bumper);
        telemetry.addData("right bumper", gamepad1.right_bumper);
        telemetry.addData("left trigger", gamepad1.left_trigger);
        telemetry.addData("right trigger", gamepad1.right_trigger);

        // ---------- TELEMETRY: d-pad ----------
        telemetry.addData("dpad up", gamepad1.dpad_up);
        telemetry.addData("dpad down", gamepad1.dpad_down);
        telemetry.addData("dpad left", gamepad1.dpad_left);
        telemetry.addData("dpad right", gamepad1.dpad_right);

        // ---------- TELEMETRY: wheel and intake powers ----------
        telemetry.addData("frontLeft power", frontLeft.getPower());
        telemetry.addData("backLeft power", backLeft.getPower());
        telemetry.addData("frontRight power", frontRight.getPower());
        telemetry.addData("backRight power", backRight.getPower());
        telemetry.addData("intake power", intakeMotor.getPower());

        telemetry.update();
    }
}