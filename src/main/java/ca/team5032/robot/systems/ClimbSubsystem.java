package ca.team5032.robot.systems;

import ca.team5032.robot.OI;
import ca.team5032.robot.Robot;
import ca.team5032.robot.framework.subsystem.Subsystem;
import ca.team5032.robot.joystick.GameJoystick;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class ClimbSubsystem extends Subsystem {

    // TODO: named based on function
    private WPI_TalonSRX climber1;
    private WPI_TalonSRX climber2;
    private WPI_VictorSPX hooker;

    private DoubleSolenoid brake;

    public ClimbSubsystem(Robot robot, boolean defaultEnabled) {
        super(robot, "ClimbSubsystem", defaultEnabled);

        this.climber1 = new WPI_TalonSRX(OI.SYSTEM_CLIMB_TALON_LEFT_ID);
        this.climber2 = new WPI_TalonSRX(OI.SYSTEM_CLIMB_TALON_RIGHT_ID);
        this.hooker = new WPI_VictorSPX(OI.SYSTEM_CLIMB_HOOKER_ID);
        this.brake = new DoubleSolenoid(0, 1);
        this.brake.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void kill() {
        this.climber1.stopMotor();
        this.climber2.stopMotor();
        this.hooker.stopMotor();
    }

    @Override
    public void tick() {
        // Apply idle power to prevent from falling.
        double climbPower = -GameJoystick.COSMETIC.getJoystick().getRawAxis(5);
        if (Math.abs(climbPower) < 0.1) return;
        if (climbPower > 0) {
            up(Math.abs(climbPower));
        } else if (climbPower < 0) {
            down(Math.abs(climbPower));
        } else {
            stop();
        }

    }

    public void windUpHooker() {
        hooker.set(0.5);
    }

    public void windDownHooker() {
        hooker.set(-0.5);
    }

    public void windUpClimber(int modifier) {
        climber1.set(modifier * 0.25);
        climber2.set(modifier * -0.25);
    }

    public void engageSolenoid() {
        this.brake.set(DoubleSolenoid.Value.kReverse);
    }

    public void disengageSolenoid() {
        this.brake.set(DoubleSolenoid.Value.kForward);
    }

    public void up(double power) {
        // Sequence multiplied by amount.
        climber1.set(-power / 2);
        climber2.set(power / 2);
        hooker.set(power / 10);
    }

    public void down(double power) {
        // Sequence multiplied by amount.
        climber1.set(power / 2);
        climber2.set(-power / 2);
        hooker.set(-power / 10);
    }

    public void stop() {
        climber1.set(0.0);
        climber2.set(0.0);
        hooker.set(0.0);
    }
}
