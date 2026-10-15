package org.firstinspires.ftc.teamcode.autonomous;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Gyroscope;

/**
 * An attempt to make localization possible with the robot
 * Tries to use the drive encoders + gyroscope to estimate its position.
 */
public class Localizer {
    private static Localizer instance; // not even a subsystem but
    public static synchronized Localizer getInstance() {
        if (instance == null) instance = new Localizer();
        return instance;
    }

    private final Drivetrain drivetrain = Drivetrain.getInstance();
    private final Gyroscope gyroscope = Gyroscope.getInstance();
    private Drivetrain.DriveState previousDriveState;
    private Pose pose;

    private Localizer() { }

    /**
     * Updates the robot pose.
     */
    public void update(double deltaT) {
        if (deltaT == 0) return;
        // TODO: test this bc all of this only hypothetically works
        Drivetrain.DriveState driveState = drivetrain.getDriveState();
        if (previousDriveState == null) {
            previousDriveState = driveState;
            return;
        }


        double multiplier = Constants.AutoConstants.inchesPerTick / deltaT;

        // these doubles probably will be in inches per second
        double vFL = multiplier * (driveState.fl - previousDriveState.fl);
        double vFR = multiplier * (driveState.fr - previousDriveState.fr);
        double vBL = multiplier * (driveState.bl - previousDriveState.bl);
        double vBR = multiplier * (driveState.br - previousDriveState.br);

        /* this def doesn't work but :sob:
            [ fl ]   [ -1  -1  -1 ]
            [ fr ] = [  1  -1   1 ] [ x ]
            [ bl ]   [  1  -1  -1 ] [ y ]
            [ br ]   [ -1  -1   1 ] [ t ]
         */
        double vXRelative = Constants.AutoConstants.localizeXScale * (-vFL + vFR + vBL - vBR);
        double vYRelative = Constants.AutoConstants.localizeYScale * (-vFL - vFR - vBL - vBR);

        /* Rotate based on heading
            [ cos   -sin ] [ x ] = [ x cos - y sin ]
            [ sin    cos ] [ y ] = [ x sin + y cos ]
         */
        double angle = gyroscope.getHeading() - drivetrain.getInitialHeading();
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double vX = vXRelative * cos - vYRelative * sin;
        double vY = vXRelative * sin + vYRelative * cos;

        pose = new Pose(
                pose.x + vX * deltaT,
                pose.y + vY * deltaT,
                gyroscope.getHeading()
        );

        // update the previous value
        previousDriveState = driveState;
    }

    /**
     * Returns the estimated robot pose.
     * @return the robot pose
     */
    public Pose getPose() { return pose; }
}