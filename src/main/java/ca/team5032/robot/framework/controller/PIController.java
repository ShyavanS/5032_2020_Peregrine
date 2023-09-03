package ca.team5032.robot.framework.controller;

import lombok.Getter;

public class PIController {

    // Constants (require fine tuning)
    private final static double PID_KP = 1.0;
    private final static double PID_KI = 1.0;

    private final static double PID_MINIMUM_OUTPUT = 0.05;

    // Change in adjust overtime.
    @Getter
    private double change;

    // Previous adjustment, used to calculate change.
    @Getter
    private double previousAdjust;

    // Input position, trying to make this equal target.
    @Getter
    private double input;

    // Target number, our goal.
    @Getter
    private double target;

    public PIController() {
        // Start out with change as 0.
        this.change = 0.0;
    }

    public void set(double input) {
        // Set the continuously updated input to provided value.
        this.input = input;
    }

    public void setTarget(double target) {
        // Update target.
        this.target = target;
    }

    public double get() {
        // If we are at our target, don't change.
        if (this.input == this.target) return 0.0;

        // Construct error as the inverse of input.
        double error = -this.input;
        // Default adjust.
        double adjust;

        // If the input is higher than one,
        if (this.input > 1.0) {
            // We want to reduce input so negative values.
            adjust = PID_KP * error - PID_MINIMUM_OUTPUT;
        } else {
            // We want to increase input so positive values.
            adjust = PID_KP * error + PID_MINIMUM_OUTPUT;
        }

        // Calculate the difference between the previous adjustment and the current adjustment.
        this.change = Math.abs(this.previousAdjust - adjust);
        // Assign the current adjustment to the previous adjustment.
        this.previousAdjust = adjust;

        // Apply the difference between the previous adjustment and the current adjustment to the current adjustment,
        // scaled down by our kI constant.
        adjust += PID_KI * this.change;

        // Return our calculated adjust.
        return adjust;
    }

    public void reset() {
        // Reset everything!
        this.change = 0.0;
        this.previousAdjust = 0.0;
        this.input = 0.0;
        this.target = 0.0;
    }

}
