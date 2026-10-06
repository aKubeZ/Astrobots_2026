package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.GyroEx;

import org.firstinspires.ftc.teamcode.Constants;

/**
 * Gyroscope subsystem.
 */
public class Gyroscope extends SubsystemBase {
    private static Gyroscope instance = null;

    /**
     * Returns the gyroscope instance.
     * Use this instead of creating a new instance.
     * @return the gyroscope instance.
     */
    public static synchronized Gyroscope getInstance() {
        if (instance == null) instance = new Gyroscope();
        return instance;
    }

    GyroEx gyro;
    private Gyroscope() {
        gyro = Constants.hardwareMap.get(GyroEx.class, "gyro");
    }

    /**
     * Returns the heading of the gyroscope.
     * @return gyroscope heading
     */
    public double getHeading() {
        return gyro.getHeading();
    }
}