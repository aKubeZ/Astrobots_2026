package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.TestCommand;
import org.firstinspires.ftc.teamcode.subsystems.TestMotor;

public class BotContainer {
    TestMotor testMotor = TestMotor.getInstance();
    GamepadEx pilot = Constants.pilot;
    GamepadEx copilot = Constants.copilot;
    private Bot.Alliance alliance;

    public BotContainer(Bot.Alliance alliance) {
        this.alliance = alliance;
        setDefaultCommands();
        configBindings();
    }

    private void setDefaultCommands() {

    }

    private void configBindings() {
        pilot.getGamepadButton(GamepadKeys.Button.A).whenHeld(new TestCommand());
    }

    public Command getAutonomousCommand() {
        return new RunCommand(() -> {});
    }
}
