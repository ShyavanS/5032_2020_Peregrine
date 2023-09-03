package ca.team5032.robot.systems.drive;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.Color;

/**
 * Represents a Robot mode. Modes can control the RGB LED lights on the robot as well as the torque and RPM
 * to allow for different speeds to be configured for different use cases.
 * For example, you could have a speed mode which has a high targeted RPM and a low targeted torque and
 * a pushing mode that has a high torque and lower RPM for pushing heavy loads.
 *
 * See https://motors.vex.com/vexpro-motors/falcon for currents.
 */
@Getter @AllArgsConstructor
public enum Mode {
    NORMAL(Color.ORANGE, 2.31, 3210),
    SPEED(Color.BLUE, 1, 5000),
    OPERATION_BATTERING_RAM(Color.RED, 4, 1000);

    /**
     * Color of the RGB LED strips.
     */
    private Color color;

    /**
     * Target torque, averaged with RPM for cap.
     */
    private double targetTorque;

    /**
     * Target RPM, average with torque for cap.
     */
    private double targetRPM;
}
