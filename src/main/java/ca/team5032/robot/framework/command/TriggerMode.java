package ca.team5032.robot.framework.command;

import ca.team5032.robot.framework.command.button.ButtonStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TriggerMode {
    ButtonStatus value();
}
