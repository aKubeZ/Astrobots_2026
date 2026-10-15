package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.Bot;
import org.firstinspires.ftc.teamcode.autonomous.Pose;

/**
 * Default autonomous command.
 */
public class AutoCommand extends ParallelCommandGroup {
    private final Bot.Alliance alliance;

    /**
     * Creates the auto command.
     * @param alliance The alliance of the robot
     */
    public AutoCommand(Bot.Alliance alliance) {
        this.alliance = alliance;
//        addCommands(
//                new Drive(false, () -> 1, () -> 1, () -> 1)
//        );
        addCommands(
                new AutoDrive((double time) -> new Pose(
                        time / 10, // hehe funny spinny sine wave
                        10 * Math.sin(time),
                        time / 10
                ))
        );
    }
}
