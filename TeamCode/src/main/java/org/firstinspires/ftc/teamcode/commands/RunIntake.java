package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

public class RunIntake extends CommandBase {
    private final Intake intake = Intake.getInstance();
    private final double power;

    public RunIntake(double power) {
        this.power = power;
        addRequirements(intake);
    }

    @Override
    public void execute() { intake.run(power); }
}
