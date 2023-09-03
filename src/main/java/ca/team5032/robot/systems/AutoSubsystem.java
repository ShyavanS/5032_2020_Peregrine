package ca.team5032.robot.systems;

import java.util.Timer;
import java.util.TimerTask;

import ca.team5032.robot.Robot;
import ca.team5032.robot.framework.subsystem.Subsystem;
import ca.team5032.robot.sensor.limelight.LimeLight;
import ca.team5032.robot.sensor.limelight.LimeLightTarget;
import ca.team5032.robot.utils.NetworkTableUtils;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.drive.Vector2d;

public class AutoSubsystem extends Subsystem {

    private boolean targetFound;
    // private boolean autoFeed = true;
    private final int powerPortHeight = 10;
    private final int limeLightHeight = 3;
    private final int limeLightAngle = 30;
    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private double ty = NetworkTableUtils.getDouble(table, "ty");
    // private Timer driver = new Timer();
    private Timer spooler = new Timer();

    public AutoSubsystem(Robot robot, boolean defaultEnabled) {
        super(robot, "AutoSubsystem", defaultEnabled);
        this.targetFound = false;
    }

    @Override
    public void autoPeriodic() {
    // Buddy Auto w/ 5024 @ Humber
    //     //drive
    //     if (!autoFeed) {
    //         getRobot().getDriveSubsystem().drive(0.0, 0.6);
    //     driver.schedule(new TimerTask(){
        
    //         @Override
    //         public void run() {
    //             getRobot().getDriveSubsystem().drive(0.0, 0.0);
                
    //         }
    //     }, 1000);
    //     } else {
    //         //feed
    //         driver.schedule(new TimerTask(){
            
    //             @Override
    //             public void run() {
    //                 getRobot().getIntakeSubsystem().accept(-0.55);
    //                 getRobot().getIndexingSubsystem().channelBottom.set(0.55);
    //             }
    //         }, 3000);
    //         driver.schedule(new TimerTask(){
            
    //             @Override
    //             public void run() {
    //                 getRobot().getIntakeSubsystem().accept(0.0);
    //                 getRobot().getIndexingSubsystem().channelBottom.set(0.0);
    //             }
    //         }, 10000);
    //     }

        if (Math.abs(getRobot().getDriveSubsystem().getRightDistance()) < 120) {
            getRobot().getDriveSubsystem().drive(0.0, -0.75);
        }
        else {
            getRobot().getDriveSubsystem().drive(0, 0);
            getRobot().getShooterSubsystem().shoot();
            spooler.schedule(new TimerTask() {
            
                @Override
                public void run() {
                    getRobot().getIndexingSubsystem().channelBottom.set(-0.7);
                }
            }, 4000);
            spooler.schedule(new TimerTask(){
            
                @Override
                public void run() {
                    getRobot().getIndexingSubsystem().channelBottom.set(0.0);
                    getRobot().getShooterSubsystem().stop();
                }
            }, 12000);
        }
    }

    // @Override
    // public void setDashboard() {
    //     SmartDashboard.putNumber("Right Distance", getRobot().getDriveSubsystem().getRightDistance());
    // }

    @Override
    public void tick() {
        // ty = NetworkTableUtils.getDouble(table, "ty");
        // getRobot().getDriveSubsystem().tankDrive(0.4, -0.4);
        // double depth = ((powerPortHeight - limeLightHeight) / Math.tan(limeLightAngle + ty));
        // double angleShooterValue = (Math.pow(2.3429 * depth, 2) - 19.39 * depth + 87.114);
        // if (angle.getVoltage() < 0.1) {
        //     getRobot().getShooterSubsystem().rotate(0.25);
        // } else if (angle.getVoltage() > 0.2) { // deadzone
        //     getRobot().getShooterSubsystem().rotate(-0.25);
        // } else {
        //     getRobot().getShooterSubsystem().rotate(0.0);
            
        // }
        // if (getRobot().getLimeLight().hasTarget()) {
        // }
    }

    public void align() {
        // LimeLightTarget target = getRobot().getLimeLight().getTarget();
        // if (target == null) return;

        // Vector2d offset = target.getOffset();
        // if (offset.x == 0) return;

        // double slowingRadius = 16;
        // double maxVelocity = 0.485;

        // double xOffset = offset.x;
        // double xTarget = 0.0;
        // double desired = xTarget - xOffset;
        // double distance = Math.abs(desired);
        
        // double depth = ((powerPortHeight - limeLightHeight) / Math.tan(limeLightAngle + ty));
        // double angleShooterValue = (Math.pow(2.3429 * depth, 2) - 19.39 * depth + 87.114);
        // if (angle.getVoltage() < 0.5) {
        //     //getRobot().getShooterSubsystem().rotate(0.25);
        // } else if (angle.getVoltage() > 0.2) { // deadzone
        //     //getRobot().getShooterSubsystem().rotate(-0.25);
        // } else {
        //     //getRobot().getShooterSubsystem().rotate(0.0);
            
        // }
        // if (getRobot().getLimeLight().hasTarget()) {
        // }

        // desired = desired > 0 ? -1 : 1;
        // if (distance < slowingRadius) {
        //     desired *= maxVelocity * (distance / slowingRadius);
        // } else {
        //     desired *= maxVelocity;
        // }

        // if (distance > 1) {
        //     desired = desired > 0 ?
        //             Math.max(desired, 0.4) :
        //             Math.min(desired, -0.4);
        // } else {
        //     getRobot().getDriveSubsystem().stop();
        //     return;
        // }

        // SmartDashboard.putNumber("Desired Velocity", desired);

        // getRobot().getDriveSubsystem().tankDrive(desired, desired);
    }
}
