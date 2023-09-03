package ca.team5032.robot.commands;

import ca.team5032.robot.framework.command.*;
import ca.team5032.robot.framework.command.button.ButtonStatus;
import ca.team5032.robot.framework.command.button.JoystickButton;
import ca.team5032.robot.joystick.GameJoystick;

@CommandGroup
public class IndexCommands {

    @Command
    @Trigger(joystick = GameJoystick.DRIVE, value = JoystickButton.L1)
    @TriggerMode(ButtonStatus.ON_PRESSED)
    public void transfer(CommandEnvironment env) {
        env.getRobot().getIndexingSubsystem().cycle();
    }

    @Command
    @Trigger(joystick = GameJoystick.DRIVE, value = JoystickButton.L1)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public void transferReleased(CommandEnvironment env) {
        env.getRobot().getIndexingSubsystem().stop();
    }


}
