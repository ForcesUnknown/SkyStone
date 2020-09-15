package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


public class DriveBaseData {

    public DcMotor leftFront = null;
    public DcMotor rightFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightBack = null;

    public double wheelCircumference;
    public double ticksPerCentimeter;
    public int encoderTicksPerRotation; // I believe its is 28 for rev and 1440 for Tetrix but i could be wrong

    public DriveBaseData(DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack, double wheelDiameter, Boolean tetrix)
    {
        this.encoderTicksPerRotation = tetrix? 1440 : 28;
        this.wheelCircumference = wheelDiameter * Math.PI;
        ticksPerCentimeter = encoderTicksPerRotation / wheelCircumference;

        this.leftFront = leftFront;
        this.rightFront = rightFront;
        this.leftBack = leftBack;
        this.rightBack = rightBack;

        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void SetPower(double power)
    {
        leftFront.setPower(power);
        rightFront.setPower(power);
        leftBack.setPower(power);
        rightBack.setPower(power);
    }

    public void SetMode(DcMotor.RunMode runMode)
    {
        leftFront.setMode(runMode);
        rightFront.setMode(runMode);
        leftBack.setMode(runMode);
        rightBack.setMode(runMode);
    }

    public void SetTargetPositions(int leftFront, int rightFront, int leftBack, int rightBack)
    {
        this.leftFront.setTargetPosition(leftFront);
        this.rightFront.setTargetPosition(rightFront);
        this.leftBack.setTargetPosition(leftBack);
        this.rightBack.setTargetPosition(rightBack);
    }
}
