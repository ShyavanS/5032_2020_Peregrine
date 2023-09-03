package ca.team5032.robot.framework.subsystem;

import ca.team5032.robot.Robot;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Subsystem {

    private String name;
    private Robot robot;

    private boolean enabled;

    public Subsystem(Robot robot, String name, boolean defaultEnabled) {
        this.robot = robot;
        this.name = name;

        // Default enabled value.
        this.enabled = defaultEnabled;

        // Register ourselves in the robots subsystem manager.
        this.robot.getSubsystemManager().registerSubsystem(this);
    }

    public abstract void tick();

    public void disable() {}
    public void enable() {}
    public void autoPeriodic() {}

    public void kill() {}

    public void setDashboard() { }

}
