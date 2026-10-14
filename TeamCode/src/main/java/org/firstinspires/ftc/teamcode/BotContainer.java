package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.commands.AutoCommand;
import org.firstinspires.ftc.teamcode.commands.DataLog;
import org.firstinspires.ftc.teamcode.commands.TestDrive;
import org.firstinspires.ftc.teamcode.subsystems.Gyroscope;

import org.firstinspires.ftc.teamcode.commands.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class BotContainer {
    private final GamepadEx pilot = Constants.pilot;
    private final GamepadEx copilot = Constants.copilot;
    private final Drivetrain drivetrain = Drivetrain.getInstance();
    private final Gyroscope gyroscope = Gyroscope.getInstance();
    private DataLog logger;
    private final ElapsedTime stopwatch = new ElapsedTime();
    private final Bot.Alliance alliance;
    private final Bot.ButtonConfig buttonConfig;

    public BotContainer(Bot.Alliance alliance, Bot.ButtonConfig config) {
        this.alliance = alliance;
        this.buttonConfig = config;
        setDefaultCommands();
        configBindings();
    }

    /**
     * Sets the commands that are run by default
     * when no other command is running.
     */
    private void setDefaultCommands() {
        drivetrain.setInitialHeading(gyroscope.getHeading()); // idk where to put this so here :3
        logger = new DataLog(
                new DataLog.Entry("six", () -> "seven"),
                new DataLog.Entry("elapsed time", () -> Double.toString(stopwatch.seconds())),
                new DataLog.Entry("heading", () -> Double.toString(gyroscope.getHeading())),
                new DataLog.Entry("left x", () -> Double.toString(pilot.getLeftX())),
                new DataLog.Entry("left y", () -> Double.toString(pilot.getLeftY())),
                new DataLog.Entry("right x", () -> Double.toString(pilot.getRightX())),
                new DataLog.Entry("fl motor", () -> Double.toString(drivetrain.getFLPosition())),
                new DataLog.Entry("fr motor", () -> Double.toString(drivetrain.getFRPosition())),
                new DataLog.Entry("bl motor", () -> Double.toString(drivetrain.getBLPosition())),
                new DataLog.Entry("br motor", () -> Double.toString(drivetrain.getBRPosition()))
        );
        logger.schedule();

        switch (buttonConfig) {
            case AUTO: break;
            case TELE: {
                drivetrain.setDefaultCommand(new Drive(false,
                        pilot::getLeftX,
                        pilot::getLeftY,
                        pilot::getRightX
                ));
            } break;
            case DRIVETRAIN_TEST: {
                drivetrain.setDefaultCommand(new TestDrive(
                        () -> (pilot.getGamepadButton(GamepadKeys.Button.Y).get() ? 1 : 0)
                                - (pilot.getGamepadButton(GamepadKeys.Button.DPAD_UP).get() ? 1 : 0),
                        () -> (pilot.getGamepadButton(GamepadKeys.Button.B).get() ? 1 : 0)
                                - (pilot.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).get() ? 1 : 0),
                        () -> (pilot.getGamepadButton(GamepadKeys.Button.X).get() ? 1 : 0)
                                - (pilot.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).get() ? 1 : 0),
                        () -> (pilot.getGamepadButton(GamepadKeys.Button.A).get() ? 1 : 0)
                                - (pilot.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).get() ? 1 : 0)
                ));
                // If you hold your controller rotated 45 degrees counterclockwise it's easy
                // X Y A B will correspond to positive rotation of the FL FR BL BR motors
                // UP RIGHT LEFT DOWN will correspond to the negative rotation of the FL FR BL BR motors
            } break;
        }
    }

    /**
     * Configures the commands that run when a button or trigger is pressed.
     */
    private void configBindings() {
        switch (buttonConfig) {
            case AUTO: break;
            case TELE: break;
            case DRIVETRAIN_TEST: break;
        }
    }

    /**
     * Returns the auto command.
     * @return the autonomous command to be used
     */
    public Command getAutonomousCommand() {
        return new AutoCommand(alliance);
    }
}
