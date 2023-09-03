package ca.team5032.robot.systems.music;

import ca.team5032.robot.Robot;
import ca.team5032.robot.framework.subsystem.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

import java.util.Arrays;
import java.util.LinkedList;

public class MusicSubsystem extends Subsystem {

    // The queue of songs. Once one song ends the next song begins.
    private LinkedList<Song> queue;

    // The Orchestra from CTRE, used for playing .chrp files.
    private Orchestra orchestra;

    /**
     * Constructs a new MusicSubsystem, which manages music.
     *
     * @param robot The robot the system is associated with.
     */
    public MusicSubsystem(Robot robot, boolean defaultEnabled) {
        super(robot, "MusicSubsystem", defaultEnabled);
        this.queue = new LinkedList<>();
        this.orchestra = new Orchestra();

        // Create a list of all the falcons on our robot.
        TalonFX[] falcons = new TalonFX[] {
                new TalonFX(0),
                new TalonFX(1),
                new TalonFX(2),
                new TalonFX(3)
        };

        // Stream the falcons and add them to the orchestra.
        Arrays.stream(falcons).forEach(orchestra::addInstrument);
    }

    @Override
    public void tick() {
        // Play the orchestra.
        this.orchestra.play();

        // Check that the orchestra is not playing and that the queue is not empty.
        if (!this.orchestra.isPlaying() && !this.queue.isEmpty()) {
            // Play the next song.
            this.playNext();
        }
    }

    /**
     * Force plays a song.
     *
     * @param song The song to force play.
     */
    public void playSong(Song song) {
        // Load the new song.
        this.orchestra.loadMusic(song.getFile());
    }

    /**
     * Plays the next song from the queue, removing the song. If the queue is empty it does nothing.
     */
    public void playNext() {
        // If the queue is empty, return.
        if (this.queue.isEmpty()) return;

        // Poll the next song.
        Song toPlay = this.queue.poll();

        // Play the song.
        this.playSong(toPlay);
    }

    /**
     * Forces a song to the beginning of the queue.
     *
     * @param song The song to the force to the beginning.
     */
    public void forceQueue(Song song) {
        // Add the song to the beginning of the queue.
        this.queue.addFirst(song);
    }

    /**
     * Adds a song to the queue.
     *
     * @param song The song to add to the queue.
     */
    public void queueSong(Song song) {
        // Add the song to the queue.
        this.queue.add(song);
    }

}
