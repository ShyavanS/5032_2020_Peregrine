package ca.team5032.robot.systems;

import ca.team5032.robot.OI;
import ca.team5032.robot.Robot;
import ca.team5032.robot.framework.subsystem.Subsystem;

import java.util.Timer;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lombok.Getter;

public class IndexingSubsystem extends Subsystem {

    // TODO: Figure out how many motors & victors.
    private DigitalInput shooterSensor;
    private DigitalInput indexSensor;
    
    @Getter
    private DigitalInput intakeChamber;

    @Getter
	public WPI_VictorSPX channelBottom;

    private boolean cycling;
    private int ticksWithoutDetection;
    private boolean prevSensor = false;
    private boolean triggered = false;

    public IndexingSubsystem(Robot robot, boolean defaultEnabled) {
        super(robot, "IndexingSubsystem", defaultEnabled);

        this.channelBottom = new WPI_VictorSPX(OI.SYSTEM_TRANSFER_CHANNEL_BOTTOM_ID);

        this.intakeChamber = new DigitalInput(1);
        this.indexSensor = new DigitalInput(2);
        this.shooterSensor = new DigitalInput(3);

        this.cycling = false;
        this.ticksWithoutDetection = 0;
    }

    @Override
    public void tick() {
        // If it's taken more than 5 seconds for the ball to show up in the shooter than stop.       
        // if (intakeChamber.get()) {
        //     triggered = true;
        // }
        // if (!prevSensor && indexSensor.get()) {
        //     triggered = false;
        // } 
        // prevSensor = indexSensor.get();
        // if (triggered) {
        //     channelBottom.set(-0.7);
        // } else {
        //     channelBottom.set(0.0);
        // }
    }

    @Override
    public void setDashboard() {
        SmartDashboard.putBoolean("Optical Sensor #1", this.intakeChamber.get());
        SmartDashboard.putBoolean("Optical Sensor #2", this.indexSensor.get());
        SmartDashboard.putBoolean("Optical Sensor #3", this.shooterSensor.get());
    }

    public void cycle() {
        // Trigger cycling.
        this.cycling = true;
    }

    public void stop() {
        channelBottom.stopMotor();
    }
}
