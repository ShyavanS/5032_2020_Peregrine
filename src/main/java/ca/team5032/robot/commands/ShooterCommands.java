package ca.team5032.robot.commands;

import ca.team5032.robot.framework.command.*;
import ca.team5032.robot.framework.command.button.ButtonStatus;
import ca.team5032.robot.framework.command.button.JoystickButton;
import ca.team5032.robot.joystick.GameJoystick;
import ca.team5032.robot.systems.ShooterSubsystem;
import edu.wpi.first.wpilibj.AnalogInput;

@CommandGroup
public class ShooterCommands {

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.R2)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void onShoot(CommandEnvironment env) {
        env.getRobot().getShooterSubsystem().shoot();
    }

    @Command
    @Trigger(joystick = GameJoystick.COSMETIC, value = JoystickButton.R2)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void onShootReleased(CommandEnvironment env) {
        //env.getRobot().getIndexingSubsystem().stop();
        env.getRobot().getShooterSubsystem().stop();
        env.getRobot().getIndexingSubsystem().stop();
    }

    @Command
    @POVTrigger(joystick = GameJoystick.DRIVE, value = 90)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void onHoodUpRelease(CommandEnvironment env) {
        env.getRobot().getShooterSubsystem().stop();
    }

    @Command
    @POVTrigger(joystick = GameJoystick.DRIVE, value = 90)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void onHoodUp(CommandEnvironment env) {
        env.getRobot().getShooterSubsystem().rotate(0.15);
    }

    @Command
    @POVTrigger(joystick = GameJoystick.DRIVE, value = 270)
    @TriggerMode(ButtonStatus.ON_RELEASED)
    public static void onHoodDownRelease(CommandEnvironment env) {
        env.getRobot().getShooterSubsystem().stop();
    }

    @Command
    @POVTrigger(joystick = GameJoystick.DRIVE, value = 270)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void onHoodDown(CommandEnvironment env) {
        env.getRobot().getShooterSubsystem().rotate(-0.15);
    }

    @Command
    @Trigger(joystick = GameJoystick.DRIVE, value = JoystickButton.PAD)
    @TriggerMode(ButtonStatus.WHILE_PRESSED)
    public static void onWall(CommandEnvironment env) { //Distance = 0 in
        if (env.getRobot().getShooterSubsystem().getAngle().getVoltage() < 2.45) {
            env.getRobot().getShooterSubsystem().rotate(0.07);
        } else if (env.getRobot().getShooterSubsystem().getAngle().getVoltage() > 2.65) { // deadzone
            env.getRobot().getShooterSubsystem().rotate(-0.07);
        } else {
            env.getRobot().getShooterSubsystem().rotate(0.0);
        }
   }

//    @Command
//    @Trigger(joystick = GameJoystick.DRIVE, value = JoystickButton.PAD)
//    @TriggerMode(ButtonStatus.WHILE_PRESSED)
//    public static void onTrenchRun(CommandEnvironment env) {
//         if (env.getRobot().getShooterSubsystem().getAngle().getVoltage() < 0.8) {
//             env.getRobot().getShooterSubsystem().rotate(-0.25);
//         } else if (env.getRobot().getShooterSubsystem().getAngle().getVoltage() > 9.0) { // deadzone
//             env.getRobot().getShooterSubsystem().rotate(0.25);
//         } else {
//             env.getRobot().getShooterSubsystem().rotate(0.0);   
//         }
//    }

   @Command
   @Trigger(joystick = GameJoystick.DRIVE, value = JoystickButton.OPTIONS)
   @TriggerMode(ButtonStatus.WHILE_PRESSED)
   public static void onLine(CommandEnvironment env) { //Distance = 120 in
        if (env.getRobot().getShooterSubsystem().getAngle().getVoltage() < 1.9) {
            env.getRobot().getShooterSubsystem().rotate(-0.25);
        } else if (env.getRobot().getShooterSubsystem().getAngle().getVoltage() > 2.0) { // deadzone
            env.getRobot().getShooterSubsystem().rotate(0.25);
        } else {
            env.getRobot().getShooterSubsystem().rotate(0.0);
        }
   }
}
