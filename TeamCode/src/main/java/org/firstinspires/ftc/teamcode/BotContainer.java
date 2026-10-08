package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.commands.AutoCommand;
import org.firstinspires.ftc.teamcode.commands.DataLog;
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

    public BotContainer(Bot.Alliance alliance) {
        this.alliance = alliance;
        setDefaultCommands();
        configBindings();
    }

    /**
     * Sets the commands that are run by default
     * when no other command is running.
     */
    private void setDefaultCommands() {
        drivetrain.setInitialHeading(gyroscope.getHeading()); // idk where to put this so here :3
        drivetrain.setDefaultCommand(new Drive(false,
                pilot::getLeftX,
                pilot::getLeftY, // idk
                pilot::getRightX
        ));

        logger = new DataLog(
                new DataLog.Entry("six", () -> "seven"),
                new DataLog.Entry("heading", () -> Double.toString(gyroscope.getHeading())),
                new DataLog.Entry("left x", () -> Double.toString(pilot.getLeftX())),
                new DataLog.Entry("left y", () -> Double.toString(pilot.getLeftY())),
                new DataLog.Entry("right x", () -> Double.toString(pilot.getRightX()))
        );
        logger.schedule();
    }

    /**
     * Configures the commands that run when a button or trigger is pressed.
     */
    private void configBindings() {

    }

    /**
     * Returns the auto command.
     * @return the autonomous command to be used
     */
    public Command getAutonomousCommand() {
        return new AutoCommand(alliance);
    }
}
