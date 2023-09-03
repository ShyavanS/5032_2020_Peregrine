package ca.team5032.robot.systems;

import ca.team5032.robot.OI;
import ca.team5032.robot.Robot;
import ca.team5032.robot.framework.subsystem.Subsystem;
import ca.team5032.robot.tasks.Task;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends Subsystem {

    // TODO: better names based on function
    private WPI_TalonSRX speedController;
    private WPI_VictorSPX hoodController;

    private Counter encoder;
    // private int rpm;
    private int target = 100;
    private double speed = 1.0;
    private AnalogInput angle;

    public ShooterSubsystem(Robot robot, boolean defaultEnabled) {
        super(robot, "ShooterSubsystem", defaultEnabled);
        this.speedController = new WPI_TalonSRX(6);
        this.hoodController = new WPI_VictorSPX(OI.SYSTEM_SHOOTER_HOOD_VICTOR_ID);
        this.encoder = new Counter(0);
        this.angle = new AnalogInput(0);
        this.encoder.setDistancePerPulse(1);
    }

    public AnalogInput getAngle() {
        return this.angle;
    }

    @Override
    public void tick() {

    }

    @Override
    public void kill() {
        this.speedController.stopMotor();
        this.hoodController.stopMotor();
    }

    @Override
    public void setDashboard() {
        SmartDashboard.putNumber("Encoder", this.encoder.get());
        SmartDashboard.putNumber("Encoder Distance", this.encoder.getDistance());
        SmartDashboard.putNumber("Encoder Rate", this.encoder.getRate());
        // SmartDashboard.putNumber("POT", angle.getVoltage());
        // SmartDashboard.putBoolean("Top Speed", speed);
        SmartDashboard.putNumber("Distance to target (in)", getRobot().getLimeLight().getDistance());
    }

    public void resetEncoder() {
        this.encoder.reset();
    }

    public void shoot() {
        // if (encoder.getRate() <= target * 0.6) {
        //     speedController.set(speed);
        //     // speed = false;
        // } else if (encoder.getRate() <= target * 0.7 && encoder.getRate() > target * 0.6) {
        //     speedController.set(speed * 0.9);
        //     // speed = false;
        // } else if (encoder.getRate() <= target * 0.8 && encoder.getRate() > target * 0.7) {
        //     speedController.set(speed * 0.8);
        //     // speed = false;
        // } else if (encoder.getRate() <= target * 0.9 && encoder.getRate() > target * 0.8) {
        //     speedController.set(speed * 0.7);
        //     // speed = false;
        // } else {
        //     speedController.set(speed * 0.6);
        //     // speed = true;
        //     getRobot().getIndexingSubsystem().channelBottom.set(-0.7);
        // }
        speedController.set(-1);
    }

    public void stop() {
        this.hoodController.set(0.0);
        this.speedController.set(0.0);
        getRobot().getIndexingSubsystem().stop();
    }

    public void rotate(double amount) {
        this.hoodController.set(amount);
    }

    // private void pulse(double strength, int ms) {
    //     new Task() {
    //         @Override
    //         public void start() {
    //             speedController.set(strength);
    //         }

    //         @Override
    //         public void finish() {
    //             speedController.set(0.0);
    //         }
    //     }.runFor(ms).delay(0).rate(0).then(() -> {}).run();
    // }
}
