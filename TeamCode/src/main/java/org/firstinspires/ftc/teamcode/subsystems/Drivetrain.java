package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants;

/**
 * Drivetrain subsystem.
 */
public class Drivetrain extends SubsystemBase {
    private static Drivetrain instance = null;

    /**
     * Returns the instance of the drivetrain subsystem,
     * use this instead of constructing an instance.
     * @return the drivetrain instance.
     */
    public static synchronized Drivetrain getInstance() {
        if (instance == null) instance = new Drivetrain();
        return instance;
    }

    DcMotorEx motorFL;
    DcMotorEx motorFR;
    DcMotorEx motorBL;
    DcMotorEx motorBR;
    double initialHeading = 0;
    private Drivetrain() {
        motorFL = Constants.hardwareMap.get(DcMotorEx.class, "fl");
        motorFR = Constants.hardwareMap.get(DcMotorEx.class, "fr");
        motorBL = Constants.hardwareMap.get(DcMotorEx.class, "bl");
        motorBR = Constants.hardwareMap.get(DcMotorEx.class, "br");

//        motorFR.setDirection(DcMotorSimple.Direction.REVERSE);
//        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setInitialHeading(double heading) { this.initialHeading = heading; }

    /**
     * Drives the robot using robot centric
     * @param x positive moves it right, negative moves it left.
     * @param y positive moves it forwards, negative moves it backwards.
     * @param rot positive spins it clockwise, negative spins it counter-clockwise
     */
    public void driveRC(double x, double y, double rot) {
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rot), 1);
        motorFL.setPower((-y - x - rot) / denominator);
        motorFR.setPower((-y + x + rot) / denominator);
        motorBL.setPower((-y + x - rot) / denominator);
        motorBR.setPower((-y - x + rot) / denominator);
    }

     /**
     * Drives the robot using field centric (NOT IMPLEMENTED YET PLS DO NOT USE YET)
     * @param x positive moves it right, negative moves it left.
     * @param y positive moves it forwards, negative moves it backwards.
     * @param rot positive spins it clockwise, negative spins it counter-clockwise
     */
    public void driveFC(double x, double y, double rot, double heading) {
        /*
            [ cos   -sin ] [ x ] = [ x cos - y sin ]
            [ sin    cos ] [ y ] = [ x sin + y cos ]
         */
        double cos = Math.cos(heading - initialHeading);
        double sin = Math.sin(heading - initialHeading);
        double robotX = x * cos - y * sin;
        double robotY = x * sin + y * cos;
        driveRC(robotX, robotY, rot);
    }

    /**
     * Halts every motor.
     */
    public void brake() {
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }
}