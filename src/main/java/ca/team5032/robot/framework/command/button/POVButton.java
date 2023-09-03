package ca.team5032.robot.framework.command.button;

import ca.team5032.robot.joystick.GameJoystick;
import lombok.Getter;

@Getter
public class POVButton extends Button {

    private int angle;

    public POVButton(GameJoystick joystick, int angle) {
        super(joystick);
        this.angle = angle;
    }

}
