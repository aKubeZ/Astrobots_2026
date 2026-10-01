package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Constants;

public class TestMotor extends SubsystemBase {
    private static TestMotor instance = null;
    public static synchronized TestMotor getInstance() {
        if (instance == null) instance = new TestMotor();
        return instance;
    }

    DcMotorEx motor;
    private TestMotor() {
        motor = Constants.hardwareMap.get(DcMotorEx.class, "testmotor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void run(double value) {
        motor.setPower(value);
    }

    public void brake() {
        motor.setPower(0);
    }
}
