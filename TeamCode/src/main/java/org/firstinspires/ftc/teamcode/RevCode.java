package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.util.List;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import android.util.Size;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.CRServo;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.util.ElapsedTime;

//import java.util.List;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import org.firstinspires.ftc.vision.VisionPortal;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;

//package org.firstinspires.ftc.teamcode;


public class RevCode {
//    package org.firstinspires.ftc.teamcode;


//import android.util.Size;
//
//import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import com.qualcomm.robotcore.hardware.CRServo;
//
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import java.util.List;
//
//import org.firstinspires.ftc.robotcore.external.JavaUtil;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//
//import org.firstinspires.ftc.robotcore.external.navigation.Position;
//
//import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
//
//import org.firstinspires.ftc.vision.VisionPortal;
//
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;


    @TeleOp(name = "CompV3search (Blocks to Java)")

    public class CompV3search extends LinearOpMode {


        private DcMotor frontRight;

        private DcMotor backLeft;

        private DcMotor backRight;

        private DcMotor bigWheel;

        private RevBlinkinLedDriver LED_RevBlinkinLedDriver;

        private DcMotor sideIntake;

        private DcMotor frontLeft;

        private CRServo pusher;

        private DcMotor intake;

        private CRServo feeder;


        int d;

        AprilTagProcessor myAprilTagProcessor;

        ElapsedTime PIDTimer;

        int i;

        double pCoefficient;

        int iCoefficient;

        int dCoefficient;


        /**

         * This function is executed when this Op Mode is selected from the Driver Station.

         */

        @Override

        public void runOpMode() {

            VisionPortal.Builder myVisionPortalBuilder;

            AprilTagProcessor.Builder myAprilTagProcessorBuilder;

            int intake2;

            int outtakeMacro2;

            int wheelDifferential;

            ElapsedTime outtakeMacro;

            ElapsedTime trueWheelMacroTimer;

            int tolerance;

            List possibleWheelPos;

            int PusherMode;

            ElapsedTime timeSinceSpin;

            ElapsedTime wheelMacroTimer;

            int wheelmode;

            double slowMode;

            YawPitchRollAngles cameraOrientation;

            Position cameraPosition;

            VisionPortal myVisionPortal;

            float y;

            double x;

            float rx;

            double denominator;


            frontRight = hardwareMap.get(DcMotor.class, "frontRight");

            backLeft = hardwareMap.get(DcMotor.class, "backLeft");

            backRight = hardwareMap.get(DcMotor.class, "backRight");

            bigWheel = hardwareMap.get(DcMotor.class, "bigWheel");

            LED_RevBlinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, "LED");

            sideIntake = hardwareMap.get(DcMotor.class, "sideIntake");

            frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");

            pusher = hardwareMap.get(CRServo.class, "pusher");

            intake = hardwareMap.get(DcMotor.class, "intake");

            feeder = hardwareMap.get(CRServo.class, "feeder");


            // Reverse the right side motors.  This may be wrong for your setup.

            // If your robot moves backwards when commanded to go forwards, reverse the left side instead.

            frontRight.setDirection(DcMotor.Direction.REVERSE);

            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            backRight.setDirection(DcMotor.Direction.REVERSE);

            backLeft.setDirection(DcMotor.Direction.REVERSE);

            tolerance = 10;

            wheelDifferential = 60;

            outtakeMacro = new ElapsedTime();

            outtakeMacro2 = 0;

            possibleWheelPos = (List) JavaUtil.createListWith();

            PusherMode = 0;

            i = 0;

            d = 0;

            trueWheelMacroTimer = new ElapsedTime();

            timeSinceSpin = new ElapsedTime();

            PIDTimer = new ElapsedTime();

            pCoefficient = 0.0005;

            iCoefficient = 0;

            dCoefficient = 0;

            wheelMacroTimer = new ElapsedTime();

            intake2 = 0;

            wheelmode = 0;

            slowMode = 1;

            ((DcMotorEx) bigWheel).setVelocityPIDFCoefficients(6, 1, 6, 1);

            cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES, 0, -90, 0, 0);

            cameraPosition = new Position(DistanceUnit.CM, 0, -90, 0, 0);

            // Create a new AprilTagProcessor.Builder object and assign it to a variable.

            myAprilTagProcessorBuilder = new AprilTagProcessor.Builder();

            myAprilTagProcessorBuilder.setCameraPose(cameraPosition, cameraOrientation);

            // Build the AprilTag processor and assign it to a variable.

            myAprilTagProcessor = myAprilTagProcessorBuilder.build();

            // Create a VisionPortal.Builder object so you can specify attributes about the cameras.

            myVisionPortalBuilder = new VisionPortal.Builder();

            // Set the camera to the specified webcam name.

            myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam"));

            // Add the AprilTag processor.

            myVisionPortalBuilder.addProcessor(myAprilTagProcessor);

            // Build the VisionPortal object and assign it to a variable.

            myVisionPortal = myVisionPortalBuilder.build();

