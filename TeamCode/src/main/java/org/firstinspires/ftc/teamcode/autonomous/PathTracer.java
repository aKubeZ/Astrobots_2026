package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.BotContainer;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.pid.PIDController;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Gyroscope;

import java.util.function.Supplier;

/**
 * Command that attempts to make the robot trace a path using PID.
 */
public class PathTracer {
    private final Drivetrain drivetrain = Drivetrain.getInstance();
    private final Gyroscope gyroscope = Gyroscope.getInstance();
    private final Path path;
    private final Supplier<Pose> poseSupplier;

    private final PIDController xPID = new PIDController(Constants.AutoConstants.xyPID, false);
    private final PIDController yPID = new PIDController(Constants.AutoConstants.xyPID, false);
    private final PIDController headingPID = new PIDController(Constants.AutoConstants.headingPID, true);

    private double timeOffset;
    private double previousTimestamp;

    /**
     * Create a path tracer.
     * @param poseSupplier a supplier that constantly gives the robot pose.
     * @param path the path the tracer will follow.
     */
    public PathTracer(Supplier<Pose> poseSupplier, Path path) {
        this.poseSupplier = poseSupplier;
        this.path = path;
    }

    /**
     * Initialize the path tracer
     */
    public void initialize() {
        timeOffset = BotContainer.timestamp();
    }

    /**
     * Traces the path!
     */
    public void trace() {
        double timestamp = BotContainer.timestamp() - timeOffset;
        Pose desiredPose = path.at(timestamp);
        double deltaT = timestamp - previousTimestamp;
        double driveX = xPID.update(desiredPose.x, poseSupplier.get().x, deltaT);
        double driveY = yPID.update(desiredPose.y, poseSupplier.get().y, deltaT);
        double driveRot = headingPID.update(desiredPose.heading, poseSupplier.get().heading, deltaT);
        drivetrain.driveFieldCentricBounded(driveX, driveY, driveRot, gyroscope.getHeading());
        // update previous timestamp
        previousTimestamp = timestamp;
    }
}
