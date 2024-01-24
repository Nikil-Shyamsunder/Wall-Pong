import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.File;

public class SoundEffectPlayer implements Runnable{
    private String soundFilePath;

    public SoundEffectPlayer(String soundFilePath) {
        this.soundFilePath = soundFilePath;

        Thread soundEffectPlayerThread = new Thread(this);
        soundEffectPlayerThread.start();
    }

    public  void playSound() {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        playSound();
    }
}
