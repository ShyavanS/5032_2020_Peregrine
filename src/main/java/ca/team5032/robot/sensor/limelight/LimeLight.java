package ca.team5032.robot.sensor.limelight;

import ca.team5032.robot.OI;
import ca.team5032.robot.utils.NetworkTableUtils;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.drive.Vector2d;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * LimeLight
 */
@Getter
public class LimeLight {

    @Getter
    @AllArgsConstructor
    public enum CameraMode {
        DRIVE(1),
        PROCESSING(0);

        private int value;

        static CameraMode fromValue(int value) {
            for (CameraMode mode: CameraMode.values()) {
                if (mode.getValue() == value) return mode;
            }
            return null;
        }
    }

    @AllArgsConstructor
    @Getter
    public enum Pipeline {
        REFLECTIVE_TAPE(0);

        private int value;

        static Pipeline fromValue(int value) {
            for (Pipeline mode: Pipeline.values()) {
                if (mode.getValue() == value) return mode;
            }
            return null;
        }
    }

    @AllArgsConstructor
    @Getter
    public enum LEDMode {
        CURRENT(0), OFF(1), BLINK(2), ON(3);

        private int value;

        static LEDMode fromValue(int value) {
            for (LEDMode mode: LEDMode.values()) {
                if (mode.getValue() == value) return mode;
            }
            return null;
        }
    }

    private NetworkTable table;
    private boolean targeting = false;

    public LimeLight() {
        // Grab the network table for the limelight.
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public void startTarget(Pipeline pipeline) {
        // Set the target pipeline to the provided one.
        setPipeline(pipeline);
        // Turn on the LEDs.
        setLEDMode(LEDMode.ON);

        // Signal that we are targeting now.
        this.targeting = true;
    }

    public LimeLightTarget getTarget() {
        // Verify there is a valid target and we are targeting. If there isn't a target but we are targeting flash the LEDs.
        if (hasTarget() && isTargeting()) {
            // Turn on the LEDs if not already on.
            if (!getLEDMode().equals(LEDMode.ON)) setLEDMode(LEDMode.ON);

            // Now that the LEDs are on, the limelight can detect the tape.
            double tx = NetworkTableUtils.getDouble(table, "tx");
            double ty = NetworkTableUtils.getDouble(table, "ty");
            double ta = NetworkTableUtils.getDouble(table, "ta");
            double ts = NetworkTableUtils.getDouble(table, "ts");

            double thor = NetworkTableUtils.getDouble(table, "thor");
            double tvert = NetworkTableUtils.getDouble(table, "tvert");

            // Return a limelight target class with our values.
            return new LimeLightTarget(thor, tvert, ts, ta, new Vector2d(tx, ty));
        } else if (isTargeting()) {
            // Blink LEDs if no target.
            setLEDMode(LEDMode.BLINK);
        }

        // If there is no target and we aren't targeting then return nothing.
        return null;
    }

    public double getDistance() {
        // (h2 - h1) / tan(a1 + a2) ::: Calculates distance to target.
        double ty = NetworkTableUtils.getDouble(table, "ty");
        return (OI.LIMELIGHT_HEIGHT_OF_OUTER_PORT - OI.LIMELIGHT_HEIGHT_OF_LIMELIGHT) / Math.tan(OI.LIMELIGHT_ANGLE + ty); // + getTarget().getOffset().y
    }

    public void finishTarget() {
        // If we're targeting than turn off the LEDs.
        if (isTargeting()) {
            setLEDMode(LEDMode.OFF);

            // Signal that we are no longer targeting.
            this.targeting = false;
        }
    }

    // Utility methods for limelight.
    public void setLEDMode(LEDMode mode) {
        table.getEntry("ledMode").setNumber(mode.getValue());
    }

    public LEDMode getLEDMode() {
        return LEDMode.fromValue(table.getEntry("ledMode").getNumber(0.0).intValue());
    }

    public void setPipeline(Pipeline pipeline) {
        table.getEntry("pipeline").setNumber(pipeline.getValue());
    }

    public Pipeline getPipeline() {
        return Pipeline.fromValue(table.getEntry("getpipe").getNumber(0.0).intValue());
    }

    public boolean hasTarget() {
        return NetworkTableUtils.getDouble(table, "tv") == 1.0;
    }

    public void setMode(CameraMode mode) {
        table.getEntry("camMode").setNumber(mode.getValue());
    }

    public CameraMode getMode() {
        return CameraMode.fromValue(table.getEntry("camMode").getNumber(0.0).intValue());
    }

}