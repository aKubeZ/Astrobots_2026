package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.RunCommand;

public class Bot extends Robot {
    //auto command that runs
    public Command autoCommand = null;
    //Bot conatiner
    public BotContainer container = null;
    //default auto or not
    boolean defaultAuto = true;

    //modes
    public enum OpMode {
        TELEOP, AUTO
    }

    public enum Alliance {
        BLUE, RED
    }

    //Tele Op and default auto constructor
    public Bot(OpMode opMode, Alliance alliance) {
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

    //custom auto constructor
    public Bot(OpMode opMode, Command auto) {
        defaultAuto = false;
        autoCommand = auto;
        initAuto();
    }

    //initialize teleOp, cancels current auto command if its running
    public void initTele() {
        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }

    //initialize auto and schedules command
    public void initAuto() {
        //if default auto, grab default auto command from Bot Container
        if (defaultAuto) {
            autoCommand = container.getAutonomousCommand();
        }

        if (autoCommand != null) {
            autoCommand.schedule();
        }
    }
}
