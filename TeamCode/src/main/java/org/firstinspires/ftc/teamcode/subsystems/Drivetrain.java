package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Constants;

/**
 * Drivetrain subsystem.
 */
public class Drivetrain extends SubsystemBase {
    /**
     * A data class that stores 4 doubles corresponding to each motor.
     */
    public static class DriveState {
        public final double fl;
        public final double fr;
        public final double bl;
        public final double br;
        public DriveState(double fl, double fr, double bl, double br) {
            this.fl = fl;
            this.fr = fr;
            this.bl = bl;
            this.br = br;
        }
    }


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

        motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Sets the initial heading of the robot, to make a reference
     * to base the field-centric drive on.
     * @param heading the initial heading
     */
    public void setInitialHeading(double heading) { initialHeading = heading; }

    /**
     * Returns the initial heading of the robot for a reference.
     * @return the initial heading
     */
    public double getInitialHeading() { return initialHeading; }

    /**
     * Drives the robot using robot centric.
     * @param x positive moves it right, negative moves it left
     * @param y positive moves it forwards, negative moves it backwards
     * @param rot positive spins it clockwise, negative spins it counter-clockwise
     */
    public void driveRobotCentric(double x, double y, double rot) {
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rot), 1);
        motorFL.setPower((-y - x - rot) / denominator);
        motorFR.setPower((-y + x + rot) / denominator);
        motorBL.setPower((y - x + rot) / denominator);
        motorBR.setPower((y + x - rot) / denominator);
    }

     /**
     * Drives the robot using field centric (NOT IMPLEMENTED YET PLS DO NOT USE YET)
     * @param x positive moves it right, negative moves it left
     * @param y positive moves it forwards, negative moves it backwards
     * @param rot positive spins it clockwise, negative spins it counter-clockwise
     */
    public void driveFieldCentric(double x, double y, double rot, double heading) {
        /*
            [ cos   -sin ] [ x ] = [ x cos - y sin ]
            [ sin    cos ] [ y ] = [ x sin + y cos ]
         */
        double angle = -(heading - initialHeading); // flipped bc clockwise
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double robotX = x * cos - y * sin;
        double robotY = x * sin + y * cos;
        driveRobotCentric(robotX, robotY, rot);
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

    public void setFLPower(double power) { motorFL.setPower(power); }
    public void setFRPower(double power) { motorFR.setPower(power); }
    public void setBLPower(double power) { motorBL.setPower(power); }
    public void setBRPower(double power) { motorBR.setPower(power); }

    /**
     * Returns the current drive state with each motor's position.
     * @return the drive state.
     */
    public DriveState getDriveState() {
        return new DriveState(
                motorFL.getCurrentPosition(),
                motorFR.getCurrentPosition(),
                motorBL.getCurrentPosition(),
                motorBR.getCurrentPosition()
        );
    }

    /**
     * Returns the current position of the front left motor
     * @return the front left motor's position
     */
    public double getFLPosition() { return motorFL.getCurrentPosition(); }

    /**
     * Returns the current position of the front right motor
     * @return the front right motor's position
     */
    public double getFRPosition() { return motorFR.getCurrentPosition(); }

    /**
     * Returns the current position of the back left motor
     * @return the back left motor's position
     */
    public double getBLPosition() { return motorBL.getCurrentPosition(); }

    /**
     * Returns the current position of the back right motor
     * @return the back right motor's position
     */
    public double getBRPosition() { return motorBR.getCurrentPosition(); }
}