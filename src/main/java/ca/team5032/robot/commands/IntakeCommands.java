package ca.team5032.robot.commands;

import ca.team5032.robot.framework.command.*;
import ca.team5032.robot.framework.command.button.ButtonStatus;
import ca.team5032.robot.framework.command.button.JoystickButton;
import ca.team5032.robot.joystick.GameJoystick;

@CommandGroup
public class IntakeCommands {

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.CIRCLE)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void onAccept(CommandEnvironment env) {
        // Accept the ball.
        env.getRobot().getIntakeSubsystem().accept(1.0);
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.SQUARE)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void onDispense(CommandEnvironment env) {
        // Eject the ball.
        env.getRobot().getIntakeSubsystem().accept(-0.7);
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.SQUARE)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void onDispenseRelease(CommandEnvironment env) {
        env.getRobot().getIntakeSubsystem().stop();
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.CIRCLE)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void onAcceptRelease(CommandEnvironment env) {
        //env.getRobot().getIndexingSubsystem().stop();
        env.getRobot().getIntakeSubsystem().stop();
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.TRIANGLE)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void onPivotUp(CommandEnvironment env) {
        env.getRobot().getIntakeSubsystem().pivot(0.4);
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.X)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void onPivotDown(CommandEnvironment env) {
        env.getRobot().getIntakeSubsystem().pivot(-0.4);
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.TRIANGLE)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void onPivotUpRelease(CommandEnvironment env) {
        env.getRobot().getIntakeSubsystem().stop();
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.X)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void onPivotDownRelease(CommandEnvironment env) {
        env.getRobot().getIntakeSubsystem().stop();
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.L1)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void onTransfer(CommandEnvironment env) {
        env.getRobot().getIndexingSubsystem().channelBottom.set(-0.7);
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.L1)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void releaseTransfer(CommandEnvironment env) {
        env.getRobot().getIndexingSubsystem().channelBottom.set(0.0);
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.SHARE)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void onTransferOut(CommandEnvironment env) {
        env.getRobot().getIndexingSubsystem().channelBottom.set(0.7);
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.SHARE)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void releaseTransferOut(CommandEnvironment env) {
        env.getRobot().getIndexingSubsystem().channelBottom.set(0.0);
    }
}
