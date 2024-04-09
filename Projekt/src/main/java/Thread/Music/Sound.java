package Thread.Music;

import javazoom.jl.player.Player;
import java.io.FileInputStream;

/**
 * This class creates a player in order to play mp3 sound files.
 * If the object is passed to a ThreadSound object, the sound can be played.
 */
public class Sound {

    private final String URL;

    /**
     * This method will create a mp3 file instance from the resource/music folder.
     * To play it, pass it to ThreadSound and create a Thread of it.
     * @param soundName is the short name(without .mp3) of the desired sound.
     */
    public Sound(String soundName) {
            this.URL = "target/classes/music/" + soundName + ".mp3";
    }

    /**
     * This method creates a player in order to play mp3 sound on it.
     * The ThreadSound class will call this method in the run()
     */
    public void playSound(){
        try {
            FileInputStream applause = new FileInputStream(URL);
            Player play = new Player(applause);
            play.play();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
