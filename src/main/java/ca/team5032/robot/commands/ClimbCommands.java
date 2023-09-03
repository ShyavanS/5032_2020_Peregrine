package ca.team5032.robot.commands;

import ca.team5032.robot.OI;
import ca.team5032.robot.framework.command.*;
import ca.team5032.robot.framework.command.button.ButtonStatus;
import ca.team5032.robot.framework.command.button.JoystickButton;
import ca.team5032.robot.joystick.GameJoystick;

@CommandGroup
public class ClimbCommands {

    @Command
    @POVTrigger(joystick = GameJoystick.COSMETIC, value = 180)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void onUpClimbHeld(CommandEnvironment env) {
        //env.getRobot().getClimbSubsystem().disengageSolenoid();
        env.getRobot().getClimbSubsystem().windUpClimber(-4);
        env.getRobot().getClimbSubsystem().windUpHooker();
    }

    @Command
    @POVTrigger(joystick = GameJoystick.COSMETIC, value = 0)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void onUpClimbReleased(CommandEnvironment env) {
        //env.getRobot().getClimbSubsystem().engageSolenoid();
        env.getRobot().getClimbSubsystem().windDownHooker();
        env.getRobot().getClimbSubsystem().windUpClimber(1);
    }

    @Command
    @POVTrigger(joystick = GameJoystick.COSMETIC, value = 180)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void onDownClimbHeld(CommandEnvironment env) {
        //env.getRobot().getClimbSubsystem().disengageSolenoid();
        env.getRobot().getClimbSubsystem().stop();
    }

    @Command
    @POVTrigger(joystick = GameJoystick.COSMETIC, value = 0)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void onDownClimbReleased(CommandEnvironment env) {
        //env.getRobot().getClimbSubsystem().engageSolenoid();
        env.getRobot().getClimbSubsystem().stop();
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.L2)
    @TriggerMode(ButtonStatus.ON_PRESSED)
    public static void yeet(CommandEnvironment env) {
        env.getRobot().getClimbSubsystem().engageSolenoid();
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.OPTIONS)
    @TriggerMode(ButtonStatus.ON_PRESSED)
    public static void yeet2(CommandEnvironment env) {
        env.getRobot().getClimbSubsystem().disengageSolenoid();
    }

    // Tighten the orange one
    @Command
    @Trigger(joystick = GameJoystick.DRIVE, value = JoystickButton.X)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void button1(CommandEnvironment env) {
        env.getRobot().getClimbSubsystem().windDownHooker();
    }

    @Command
    @Trigger(joystick = GameJoystick.DRIVE, value = JoystickButton.X)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void button1Release(CommandEnvironment env) {
        env.getRobot().getClimbSubsystem().stop();
    }

    // Loosen the orange one.
    @Command
    @Trigger(joystick = GameJoystick.DRIVE, value = JoystickButton.CIRCLE)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void button2(CommandEnvironment env) {
        env.getRobot().getClimbSubsystem().windUpHooker();
    }

    @Command
    @Trigger(joystick = GameJoystick.DRIVE, value = JoystickButton.CIRCLE)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void button2Release(CommandEnvironment env) {
        env.getRobot().getClimbSubsystem().stop();
    }

    // Loosen the black one
    @Command
    @Trigger(joystick = GameJoystick.DRIVE, value = JoystickButton.TRIANGLE)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void button3(CommandEnvironment env) {
        env.getRobot().getClimbSubsystem().windUpClimber(1);
    }

    @Command
    @Trigger(joystick = GameJoystick.DRIVE, value = JoystickButton.TRIANGLE)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void button3Release(CommandEnvironment env) {
        env.getRobot().getClimbSubsystem().stop();
    }

    // Tighten the black one
    @Command
    @Trigger(joystick = GameJoystick.DRIVE, value = JoystickButton.SQUARE)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void button4(CommandEnvironment env) {
        env.getRobot().getClimbSubsystem().windUpClimber(-1);
    }

    @Command
    @Trigger(joystick = GameJoystick.DRIVE, value = JoystickButton.SQUARE)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void button4Release(CommandEnvironment env) {
        env.getRobot().getClimbSubsystem().stop();
    }


}
