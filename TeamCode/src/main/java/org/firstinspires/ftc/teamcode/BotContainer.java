package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.Localizer;
import org.firstinspires.ftc.teamcode.commands.AutoCommand;
import org.firstinspires.ftc.teamcode.commands.DataLog;
import org.firstinspires.ftc.teamcode.commands.RunIntake;
import org.firstinspires.ftc.teamcode.commands.TestDrive;
import org.firstinspires.ftc.teamcode.commands.Shoot;
import org.firstinspires.ftc.teamcode.commands.TestLocalizer;
import org.firstinspires.ftc.teamcode.subsystems.Gyroscope;

import org.firstinspires.ftc.teamcode.commands.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Indexer;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

public class BotContainer {
    private static final ElapsedTime stopwatch = new ElapsedTime();

    /**
     * Returns the current timestamp.
     * @return the current timestamp.
     */
    public static double timestamp() { return stopwatch.seconds(); }

    // controllers
    private final GamepadEx pilot = Constants.pilot;
    private final GamepadEx copilot = Constants.copilot;

    // subsystems
    private final Drivetrain drivetrain = Drivetrain.getInstance();
    private final Gyroscope gyroscope = Gyroscope.getInstance();
    private final Shooter shooter = Shooter.getInstance();
    private final Intake intake = Intake.getInstance();
    private final Indexer indexer = Indexer.getInstance();

    // others
    private final DataLog logger = new DataLog();
    private final Bot.Alliance alliance;
    private final Bot.Config config;

    public BotContainer(Bot.Alliance alliance, Bot.Config config) {
        this.alliance = alliance;
        this.config = config;
        logger.schedule();
        setDefaultCommands();
        configBindings();
    }

    /**
     * Sets the commands that are run by default
     * when no other command is running.
     */
    private void setDefaultCommands() {
        drivetrain.setEastHeading(gyroscope.getHeading()); // idk where to put this so here :3
        logger.addEntry("six", () -> "seven");
        logger.addEntry("elapsed time", () -> Double.toString(timestamp()));
        logger.addEntry("heading", () -> Double.toString(gyroscope.getHeading()));
        switch (config) {
            case AUTO: break;
            case TELE: {
                drivetrain.setDefaultCommand(new Drive(true,
                        pilot::getLeftX,
                        pilot::getLeftY,
                        pilot::getRightX
                ));
                intake.setDefaultCommand(new RunIntake(
                        () -> copilot.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)
                ));
                shooter.setDefaultCommand(new Shoot(
                        () -> copilot.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)
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
            case LOCALIZATION_TEST: {
                new TestLocalizer().schedule(); // run the localizer
                Localizer localizer = Localizer.getInstance(); // creates a localizer instance
                logger.addEntry("px", () -> Double.toString(localizer.getPose().x));
                logger.addEntry("py", () -> Double.toString(localizer.getPose().y));
                // logs the perceived x's and y's
            }
        }
    }

    /**
     * Configures the commands that run when a button or trigger is pressed.
     */
    private void configBindings() {
        switch (config) {
            case AUTO: break;
            case TELE: {
                pilot.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenActive(
                        () -> drivetrain.setEastHeading(gyroscope.getHeading())
                );
                copilot.getGamepadButton(GamepadKeys.Button.A).whileHeld(() -> {
                    indexer.run();
//                    logger.addEntry(new DataLog.Entry("poo poo", () -> "ee"));
                });
            } break;
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
