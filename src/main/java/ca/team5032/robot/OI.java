package ca.team5032.robot;

public class OI {

    public static final int UNITS_PER_ROTATION = 2048;
    public static final double WHEEL_CIRCUMFERENCE = 6 * Math.PI;

    public static final double LIMELIGHT_HEIGHT_OF_LIMELIGHT = 30;
    public static final double LIMELIGHT_HEIGHT_OF_OUTER_PORT = 98.25;
    public static final double LIMELIGHT_ANGLE = 30;

    // TODO: actual values
    // TODO: better names
    public static final int SYSTEM_CAMERA_USBCAMERA_1 = 0;
    public static final int SYSTEM_CAMERA_USBCAMERA_2 = 1;

    public static final int SYSTEM_CLIMB_TALON_LEFT_ID = 7;
    public static final int SYSTEM_CLIMB_TALON_RIGHT_ID = 8;
    public static final int SYSTEM_CLIMB_HOOKER_ID = 9;

    public static final int SYSTEM_INTAKE_VICTOR_INTAKE_ID = 3;
    public static final int SYSTEM_INTAKE_VICTOR_ROTATE_ID = 10;

    public static final int SYSTEM_SHOOTER_SPEED_VICTOR_ID = 6;
    public static final int SYSTEM_SHOOTER_HOOD_VICTOR_ID = 4;

    public static final int SYSTEM_TRANSFER_CHANNEL_BOTTOM_ID = 2;

    public static final int DRIVE_JOYSTICK_ID = 0;
    public static final int COSMETIC_JOYSTICK_ID = 1;

    public static final String SYSTEM_PREFIX = "System";

    public static final int COMMAND_ALIGN_SLOWING_RADIUS = 16;
    public static final double COMMAND_ALIGN_MAX_VELOCITY = 0.5;
    public static final double COMMAND_ALIGN_MIN_VELOCITY = 0.4;
}
