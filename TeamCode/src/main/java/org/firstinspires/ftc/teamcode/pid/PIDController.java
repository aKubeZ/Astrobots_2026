package org.firstinspires.ftc.teamcode.pid;

/**
 * A PID controller to control a machine's input based on
 * its output and a desired output.
 */
public class PIDController {
    // constants
    private final double kP;
    private final double kI;
    private final double kD;

    // values
    private double integral = 0;
    private double previousError;
    private boolean initialized = false;

    public PIDController(PIDData pidData) {
        this.kP = pidData.kP;
        this.kI = pidData.kI;
        this.kD = pidData.kD;
    }

    /**
     * Updates the PID and returns the input. Be sure to initialize before using.
     * @param desiredOutput the desired output of the machine
     * @param output the output of the machine
     * @param deltaT the change in time
     * @return the new input to be used
     */
    public double update(double desiredOutput, double output, double deltaT) {
        double error = desiredOutput - output;
        if (!initialized) {
            previousError = error;
            initialized = true;
        }

        double derivative = (error - previousError) / deltaT;
        integral += error * deltaT;

        previousError = output;
        return kP * error + kI * integral + kD * derivative;
    }
}
