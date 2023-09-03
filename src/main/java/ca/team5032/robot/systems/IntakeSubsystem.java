package ca.team5032.robot.systems;

import ca.team5032.robot.OI;
import ca.team5032.robot.Robot;
import ca.team5032.robot.framework.subsystem.Subsystem;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class IntakeSubsystem extends Subsystem {

    // TODO: better names based on function
    private WPI_VictorSPX intakeVictor;
    private WPI_VictorSPX pivot;

    public IntakeSubsystem(Robot robot, boolean defaultEnabled) {
        super(robot, "IntakeSubsystem", defaultEnabled);
        this.intakeVictor = new WPI_VictorSPX(OI.SYSTEM_INTAKE_VICTOR_INTAKE_ID);
        this.pivot = new WPI_VictorSPX(OI.SYSTEM_INTAKE_VICTOR_ROTATE_ID);
    }

    @Override
    public void tick() {
        // Accept ball
    }

    public void accept(double power) {
        // Rotates the intake rod to suck in a ball.
        intakeVictor.set(-power);
    }

    public void pivot(double power) {
        pivot.set(power);
    }

    public void stop() {
        // Stops the motors.
        intakeVictor.set(0.0);
        pivot.set(0.0);
    }

    // TODO: other functions of intake as methods
}
