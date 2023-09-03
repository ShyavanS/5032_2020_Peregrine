package ca.team5032.robot.framework.command;

import ca.team5032.robot.framework.command.button.Button;
import ca.team5032.robot.framework.command.button.ButtonStatus;
import ca.team5032.robot.framework.command.button.ButtonType;
import ca.team5032.robot.framework.command.button.ControllerButton;
import lombok.Value;

@Value
public class CommandDefinition {

    private Button trigger;
    private ButtonStatus mode;
    private ButtonType type;
    private ControllerButton[] prerequisite;

    public boolean isPOVTrigger() {
        return this.type.equals(ButtonType.POV);
    }

}
