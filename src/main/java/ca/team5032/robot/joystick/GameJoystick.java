package ca.team5032.robot.joystick;

import ca.team5032.robot.OI;
import edu.wpi.first.wpilibj.Joystick;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Way of representing the different joysticks.
 */
@Getter
@AllArgsConstructor
public enum GameJoystick {
    DRIVE(new Joystick(OI.DRIVE_JOYSTICK_ID)),
    COSMETIC(new Joystick(OI.COSMETIC_JOYSTICK_ID));

    private final Joystick joystick;
}

