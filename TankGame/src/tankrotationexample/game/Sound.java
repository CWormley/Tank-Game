package tankrotationexample.game;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    private Clip clip;

    public Sound(Clip clip) {
        this.clip = clip;
    }

    public void play() {

        this.clip.setFramePosition(0);
        this.clip.start();
    }

    public void stop() {
        this.clip.stop();
    }

    public void loop() {
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void setVolume(float level) {
        FloatControl volume = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(20f * (float) Math.log10(level));
    }

}
