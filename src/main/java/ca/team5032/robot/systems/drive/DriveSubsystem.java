package ca.team5032.robot.systems.drive;

import ca.team5032.robot.OI;
import ca.team5032.robot.Robot;
import ca.team5032.robot.framework.command.button.JoystickButton;
import ca.team5032.robot.framework.subsystem.Subsystem;
import ca.team5032.robot.joystick.GameJoystick;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class DriveSubsystem extends Subsystem {

    public static final double ANALOG_THRESHOLD = 0.25;

    private Mode currentMode;

    // One Falcon from either side for encoder sampling.
    private WPI_TalonFX left;
    private WPI_TalonFX right;

    private SpeedControllerGroup leftTalons;
    private SpeedControllerGroup rightTalons;

    private DifferentialDrive differentialDrive;

    public DriveSubsystem(Robot robot, boolean defaultEnabled) {
        super(robot, "DriveSubsystem", defaultEnabled);
        this.currentMode = Mode.NORMAL;
        this.resolveControllers();
        this.rightTalons.setInverted(true);
        this.differentialDrive = new DifferentialDrive(
                leftTalons, rightTalons
        );

        this.differentialDrive.setSafetyEnabled(false);


        // Reset encoders from sample falcons.
        this.reset();
    }

    @Override
    public void kill() {
        // Stop the motors!
        this.rightTalons.stopMotor();
        this.leftTalons.stopMotor();
    }

    @Override
    public void tick() {
        // Get the drive joystick.
        Joystick joystick = GameJoystick.DRIVE.getJoystick();

        // TODO: dynamic value.
        double sensitivity = 0.85;

        if (Math.abs(joystick.getX()) >= ANALOG_THRESHOLD || Math.abs(joystick.getY()) >= ANALOG_THRESHOLD) {
            double xJoystick = joystick.getX();
            double yJoystick = joystick.getY();

//            target.x = joystick.getX() * sensitivity;
//            target.y = joystick.getY() * sensitivity;
            this.differentialDrive.arcadeDrive(-xJoystick * sensitivity, yJoystick * sensitivity);

            //System.out.println("Over threshold for x=" + joystick.getX() + ", y=" + joystick.getY());
        } else if (Math.abs((joystick.getRawAxis(4) + 1) / 2) >= ANALOG_THRESHOLD) {
            double forwardPower = joystick.getRawAxis(4);
            this.differentialDrive.arcadeDrive(0, forwardPower * sensitivity);
            //System.out.println("Over threshold for forward=" + forwardPower);
        } else if (Math.abs((joystick.getRawAxis(3) + 1) / 2) >= ANALOG_THRESHOLD) {
            double backwardsPower = joystick.getRawAxis(3);
            this.differentialDrive.arcadeDrive(0, -backwardsPower * sensitivity);
            //System.out.println("Over threshold for backward=" + backwardsPower);
        } else if (joystick.getRawButton(JoystickButton.R1)) {
            this.differentialDrive.arcadeDrive(0, 0.35);
        } else if (joystick.getRawButton(JoystickButton.L1)) {
            this.differentialDrive.arcadeDrive(0, -0.35);
        } else {
            this.leftTalons.set(0);
            this.rightTalons.set(0);
        }

    }

    public double getLeftDistance() {
        return getLeftRotation() * OI.WHEEL_CIRCUMFERENCE;
    }

    public double getRightDistance() {
        return getRightRotation() * OI.WHEEL_CIRCUMFERENCE;
    }

    public double getLeftRotation() {
        return (float) getLeftEncoderPosition() / 8 / OI.UNITS_PER_ROTATION;
    }

    public double getRightRotation() {
        return (float) getRightEncoderPosition() / 8 / OI.UNITS_PER_ROTATION;
    }

    public int getLeftEncoderPosition() {
        return left.getSelectedSensorPosition();
    }

    public int getRightEncoderPosition() {
        return right.getSelectedSensorPosition();
    }

    public void resetLeft() {
        left.setSelectedSensorPosition(0);
    }

    public void resetRight() {
        right.setSelectedSensorPosition(0);
    }

    public void stop() {
        differentialDrive.stopMotor();
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        differentialDrive.tankDrive(leftSpeed, rightSpeed);
    }

    public void drive(double xSpeed, double zRotation) {
        differentialDrive.arcadeDrive(xSpeed, zRotation);
    }

    public void reset() {
        // Set both sample falcons' sensor positions to 0.
        left.setSelectedSensorPosition(0);
        right.setSelectedSensorPosition(0);
    }

    private void resolveControllers() {
        // Initialize all the falcons.
        WPI_TalonFX falconLeftLeft = new WPI_TalonFX(0);
        WPI_TalonFX falconLeftRight = new WPI_TalonFX(2);
        WPI_TalonFX falconRightLeft = new WPI_TalonFX(3);
        WPI_TalonFX falconRightRight = new WPI_TalonFX(1);

        // Loop over all the motors and configure the ramp rate.
        Arrays.stream(new WPI_TalonFX[] { falconLeftLeft, falconLeftRight, falconRightLeft, falconRightRight })
                .forEach(falcon -> {
                    falcon.configOpenloopRamp(0.5);
                    falcon.configClosedloopRamp(0.5);
                    falcon.setSafetyEnabled(false);
                });

        // Assign our sample falcons.
        this.left = falconLeftLeft;
        this.right = falconRightRight;

        // Create the speed controller groups for each side of the robot.
        this.leftTalons = new SpeedControllerGroup(falconLeftLeft, falconLeftRight);
        this.rightTalons = new SpeedControllerGroup(falconRightLeft, falconRightRight);
    }

}
