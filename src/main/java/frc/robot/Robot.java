/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.SpeedControllerGroup;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.networktables.*;

public class Robot extends TimedRobot {
  //private final WPI_TalonSRX frontLeft = new WPI_TalonSRX(3);
  //private final WPI_TalonSRX frontRight = new WPI_TalonSRX(4);
  //private final WPI_TalonSRX backLeft = new WPI_TalonSRX(1);
  //private final WPI_TalonSRX backRight = new WPI_TalonSRX(2);
  private final WPI_VictorSPX elevatorUp1 = new WPI_VictorSPX(5);
  private final WPI_VictorSPX elevatorUp2 = new WPI_VictorSPX(6);
  private final WPI_TalonSRX elevatorDown = new WPI_TalonSRX(1);
  private final WPI_TalonSRX colourSpinner = new WPI_TalonSRX(2);
  public Joystick stick1 = new Joystick(0);
  //public Joystick stick2 = new Joystick(1);

  //public SpeedControllerGroup leftGroup = new SpeedControllerGroup(frontLeft, backLeft);
  //public SpeedControllerGroup rightGroup = new SpeedControllerGroup(frontRight, backRight);

  //Starts a differential drive class
  //public DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);

  //Colour sensor initialization and set colour values
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();
  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
  private Color colourMatch;
  private boolean scanningColour = false;
  //private Color detectedColor = m_colorSensor.getColor();
  //private ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
  //private String colorString;

  //Grab camera switcher from network tables
  private final NetworkTableEntry cameraSelect = NetworkTableInstance.getDefault().getEntry("/PiSwitch");

  @Override
  public void robotInit() {
    //Add colour values to colour matcher
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);

    //Set default camera for switcher
    cameraSelect.setString("rPi Camera 0");
  }

  @Override
  public void robotPeriodic() {
    /**
     * The method GetColor() returns a normalized color value from the sensor and can be
     * useful if outputting the color to an RGB LED or similar. To
     * read the raw color, use GetRawColor().
     * 
     * The color sensor works best when within a few inches from an object in
     * well lit conditions (the built in LED is a big help here!). The farther
     * an object is the more light from the surroundings will bleed into the 
     * measurements and make it difficult to accurately determine its color.
     */

    //Camera Switcher
    if (stick1.getPOV() == 0) {
      cameraSelect.setString("rPi Camera 0");
    }
    if (stick1.getPOV() == 180) {
      cameraSelect.setString("rPi Camera 1");
    }
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    //drive.arcadeDrive(-stick1.getY(), stick1.getX());

    Color detectedColor = m_colorSensor.getColor();

    /**
     * Run the color match algorithm on our detected color
     */
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    
    String gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.length() > 0) {
    switch (gameData.charAt(0)) {
      case 'B' :
        colourMatch = kBlueTarget;
        break;
      case 'G' :
        colourMatch = kGreenTarget;
        break;
      case 'R' :
        colourMatch = kRedTarget;
        break;
      case 'Y' :
        colourMatch = kYellowTarget;
        break;
      default :
        break;
      }
    } else {}

    if (stick1.getRawButton(7) || scanningColour == true) {
      if (match.color != colourMatch) {
        colourSpinner.set(0.25);
        scanningColour = true;
      } else {
        colourSpinner.set(0.0);
        scanningColour = false;
      }
    }

    //Lift and lower elevator according to joystick
    if (stick1.getRawButton(3)) { // Button 3 - Talon 5
      elevatorUp1.set(1); // Set speed of this motor to sensitivity of slider
      elevatorUp2.set(1);
      elevatorDown.set(-0.25);
      // victor2.set(-calculateSensitivity() / 20); // Other motor spins slightly to aid the first motor
    } else if (!stick1.getRawButton(4)) {
      elevatorUp1.set(0.0); //Stop this motor when the other button is pressed
      elevatorUp2.set(0.0);
    } //else if (stick1.getRawButtonReleased(3)) {
      //elevatorDown.set(0.125); // Spin other motor for a bit as soon as the button is released to relieve slack
   // }

    if (stick1.getRawButton(4)) { // Button 4 - Talon 6
      elevatorDown.set(0.25);
      elevatorUp1.set(-1);
      elevatorUp2.set(-1);
      // victor1.set(-calculateSensitivity() / 20); 
    } else if (!stick1.getRawButton(3)) {
      elevatorDown.set(0.0);
    }// else if (stick1.getRawButtonReleased(4)) {
     // elevatorUp1.set(0.125);
     // elevatorUp2.set(0.125);
    //}
  }
}
