package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.Bot;

/**
 * Default autonomous command.
 */
public class AutoCommand extends ParallelCommandGroup {
    private Bot.Alliance alliance;

    /**
     * Creates the auto command.
     * @param alliance
     */
    public AutoCommand(Bot.Alliance alliance) {
        this.alliance = alliance;
        addCommands(
                new Drive(false, () -> 1, () -> 1, () -> 1)
        );
    }
}
