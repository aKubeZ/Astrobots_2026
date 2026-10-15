package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Shooter;

public class Shoot extends CommandBase {
    private final Shooter shooter = Shooter.getInstance();
    private final double power;

    public Shoot(double power) {
        this.power = power;
        addRequirements(shooter);
    }

    @Override
    public void execute() { shooter.run(power); }
}
