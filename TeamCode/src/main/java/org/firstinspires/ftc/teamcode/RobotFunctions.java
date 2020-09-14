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

    public void DriveForwardTime(DriveBaseData driveBaseData, double power, long time)
    {
        driveBaseData.leftFront.setPower(power);
        driveBaseData.rightFront.setPower(power);
        driveBaseData.leftBack.setPower(power);
        driveBaseData.rightBack.setPower(power);

        sleep(time);

        driveBaseData.leftFront.setPower(0);
        driveBaseData.rightFront.setPower(0);
        driveBaseData.leftBack.setPower(0);
        driveBaseData.rightBack.setPower(0);
    }

    public void DriveForwardDistance(DriveBaseData driveBaseData, double power, double distance, double timeoutRedundancy)
    {
        driveBaseData.leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveBaseData.leftFront.setPower(power);
        driveBaseData.rightFront.setPower(power);
        driveBaseData.leftBack.setPower(power);
        driveBaseData.rightBack.setPower(power);


        driveBaseData.leftFront.setPower(0);
        driveBaseData.rightFront.setPower(0);
        driveBaseData.leftBack.setPower(0);
        driveBaseData.rightBack.setPower(0);
    }

}
