package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.command.Command;

public class Bot extends Robot {
    public Command autoCommand = null;
    public BotContainer container;
    public final OpMode opMode;

    public enum OpMode {
        TELEOP, AUTO
    }

    public enum Alliance {
        BLUE, RED
    }

    public Bot(OpMode opMode, Alliance alliance) {
        this.opMode = opMode;
        container = new BotContainer(alliance);

        switch (opMode) {
            case TELEOP: {
                initTele();
            } break;
            case AUTO: {
                initAuto();
            } break;
        }
    }

    public void initTele() {
        if (autoCommand != null) autoCommand.cancel();
    }

    public void initAuto() {
        if (autoCommand == null) autoCommand = container.getAutonomousCommand();
        autoCommand.schedule();
    }
}
