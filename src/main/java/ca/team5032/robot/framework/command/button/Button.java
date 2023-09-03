package ca.team5032.robot.framework.command.button;

import ca.team5032.robot.joystick.GameJoystick;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public abstract class Button {

    private GameJoystick joystick;

}
