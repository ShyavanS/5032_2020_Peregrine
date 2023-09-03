package ca.team5032.robot.framework.command.button;

import ca.team5032.robot.joystick.GameJoystick;
import lombok.Getter;

@Getter
public class ControllerButton extends Button {

    private int buttonID;

    public ControllerButton(GameJoystick joystick, int buttonID) {
        super(joystick);
        this.buttonID = buttonID;
    }

}
