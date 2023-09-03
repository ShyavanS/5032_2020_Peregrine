package ca.team5032.robot.systems;

import ca.team5032.robot.Robot;
import ca.team5032.robot.framework.command.*;
import ca.team5032.robot.framework.command.button.*;
import ca.team5032.robot.framework.subsystem.Subsystem;
import ca.team5032.robot.joystick.GameJoystick;
import edu.wpi.first.wpilibj.Joystick;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class CommandSubsystem extends Subsystem {

    // Previous POVs on joysticks;
    private Map<GameJoystick, Integer> previousPOVs;

    // Command map for registered commands. Associates a method with a command definition for future use.
    private Map<Method, CommandDefinition> commands;

    /**
     * Creates a new command subsystem. This subsystem control commands on the robot, commands being
     * the pressing or "triggering" of one or more buttons on the robot running an associated method.
     *
     * @param robot Robot main class, passed to individual commands when ran and used for general misc.
     */
    public CommandSubsystem(Robot robot, boolean defaultEnabled) {
        super(robot, "CommandSubsystem", defaultEnabled);

        // Initialize our commands with a new empty hashmap.
        this.commands = new HashMap<>();

        // Initialize hashmap for previous povs.
        this.previousPOVs = new HashMap<>();

        // Initial population of hashmap.
        this.previousPOVs.put(GameJoystick.COSMETIC, GameJoystick.COSMETIC.getJoystick().getPOV());
        this.previousPOVs.put(GameJoystick.DRIVE, GameJoystick.DRIVE.getJoystick().getPOV());
    }

    /**
     * Runs every periodical from the robot, loops over the command map and checks if commands should be ran.
     */
    @Override
    public void tick() {
        // Loop over all the entries of our command map.
        for (Map.Entry<Method, CommandDefinition> command : this.commands.entrySet()) {
            // Test if the command trigger passed.
            if (!test(command.getValue())) continue;
            try {
                //System.out.println("Running command: " + command.getKey().getName());
                // Attempt to the invoke the static method associated with the command.
                command.getKey().invoke(command.getKey().getClass(), new CommandEnvironment(getRobot()));
            } catch (IllegalAccessException | InvocationTargetException ignored) {}
        }

        this.previousPOVs.put(GameJoystick.COSMETIC, GameJoystick.COSMETIC.getJoystick().getPOV());
        this.previousPOVs.put(GameJoystick.DRIVE, GameJoystick.DRIVE.getJoystick().getPOV());
    }

    /**
     * Test if a command definition trigger passes at this time.
     *
     * @param cmd The command to test.
     * @return boolean true if the command trigger passes and boolean false if it does not.
     */
    private boolean test(CommandDefinition cmd) {
        // Extract the data from the annotation.
        Button[] buttons = cmd.getPrerequisite();
        Button triggerButton = cmd.getTrigger();
        ButtonType buttonType = cmd.getType();
        ButtonStatus mode = cmd.getMode();

        // Loop over each prerequisite button.
        for (Button button : buttons) {
            // Verify that the button is held down. This is required for the prerequisite buttons.
            if (!test(button, ButtonStatus.WHILE_PRESSED, buttonType)) return false;
        }

        // Test the trigger button with the associated trigger mode to check if the trigger has passed.
        return test(triggerButton, mode, buttonType);
    }

    /**
     * Test if a button is in a specific mode for triggering.
     *
     * @param button The button to test.
     * @param mode The mode to test if the button is in.
     * @return boolean true if the test passes and boolean false if it does not, or if mode is null.
     */
    private boolean test(Button button, ButtonStatus mode, ButtonType type) {
        // Get the joystick associated with the button.
        Joystick joystick = button.getJoystick().getJoystick();

        // Check for a different value depending on what trigger mode it is.
        switch (mode) {
            case ON_PRESSED:
                if (type.equals(ButtonType.JOYSTICK)) return joystick.getRawButtonPressed(((ControllerButton) button).getButtonID());
                else return !previousPOVs.get(button.getJoystick()).equals(((POVButton) button).getAngle()) &&
                        joystick.getPOV() == ((POVButton) button).getAngle();
            case ON_RELEASED:
                if (type.equals(ButtonType.JOYSTICK)) return joystick.getRawButtonReleased(((ControllerButton) button).getButtonID());
                else return previousPOVs.get(button.getJoystick()).equals(((POVButton) button).getAngle()) &&
                        !(joystick.getPOV() == ((POVButton) button).getAngle());
            case WHILE_PRESSED:
                if (type.equals(ButtonType.JOYSTICK)) return joystick.getRawButton(((ControllerButton) button).getButtonID());
                else return joystick.getPOV() == ((POVButton) button).getAngle();
        }

        // If mode is null, which it would be to get to this point, return false.
        return false;
    }

    /**
     * Registers a command grouping, which is a class with multiple methods containing the Command and Trigger
     * annotations.
     *
     * @param clazz Singleton class to register.
     */
    public void registerCommandGroup(Class clazz) {
        // Get all the methods from the class.
        Method[] methods = clazz.getDeclaredMethods();

        // Loop over each method for registration.
        for (Method method : methods) {
            // Method requires to be defined as a command and have a trigger or else it can not be run.
            if (!method.isAnnotationPresent(Command.class)) {
                continue;
            }

            // The button that will trigger the command.
            Button triggerButton;

            // The type of the button. (POV or JOYSTICK)
            ButtonType buttonType;

            if (method.isAnnotationPresent(Trigger.class)) {
                // Grab meaningful annotations off the method.
                Trigger trigger = method.getAnnotation(Trigger.class);

                // Create Joystick button from the trigger.
                triggerButton = new ControllerButton(trigger.joystick(), trigger.value());

                // Set the button type.
                buttonType = ButtonType.JOYSTICK;
            } else if (method.isAnnotationPresent(POVTrigger.class)) {
                // Grab the POVTrigger annotation.
                POVTrigger povTrigger = method.getAnnotation(POVTrigger.class);

                // Create POV button from the trigger.
                triggerButton = new POVButton(povTrigger.joystick(), povTrigger.value());

                // Set the button type.
                buttonType = ButtonType.POV;
            } else {
                continue;
            }

            // Initialize the default button status to the value of on pressed.
            ButtonStatus status = ButtonStatus.ON_PRESSED;

            // Check if the method has a trigger mode annotation.
            if (method.isAnnotationPresent(TriggerMode.class)) {
                // If there is, get the button status from the annotation.
                status = method.getAnnotation(TriggerMode.class).value();
            }

            // Same thing as before, initialize the default prerequisites to an empty array of buttons.
            ControllerButton[] prerequisites = {};

            // Check if the method has prerequisites defined.
            if (method.isAnnotationPresent(Prerequisite.class)) {
                // Get the trigger array of prerequisites from the method.
                Trigger[] triggers = method.getAnnotation(Prerequisite.class).value();

                // Map the trigger array to a button array using the button constructor and Stream#map, assigning the new array to our prerequisites.
                prerequisites = (ControllerButton[]) Arrays.stream(triggers).map(t -> new ControllerButton(t.joystick(), t.value())).toArray();
            }

            // Verify the method is not returning some weird junk.
            if (!Void.TYPE.equals(method.getReturnType())) continue;

            // Make sure the method is static.
            if (!Modifier.isStatic(method.getModifiers())) continue;

            // Make sure the method has one parameter that is a CommandEnvironment, if not, Method#invoke will throw an exception.
            Parameter[] parameters = method.getParameters();

            if (parameters.length != 1) continue;
            if (!parameters[0].getType().equals(CommandEnvironment.class)) continue;

            // Create the command definition for the method.
            CommandDefinition definition = new CommandDefinition(triggerButton, status, buttonType, prerequisites);

            // System.out.println("============");
            // System.out.println("Initializing new command: " + method.getName());
            // System.out.println("TRIGGER JOYSTICK: " + triggerButton.getJoystick().name());
            // System.out.println("BUTTON STATUS: " + status.name());
            // System.out.println("BUTTON TYPE: " + buttonType.name());
            // System.out.println("PREREQUISITES: " + Arrays.stream(prerequisites).map(button -> button.getJoystick().name()));
            // System.out.println("============");

            // Register the command definition in association with the method into the local command map.
            commands.put(method, definition);
        }
    }

    /**
     * Registers an array of classes into the command map.
     *
     * @param classes Array of classes to register.
     */
    public void registerCommandGroups(Class... classes) {
        // Stream the array and register each one individually.
        Arrays.stream(classes).forEach(this::registerCommandGroup);
    }

}
