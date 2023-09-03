package ca.team5032.robot.framework.command;

import ca.team5032.robot.joystick.GameJoystick;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Trigger {
    GameJoystick joystick() default GameJoystick.DRIVE;
    int value();
}
