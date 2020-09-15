package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
    This file contains the underlying functions to be used during the 2020/2021 season
**/


@Disabled
public abstract class RobotFunctions extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();

    public void TurnMotorTime(DcMotor motor, double power, long time)
    {
        motor.setPower(power);
        sleep(time);
        motor.setPower(0);
    }

    public void SetServoPosition(Servo servo, double position)
    {
        servo.setPosition(position);
    }

    public void SetServoPosition(ServoData servoData)
    {
        servoData.servo.setPosition(servoData.startPosition);
    }

    public void DriveFrontBackTime(DriveBaseData driveBaseData, double power, long time)
    {
        driveBaseData.SetPower(power);

        sleep(time);

        driveBaseData.SetPower(0);
    }

    public void DriveFrontBackDistance(DriveBaseData driveBaseData, double power, double distance, double timeoutRedundancy)
    {
        int newPositionLeftFront = (int)(driveBaseData.leftFront.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter));
        int newPositionRightFront = (int)(driveBaseData.rightFront.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter));
        int newPositionLeftBack = (int)(driveBaseData.leftBack.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter));
        int newPositionRightBack = (int)(driveBaseData.rightBack.getCurrentPosition() + Math.round(distance * driveBaseData.ticksPerCentimeter));

        driveBaseData.SetTargetPositions(newPositionLeftFront, newPositionRightFront, newPositionLeftBack, newPositionRightBack);

        driveBaseData.SetMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveBaseData.SetPower(Math.abs(power));

        runtime.reset();

        while (opModeIsActive() && runtime.time() < timeoutRedundancy && (driveBaseData.leftFront.isBusy() && driveBaseData.rightFront.isBusy() && driveBaseData.leftBack.isBusy() && driveBaseData.rightBack.isBusy()))
        {
            telemetry.addLine("Left Front: Running");
            telemetry.addLine("Right Front: Running");
            telemetry.addLine("Left Back: Running");
            telemetry.addLine("Right Back: Running");

            telemetry.update();
        }

        telemetry.addLine("Left Front: Running");
        telemetry.addLine("Right Front: Running");
        telemetry.addLine("Left Back: Running");
        telemetry.addLine("Right Back: Running");

        telemetry.update();

        driveBaseData.SetPower(0);

        driveBaseData.SetMode(DcMotor.RunMode.RUN_USING_ENCODER);


        sleep(100);

    }

}
