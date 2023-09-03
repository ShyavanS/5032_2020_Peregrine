package ca.team5032.robot.utils;

import edu.wpi.first.networktables.NetworkTable;

public class NetworkTableUtils {

    public static double getDouble(NetworkTable table, String key) {
        return getDouble(table, key, 1.0);
    }

    public static double getDouble(NetworkTable table, String key, double defaultValue) {
        return table.getEntry(key).getDouble(defaultValue);
    }

    public static String getString(NetworkTable table, String key) {
        return getString(table, key, "");
    }

    public static String getString(NetworkTable table, String key, String defaultValue) {
        return table.getEntry(key).getString(defaultValue);
    }

    public static boolean getBoolean(NetworkTable table, String key) {
        return getBoolean(table, key, false);
    }

    public static boolean getBoolean(NetworkTable table, String key, boolean defaultValue) {
        return table.getEntry(key).getBoolean(defaultValue);
    }

}
