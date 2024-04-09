package Thread.Music;

/**
 * This class creates a music thread.
 */
public class ThreadSound implements Runnable{

    private final Sound SOUND;
    private final boolean LOOP;

    /**
     * For a music thread, a sound object & the loop condition is needed.
     * @param sound instance of class Sound
     * @param loop condition if music should be looped or not.
     */
    public ThreadSound(Sound sound, boolean loop) {
        this.SOUND = sound;
        this.LOOP = loop;
    }

    @Override
    public void run() {
        do {
            SOUND.playSound();
        } while (LOOP);
    }
}
