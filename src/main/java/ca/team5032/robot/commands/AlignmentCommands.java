package ca.team5032.robot.commands;

import ca.team5032.robot.framework.command.*;
import ca.team5032.robot.framework.command.button.ButtonStatus;
import ca.team5032.robot.framework.command.button.JoystickButton;
import ca.team5032.robot.joystick.GameJoystick;
import ca.team5032.robot.sensor.limelight.LimeLight;

@CommandGroup
public class AlignmentCommands {

   @Command
   @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.R1)
   @TriggerMode(ButtonStatus.ON_PRESSED)
   public static void onAlignPressed(CommandEnvironment env) {
       env.getRobot().getLimeLight().startTarget(LimeLight.Pipeline.REFLECTIVE_TAPE);
   }

   @Command
   @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.R1)
   @TriggerMode(ButtonStatus.WHILE_PRESSED)
   public static void onAlignHeld(CommandEnvironment env) {
       env.getRobot().getAutoSubsystem().align();
   }

   @Command
   @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.R1)
   @TriggerMode(ButtonStatus.ON_RELEASED)
   public static void onAlignReleased(CommandEnvironment env) {
       env.getRobot().getLimeLight().finishTarget();
   }

}
