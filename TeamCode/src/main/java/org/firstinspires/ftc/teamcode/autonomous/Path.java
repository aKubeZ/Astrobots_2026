package org.firstinspires.ftc.teamcode.autonomous;

/**
 * Immutable 3D parametric equation of x, y, and heading.
 */
@FunctionalInterface
public interface Path {
    Pose at(double time);
}