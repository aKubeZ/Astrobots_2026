package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.CRServoImplEx;

import org.firstinspires.ftc.teamcode.Constants;

/**
 * A magazine subsystem; transports pollen/nectar from the intake
 * to the shooter.
 */
public class Indexer extends SubsystemBase {
    private static Indexer shooter;
    public static synchronized Indexer getInstance() {
        if (shooter == null) shooter = new Indexer();
        return shooter;
    }

    private CRServoImpl servo;
    private Indexer() {
        servo = Constants.hardwareMap.get(CRServoImpl.class, "ix");
    }

    public void run() { servo.setPower(1); }
}
