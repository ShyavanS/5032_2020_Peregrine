package ca.team5032.robot.sensor;

// import edu.wpi.cscore.MjpegServer;
// import edu.wpi.cscore.UsbCamera;
// import edu.wpi.cscore.VideoSource;
// import edu.wpi.first.cameraserver.CameraServer;
// import edu.wpi.first.wpilibj.Joystick;
// import lombok.Getter;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.stream.Collectors;

// @Getter
public class CameraSwitcher {

//     // Name of camera switcher.
//     private String name;
//     // Index of currently selected camera.
//     private int selected = 0;

//     // List of all cameras available to the switcher.
//     private List<VideoSource> cameras = new ArrayList<>();
//     // Mjpeg server for switcher.
//     private MjpegServer switcher;

//     public CameraSwitcher(String name, int... ids) {
//         // Assign camera switcher name.
//         this.name = name;

//         // Create a new switched camera.
//         switcher = CameraServer.getInstance().addSwitchedCamera(name);

//         // Default configuration for camera, 60fps 320p.
//         switcher.setFPS(60);
//         switcher.setResolution(320, 240);

//         // Map all the provided ids and resolve USB cameras from them.
//         cameras.addAll(Arrays.stream(ids).mapToObj(this::resolveUSBCamera).collect(Collectors.toList()));
    
//         // Update the source to start streaming.
//         updateSource();
//     }

//     public void nextCamera() {
//         // Increment the selected index.
//         selected++;

//         // If the selected index is over the cameras size, set it back to 0.
//         if (selected >= cameras.size()) selected = 0;

//         // Signal to update the switcher's source.
//         updateSource();
//     }

//     public void previousCamera() {
//         // Decrement the selected index.
//         selected--;

//         // If it's less than 0, set it to the maximum index.
//         if (selected < 0) selected = cameras.size() - 1;

//         // Signal to update the switcher's source.
//         updateSource();
//     }

//     private void updateSource() {
//         // Set the source of the switcher from the selected index in the camera list.
//         switcher.setSource(cameras.get(selected));
//     }

//     private UsbCamera resolveUSBCamera(int id) {
//         // Start the automatic capture of the USB camera. Format the name [name]:Switcher%USB[id]
//         UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(String.format("%s:Switcher@USB%d", name, id), id);

//         // Default configuration for the camera. 60fps 320p keepOpen.
//         camera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
//         camera.setFPS(60);
//         camera.setResolution(320, 240);

//         // Return the resolved camera.
//         return camera;
//     }

}
