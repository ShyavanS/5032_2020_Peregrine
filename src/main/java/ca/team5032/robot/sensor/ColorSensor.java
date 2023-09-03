package ca.team5032.robot.sensor;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ColorSensor {

    @Getter @AllArgsConstructor
    public enum ColorResult {
        RED(ColorMatch.makeColor(0.561, 0.232, 0.114)),
        GREEN(ColorMatch.makeColor(0.197, 0.561, 0.240)),
        BLUE(ColorMatch.makeColor(0.143, 0.427, 0.429)),
        YELLOW(ColorMatch.makeColor(0.361, 0.524, 0.113));

        private final Color color;

        public static ColorResult getResult(Color color) {
            for (ColorResult result : ColorResult.values()) {
                if (result.getColor().equals(color)) return result;
            }
            return null;
        }
    }

    // REV Color Sensor for detecting colour of wheel.
    private ColorSensorV3 colorSensorV3;
    // Colour matcher for matching the sensed colour to one of the colours.
    private ColorMatch colorMatch;

    public ColorSensor() {
        // Define both color utilities.
        this.colorSensorV3 = new ColorSensorV3(I2C.Port.kOnboard);
        this.colorMatch = new ColorMatch();

        // Add the default colours to the colour matcher.
        this.addDefaultColors();
    }

    private void addDefaultColors() {
        // Loop over all the ColorResult enums.
        for (ColorResult result : ColorResult.values()) {
            // Add the colour from the result to the colour matcher.
            this.colorMatch.addColorMatch(result.getColor());
        }
    }

    // Optionally method to add additional colour, not used.
    public void addColor(Color color) {
        this.colorMatch.addColorMatch(color);
    }

    // Get the match as a colour result. Guarantees confidence level is over a certain threshold.
    public ColorResult getMatch() {
        // Use provided colour matcher to match the closet color to what the sensor detects.
        ColorMatchResult colorMatcherResult = colorMatch.matchClosestColor(colorSensorV3.getColor());

        // Verify it is fairly confident in the result.
        if (colorMatcherResult.confidence > 0.5) {
            // Return the associated colour result enum from the matcher's resulting colour.
            return ColorResult.getResult(colorMatcherResult.color);
        }
        return null;
    }

}
