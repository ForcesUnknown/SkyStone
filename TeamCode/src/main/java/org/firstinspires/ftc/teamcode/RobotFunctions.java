package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.util.Range;

/**
    This file contains the underlying functions to be used during the 2020/2021 season
**/


@Disabled
public abstract class RobotFunctions extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();

    //region Servo

    public void SetServoPosition(Servo servo, double position)
    {
        servo.setPosition(position);
    }

    public void SetServoPosition(ServoData servoData)
    {
        servoData.servo.setPosition(servoData.startPosition);
    }

    //endregion

    //region Time

    public void TurnMotorTime(DcMotor motor, double power, long time)
    {
        motor.setPower(power);
        sleep(time);
        motor.setPower(0);
    }

    public void DriveAngleTime(DriveBaseData driveBaseData, double power, long time, double angle)
    {
        Vector move = AngleToVector(angle);

        double left = Clamp((move.x + move.y) * power, -1, 1);
        double right = Clamp((move.x - move.y) * power, -1, 1);

        driveBaseData.SetPower(left, right, right, left);

        sleep(time);

        driveBaseData.SetPower(0);
    }

    public void DriveFrontBackTime(DriveBaseData driveBaseData, double power, long time)
    {
        driveBaseData.SetPower(power);

        sleep(time);

        driveBaseData.SetPower(0);
    }

    public void DriveLeftRightTime(DriveBaseData driveBaseData, double power, long time)
    {
        driveBaseData.SetPower(power, -power, -power, power);

        sleep(time);

        driveBaseData.SetPower(0);
    }

    //endregion

    //region Encoders

    public void TurnMotorDistance(DcMotor motor, double power, double distance, double timeoutRedundancy, double wheelDiameter, boolean tetrix)
    {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int encoderTicksPerRotation = tetrix? 1440 : 28;
        double wheelCircumference = wheelDiameter * Math.PI;
        double ticksPerCentimeter = encoderTicksPerRotation / wheelCircumference;

        int newPosition = (int)(motor.getCurrentPosition() + Math.round(distance * ticksPerCentimeter));

        motor.setTargetPosition(newPosition);

        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor.setPower(Math.abs(power));

        runtime.reset();

        while (opModeIsActive() && runtime.time() < timeoutRedundancy && motor.isBusy())
        {
            telemetry.addLine("Motor: Running");

            telemetry.update();
        }

        telemetry.addLine("Motor: Complete");

        telemetry.update();

        motor.setPower(0);

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(100);
    }

    public void DriveAngleDistance(DriveBaseData driveBaseData, double power, double distance, double angle, double timeoutRedundancy)
    {
        driveBaseData.SetMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Vector move = AngleToVector(angle);

        double left = Clamp((move.x + move.y), -1, 1);
        double right = Clamp((move.x - move.y), -1, 1);

        int newPositionLeftFront = (int)(driveBaseData.leftFront.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter * left));
        int newPositionRightFront = (int)(driveBaseData.rightFront.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter * right));
        int newPositionLeftBack = (int)(driveBaseData.leftBack.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter * right));
        int newPositionRightBack = (int)(driveBaseData.rightBack.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter * left));

        driveBaseData.SetTargetPosition(newPositionLeftFront, newPositionRightFront, newPositionLeftBack, newPositionRightBack);

        driveBaseData.SetMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveBaseData.SetPower(Math.abs(power));

        runtime.reset();

        while (opModeIsActive() && runtime.time() < timeoutRedundancy && (driveBaseData.leftFront.isBusy() && driveBaseData.rightFront.isBusy() && driveBaseData.leftBack.isBusy() && driveBaseData.rightBack.isBusy()))
        {
            telemetry.addLine("Motors: Running");

            telemetry.update();
        }

        telemetry.addLine("Motors: Complete");

        telemetry.update();

        driveBaseData.SetPower(0);

        driveBaseData.SetMode(DcMotor.RunMode.RUN_USING_ENCODER);


        sleep(100);
    }

    public void DriveFrontBackDistance(DriveBaseData driveBaseData, double power, double distance, double timeoutRedundancy)
    {
        driveBaseData.SetMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int newPositionLeftFront = (int)(driveBaseData.leftFront.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter));
        int newPositionRightFront = (int)(driveBaseData.rightFront.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter));
        int newPositionLeftBack = (int)(driveBaseData.leftBack.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter));
        int newPositionRightBack = (int)(driveBaseData.rightBack.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter));

        driveBaseData.SetTargetPosition(newPositionLeftFront, newPositionRightFront, newPositionLeftBack, newPositionRightBack);

        driveBaseData.SetMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveBaseData.SetPower(Math.abs(power));

        runtime.reset();

        while (opModeIsActive() && runtime.time() < timeoutRedundancy && (driveBaseData.leftFront.isBusy() && driveBaseData.rightFront.isBusy() && driveBaseData.leftBack.isBusy() && driveBaseData.rightBack.isBusy()))
        {
            telemetry.addLine("Motors: Running");

            telemetry.update();
        }

        telemetry.addLine("Motors: Complete");

        telemetry.update();

        driveBaseData.SetPower(0);

        driveBaseData.SetMode(DcMotor.RunMode.RUN_USING_ENCODER);


        sleep(100);

    }

    public void DriveLeftRightDistance(DriveBaseData driveBaseData, double power, double distance, double timeoutRedundancy)
    {
        driveBaseData.SetMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int newPositionLeftFront = (int)(driveBaseData.leftFront.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter));
        int newPositionRightFront = -(int)(driveBaseData.rightFront.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter));
        int newPositionLeftBack = -(int)(driveBaseData.leftBack.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter));
        int newPositionRightBack = (int)(driveBaseData.rightBack.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter));

        driveBaseData.SetTargetPosition(newPositionLeftFront, newPositionRightFront, newPositionLeftBack, newPositionRightBack);

        driveBaseData.SetMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveBaseData.SetPower(Math.abs(power));

        runtime.reset();

        while (opModeIsActive() && runtime.time() < timeoutRedundancy && (driveBaseData.leftFront.isBusy() && driveBaseData.rightFront.isBusy() && driveBaseData.leftBack.isBusy() && driveBaseData.rightBack.isBusy()))
        {
            telemetry.addLine("Motors: Running");

            telemetry.update();
        }

        telemetry.addLine("Motors: Complete");

        telemetry.update();

        driveBaseData.SetPower(0);

        driveBaseData.SetMode(DcMotor.RunMode.RUN_USING_ENCODER);


        sleep(100);

    }

    //endregion

    //region ColourSensor

    public void TurnMotorColour(DcMotor motor, double power, Vector colour, RevColorSensorV3 colourSensor, double timeoutRedundancy)
    {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor.setPower(power);

        runtime.reset();

        NormalizedRGBA colourSensorColour = colourSensor.getNormalizedColors();
        Vector c = new Vector(colourSensorColour.red, colourSensorColour.green, colourSensorColour.blue);

        while (opModeIsActive() && runtime.time() < timeoutRedundancy && ColourEqual(colour, c, 40))
        {
            colourSensorColour = colourSensor.getNormalizedColors();
            c = new Vector(colourSensorColour.red, colourSensorColour.green, colourSensorColour.blue);

            telemetry.addLine("Motor: Running");
            telemetry.addLine("Red: " + c.x + " Green: " + c.y + " Blue: " + c.z);

            telemetry.update();
        }

        telemetry.addLine("Motor: Complete");

        telemetry.update();

        motor.setPower(0);

        sleep(100);
    }

    public void DriveAngleColour(DriveBaseData driveBaseData, double power, double angle, Vector colour, RevColorSensorV3 colourSensor, double timeoutRedundancy)
    {
        driveBaseData.SetMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Vector move = AngleToVector(angle);

        double left = Clamp((move.x + move.y) * power, -1, 1);
        double right = Clamp((move.x - move.y) * power, -1, 1);

        driveBaseData.SetPower(left, right, right, left);

        runtime.reset();

        NormalizedRGBA colourSensorColour = colourSensor.getNormalizedColors();
        Vector c = new Vector(colourSensorColour.red, colourSensorColour.green, colourSensorColour.blue);

        while (opModeIsActive() && runtime.time() < timeoutRedundancy && ColourEqual(colour, c, 40))
        {
            colourSensorColour = colourSensor.getNormalizedColors();
            c = new Vector(colourSensorColour.red, colourSensorColour.green, colourSensorColour.blue);

            telemetry.addLine("Motors: Running");
            telemetry.addLine("Red: " + c.x + " Green: " + c.y + " Blue: " + c.z);

            telemetry.update();
        }

        telemetry.addLine("Motors: Complete");

        telemetry.update();

        driveBaseData.SetPower(0);

        sleep(100);
    }

    public void DriveFrontBackColour(DriveBaseData driveBaseData, double power, Vector colour, RevColorSensorV3 colourSensor, double timeoutRedundancy)
    {
        driveBaseData.SetMode(DcMotor.RunMode.RUN_USING_ENCODER);

        driveBaseData.SetPower(power);

        runtime.reset();

        NormalizedRGBA colourSensorColour = colourSensor.getNormalizedColors();
        Vector c = new Vector(colourSensorColour.red, colourSensorColour.green, colourSensorColour.blue);

        while (opModeIsActive() && runtime.time() < timeoutRedundancy && ColourEqual(colour, c, 40))
        {
            colourSensorColour = colourSensor.getNormalizedColors();
            c = new Vector(colourSensorColour.red, colourSensorColour.green, colourSensorColour.blue);

            telemetry.addLine("Motors: Running");
            telemetry.addLine("Red: " + c.x + " Green: " + c.y + " Blue: " + c.z);

            telemetry.update();
        }

        telemetry.addLine("Motors: Complete");

        telemetry.update();

        driveBaseData.SetPower(0);

        sleep(100);
    }

    public void DriveLeftRightColour(DriveBaseData driveBaseData, double power, Vector colour, RevColorSensorV3 colourSensor, double timeoutRedundancy)
    {
        driveBaseData.SetMode(DcMotor.RunMode.RUN_USING_ENCODER);

        driveBaseData.SetPower(power, -power, -power, power);

        runtime.reset();

        NormalizedRGBA colourSensorColour = colourSensor.getNormalizedColors();
        Vector c = new Vector(colourSensorColour.red, colourSensorColour.green, colourSensorColour.blue);

        while (opModeIsActive() && runtime.time() < timeoutRedundancy && ColourEqual(colour, c, 40))
        {
            colourSensorColour = colourSensor.getNormalizedColors();
            c = new Vector(colourSensorColour.red, colourSensorColour.green, colourSensorColour.blue);

            telemetry.addLine("Motors: Running");
            telemetry.addLine("Red: " + c.x + " Green: " + c.y + " Blue: " + c.z);

            telemetry.update();
        }

        telemetry.addLine("Motors: Complete");

        telemetry.update();

        driveBaseData.SetPower(0);

        sleep(100);
    }

    //endregion

    //region Utility

    private double Clamp(double value, double a, double b)
    {
        if(value > a)
            return a;
        else if(value < b)
            return b;

         return value;
    }

    private Vector AngleToVector(double angle)
    {
        Vector move = new Vector(0 , 0);

        Math.toRadians(angle);
        move.y = Math.sin(angle);
        move.x = Math.sqrt(1 - Math.pow(move.y, 2));
        return move;
    }

    private boolean ColourEqual(Vector a, Vector b, int accuracy)
    {
        double red = a.x - b.x;
        double green = a.y - b.y;
        double blue = a.z - b.z;

        if((red < accuracy && red > -accuracy) && (green < accuracy && green > -accuracy) && (blue < accuracy && blue > -accuracy))
            return true;

        return false;
    }

    //endregion
}


