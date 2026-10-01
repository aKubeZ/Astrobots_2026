package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.TestMotor;

public class TestCommand extends CommandBase {
    TestMotor motor;
    public TestCommand() {
        motor = TestMotor.getInstance();
        addRequirements(motor);
    }

    @Override
    public void initialize() { }

    @Override
    public void execute() {
        motor.run(12);
    }

    @Override
    public void end(boolean isFinished) {
        motor.brake();
    }

    @Override
    public boolean isFinished() { return false; }
}