            LED_RevBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);

            sideIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            waitForStart();

            while (opModeIsActive()) {

                y = gamepad1.left_stick_y * -1;

                x = gamepad1.left_stick_x * 1.1;

                rx = gamepad1.right_stick_x;

                slowMode = 1 - Math.min(0.9, 0.8 * gamepad1.left_trigger + 0.5 * gamepad1.right_trigger);

                denominator = JavaUtil.maxOfList(JavaUtil.createListWith(JavaUtil.sumOfList(JavaUtil.createListWith(Math.abs(y), Math.abs(x), Math.abs(rx))), 1));

                frontLeft.setPower(((y + x + rx) / denominator) * slowMode);

                backLeft.setPower((((y - x) + rx) / denominator) * slowMode);

                frontRight.setPower((((y - x) - rx) / denominator) * slowMode);

                backRight.setPower((((y + x) - rx) / denominator) * slowMode);

                if (gamepad1.leftBumperWasPressed() || gamepad2.leftBumperWasPressed()) {

                    if (Math.abs(intake2) == 1) {

                        pusher.setPower(0);

                        intake2 = 0;

                    } else {

                        intake2 = 1;

                        pusher.setPower(0);

                    }

                } else if (gamepad1.rightBumperWasPressed() || gamepad2.rightBumperWasPressed()) {

                    if (Math.abs(intake2) == 1) {

                        pusher.setPower(0);

                        intake2 = 0;

                    } else {

                        pusher.setPower(0);

                        intake2 = -1;

                    }

                }

                intake.setPower(intake2);

                feeder.setPower(0 + gamepad2.right_trigger * -1);

                sideIntake.setPower(intake2 * -1);

                if (gamepad1.xWasPressed() || gamepad2.xWasPressed()) {

                    if (Math.abs(wheelmode) == 1) {

                        wheelmode = 0;

                        LED_RevBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);

                    } else {

                        wheelmode = 1;

                    }

                }

                if (wheelmode == 1) {

                    RunLEDs();

                }

                if (gamepad1.aWasPressed() || gamepad2.aWasPressed()) {

                    if (Math.abs(PusherMode) == 1) {

                        PusherMode = 0;

                        pusher.setPower(-1);

                    } else {

                        PusherMode = 1;

                        pusher.setPower(1);

                    }

                }

                ((DcMotorEx) bigWheel).setVelocity(wheelmode * 1180);

                telemetry.addData("Intake Power", intake2);

                telemetry.addData("Big Wheel Speed", Math.abs(((DcMotorEx) bigWheel).getVelocity()));

                telemetry.addData("PIDF", ((DcMotorEx) bigWheel).getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));

                telemetry.update();

            }

        }


        /**

         * Describe this function...

         */

        private void resolveAprilTags() {

            ArrayList<AprilTagDetection> myAprilTagDetections;

            AprilTagDetection myAprilTagDetection;


            // Get a list containing the latest detections, which may be stale.

            myAprilTagDetections = myAprilTagProcessor.getDetections();

            telemetry.addData("# AprilTags Detected", JavaUtil.listLength(myAprilTagDetections));

            LED_RevBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);

            for (AprilTagDetection myAprilTagDetection_item : myAprilTagDetections) {

                myAprilTagDetection = myAprilTagDetection_item;

                if (myAprilTagDetection.ftcPose != null) {

                    telemetry.addData("aprilTagX", myAprilTagDetection.robotPose.getPosition().x);

                    telemetry.addData("aprilTagRoll", myAprilTagDetection.robotPose.getOrientation().getRoll());

                    if (Math.abs(myAprilTagDetection.robotPose.getOrientation().getRoll() - 2) < 12 && Math.abs(myAprilTagDetection.robotPose.getPosition().x) < 20) {

                        LED_RevBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);

                        if (Math.abs(myAprilTagDetection.robotPose.getOrientation().getRoll() - 2) < 8 && Math.abs(myAprilTagDetection.robotPose.getPosition().x) < 16) {

                            LED_RevBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED_ORANGE);

                            if (Math.abs(myAprilTagDetection.robotPose.getOrientation().getRoll() - 2) < 4 && Math.abs(myAprilTagDetection.robotPose.getPosition().x) < 10) {

                                LED_RevBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);

                            }

                        }

                    }

                }

            }

        }


        /**

         * Describe this function...

         */

        private void RunLEDs() {

            LED_RevBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);

            if (Math.abs(((DcMotorEx) bigWheel).getVelocity() - 1180) < 200) {

                LED_RevBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.DARK_RED);

                if (Math.abs(((DcMotorEx) bigWheel).getVelocity() - 1180) < 100) {

                    LED_RevBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED_ORANGE);

                    if (Math.abs(((DcMotorEx) bigWheel).getVelocity() - 1180) < 40) {

                        LED_RevBlinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);

                    }

                }

            }

        }


        /**

         * Describe this function...

         */

        private void calculatePIDvalues() {

            double deltaT;

            double currentTime;

            double error;

            double lastTime = 0;

            double p;

            double motorTargetSpeed = 0;

            double lastError = 0;


            currentTime = PIDTimer.milliseconds();

            deltaT = currentTime - lastTime;

            error = motorTargetSpeed - ((DcMotorEx) bigWheel).getVelocity();

            p = error;

            i += error * deltaT;

            d = (int) ((error - lastError) / deltaT);

            if (!(d == d)) {

                d = 0;

            }

            if (!(i == i)) {

                i = 0;

            }

            if (!(p == p)) {

                p = 0;

            }

            lastError = error;

            lastTime = PIDTimer.milliseconds();

            if (motorTargetSpeed == 0) {

                bigWheel.setPower(0);

            } else {

                bigWheel.setPower(motorTargetSpeed / 2140 + p * pCoefficient + iCoefficient * i + d * dCoefficient);

            }

        }

    }
}
