/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.team5032.robot;

import ca.team5032.robot.framework.command.CommandGroup;
import ca.team5032.robot.framework.subsystem.SubsystemManager;
import ca.team5032.robot.sensor.CameraSwitcher;
import ca.team5032.robot.sensor.limelight.LimeLight;
import ca.team5032.robot.sensor.limelight.LimeLight.LEDMode;
import ca.team5032.robot.systems.*;
import ca.team5032.robot.systems.drive.DriveSubsystem;
import ca.team5032.robot.systems.music.MusicSubsystem;
import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.TimedRobot;
import lombok.Getter;
import lombok.Setter;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
@Getter @Setter
public class Robot extends TimedRobot {

  @Getter
  public static Robot instance;

  // Subsystems
  private SubsystemManager subsystemManager;

  private DriveSubsystem driveSubsystem;
  private CommandSubsystem commandSubsystem;
  private AutoSubsystem autoSubsystem;
  private ShooterSubsystem shooterSubsystem;
  private IntakeSubsystem intakeSubsystem;
  private ClimbSubsystem climbSubsystem;
  private MusicSubsystem musicSubsystem;
  private IndexingSubsystem indexingSubsystem;

  // Vision
  private LimeLight limeLight;
  private CameraSwitcher cameraSwitcher;

  private ADIS16448_IMU adis;

  Robot() {
    super(0.03);
    Robot.instance = this;
  }

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    // Define the limelight manager.
    this.limeLight = new LimeLight();

    // Define camera switcher.
    //this.cameraSwitcher = new CameraSwitcher("Switcher", OI.SYSTEM_CAMERA_USBCAMERA_1, OI.SYSTEM_CAMERA_USBCAMERA_2);

    // Define subsystem manager.
    this.subsystemManager = new SubsystemManager();

    // Subsystem definitions.
    this.commandSubsystem = new CommandSubsystem(this, true); //
    this.driveSubsystem = new DriveSubsystem(this, true);
    this.autoSubsystem = new AutoSubsystem(this, true);
    this.climbSubsystem = new ClimbSubsystem(this, true); //
    this.intakeSubsystem = new IntakeSubsystem(this, true); //
    this.shooterSubsystem = new ShooterSubsystem(this, true);
    this.musicSubsystem = new MusicSubsystem(this, false);
    this.indexingSubsystem = new IndexingSubsystem(this, true);

    this.adis = new ADIS16448_IMU();

    // Register all commands to the command subsystem.
    this.registerCommands();
  }

  private void registerCommands() {
    // Initialize reflection object for commands package.
    Reflections reflection = new Reflections(
            new ConfigurationBuilder().addScanners(new SubTypesScanner(), new TypeAnnotationsScanner())
            .addUrls(ClasspathHelper.forPackage("ca.team5032.robot.commands"))
    );

    // Get all of the classes with the command group annotation.
    Set<Class<?>> commandClasses = reflection.getTypesAnnotatedWith(CommandGroup.class);

    // Loop over each class and register it.
    commandClasses.forEach(clazz -> this.commandSubsystem.registerCommandGroup(clazz));
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Signal the subsystem manager to run the tick method on all subsystems.
    this.subsystemManager.tick();
    //For debugging
    // this.limeLight.setLEDMode(LEDMode.ON);
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    subsystemManager.disable();
  }

  @Override
  public void disabledPeriodic() {

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    //System.out.println("autonomousEnabling");
    subsystemManager.autoPeriodic();
  }

  @Override
  public void teleopInit() {
    subsystemManager.enable();
    getClimbSubsystem().disengageSolenoid();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
