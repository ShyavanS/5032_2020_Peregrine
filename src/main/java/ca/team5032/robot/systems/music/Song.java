package ca.team5032.robot.systems.music;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a .chrp file in the /deploy directory which can be played by the music subsystem.
 */
@AllArgsConstructor
@Getter
public enum Song {
    ALL_STAR("AllStar.chrp"),
    CANTINA_BAND("CantinaBand.chrp"),
    IMPERIAL_MARCH("ImperialMarch.chrp"),
    WII_SONG("WiiSong.chrp");

    // The file in /deploy directory to load.
    private String file;
}
