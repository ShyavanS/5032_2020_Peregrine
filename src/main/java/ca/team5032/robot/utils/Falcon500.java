package ca.team5032.robot.utils;

import ca.team5032.robot.systems.drive.DriveSubsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Falcon500 extends WPI_TalonFX {

    // Increment of torque value in slope.
    public static final double TORQUE_INCREMENT = 0.0007348365506;
    // Max torque for motor.
    public static final double MAX_TORQUE = 4.688000367418;
    // Increment of RPM in slope.
    public static final double RPM_INCREMENT = 1360.8468430034;
    // Max RPM for motor.
    public static final double MAX_RPM = 6379.6505;

    // Increment of current in slope.
    public static final double CURRENT_INCREMENT = 0.040065207850022;
    // Max current for motor.
    public static final double MAX_CURRENT = 257.15304006521;

    // Target torque to reach.
    private double targetTorque;
    // Target RPM to reach.
    private double targetRPM;

    private DriveSubsystem driveSystem;

    /**
     * Constructor for motor controller
     *
     * @param deviceNumber device ID of motor controller
     */
    public Falcon500(int deviceNumber, DriveSubsystem driveSystem) {
        super(deviceNumber);
        this.driveSystem = driveSystem;
    }

    /**
     * Target torque as RPM and target RPM average together to produce an averaged target RPM value,
     * this is the max RPM of the motor and the normalized value is mapped onto the slope produced.
     *
     * This value is then used to set the motors current.
     *
     * @param normalizedInput Input ranging from -1 to 1.
     */
    @Override
    public void set(double normalizedInput) {
        // Calculate a proper RPM value from our normalized input (-1 ... +1).
        double mappedRPM = getRPM(normalizedInput);
        // Get the current by mapping the RPM to a current.
        double targetCurrent = getCurrentFromRPM(mappedRPM);

        // Set the motors current to the previously calculated target.
        super.set(ControlMode.PercentOutput, targetCurrent / MAX_CURRENT);
    }

    /**
     * Estimates the torque for a specific RPM with rigorous math!
     *
     * @param rpm The RPM
     * @return Estimated Torque
     */
    private double getTorqueFromRPM(double rpm) {
        // Use slope intercept form to map the RPM to a torque.
        return (-TORQUE_INCREMENT * rpm) + MAX_TORQUE;
    }

    /**
     * Estimates the RPM for a specific torque.
     *
     * @param torque The torque
     * @return Estimated RPM
     */
    private double getRPMFromTorque(double torque) {
        // Use slope intercept form to map the torque to a RPM.
        return (-RPM_INCREMENT * torque) + MAX_RPM;
    }

    /**
     * Estimates the current for RPM
     *
     * @param rpm The RPM
     * @return The current
     */
    private double getCurrentFromRPM(double rpm) {
        // Use slope intercept form to map the RPM to current based on the slope.
        return (-CURRENT_INCREMENT * rpm) + MAX_CURRENT;
    }

    /**
     * Estimates the current for Torque
     *
     * @param torque The Torque
     * @return The RPM
     */
    private double getCurrentFromTorque(double torque) {
        // Use slope intercept form to map the torque to current based on the slope.
        // Additionally, we use getRPMFromTorque to map the torque to a RPM value.
        return (-CURRENT_INCREMENT * getRPMFromTorque(torque)) + MAX_CURRENT;
    }

    /**
     * Maps the normalized RPM to the range of -target to +target.
     *
     * @param inputRPM Input ranging from -1 to 1.
     * @return Mapped RPM based on targets.
     */
    private double getRPM(double inputRPM) {
        // Average target RPM between the torque and RPM.
        double averageTarget = (driveSystem.getCurrentMode().getTargetRPM() /
                getRPMFromTorque(driveSystem.getCurrentMode().getTargetTorque())) / 2;

        // Return the mapped normalized input by multiplying by the target.
        return averageTarget * inputRPM;
    }
}
