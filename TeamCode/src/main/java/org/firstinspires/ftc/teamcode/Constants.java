package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pid.PIDData;

@Config
public class Constants {
    public static HardwareMap hardwareMap = null;
    public static Telemetry telemetry = null;
    public static GamepadEx pilot = null;
    public static GamepadEx copilot = null;

    public static class AutoConstants {
        public final static double inchesPerTick = ((double) 48 / 2000);
        public final static double localizeScale = 0.24;
        public static boolean neutralDrivetrain = false;
        public final static double driveMax = 0.7;
        public final static PIDData xyPID = new PIDData(-0.1, 0, 0);
        public final static PIDData headingPID = new PIDData(0.4, 0.01, 0);
    }
}