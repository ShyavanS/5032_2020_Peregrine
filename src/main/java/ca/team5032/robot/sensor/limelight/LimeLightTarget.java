package ca.team5032.robot.sensor.limelight;

import edu.wpi.first.wpilibj.drive.Vector2d;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value
public class LimeLightTarget {

    private double width;
    private double height;
    private double rotation;
    private double area;

    private Vector2d offset;

}
