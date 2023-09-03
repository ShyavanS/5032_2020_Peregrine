package ca.team5032.robot.framework.subsystem;

import java.util.ArrayList;
import java.util.List;

public class SubsystemManager {

    private List<Subsystem> subsystems;
    private boolean enabled;

    public SubsystemManager() {
        // Define subsystems as new empty list.
        this.subsystems = new ArrayList<>();
        this.enabled = false;
    }

    public void tick() {
        // Loop over each subsystem and ask them to set their dashboards.
        subsystems.forEach(Subsystem::setDashboard);

        // If the manager isn't enabled, don't call tick on the subsystems.
        if (!this.enabled) return;

        // Loop over all enabled subsystems and invoke tick method.
        subsystems.stream().filter(Subsystem::isEnabled).forEach(Subsystem::tick);

    }

    public void enable() {
        //System.out.println("[SubsystemManager] Enabling subsystems.");
        this.enabled = true;
        subsystems.forEach(Subsystem::enable);
    }

    public void disable() {
        //System.out.println("[SubsystemManager] Disabling subsystems.");
        this.enabled = false;
        subsystems.forEach(Subsystem::disable);
        subsystems.forEach(Subsystem::kill);
    }

    public void autoPeriodic() {
        //System.out.println(subsystems.size());
        subsystems.forEach(Subsystem::autoPeriodic);
    }

    void registerSubsystem(Subsystem subsystem) {
        // Add subsystem to our list for later use.
        this.subsystems.add(subsystem);
    }

}
